package pl.qceyco.app.employee;

import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.qceyco.app.employee.additinalInfo.AdditionalInfoEmployeeRepository;
import pl.qceyco.app.project.ProjectRepository;
import pl.qceyco.app.secureapp.Authority;
import pl.qceyco.app.secureapp.AuthorityRepository;
import pl.qceyco.app.timesheet.referenceUnit.TimesheetReferenceUnit;
import pl.qceyco.app.timesheet.referenceUnit.TimesheetReferenceUnitRepository;
import pl.qceyco.app.timesheet.week.TimesheetWeekRepository;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/employees")

public class EmployeeController {

    private final AuthorityRepository authorityRepository;
    private final ProjectRepository projectRepository;
    private final AdditionalInfoEmployeeRepository additionalInfoEmployeeRepository;
    private final EmployeeRepository employeeRepository;
    private final TimesheetReferenceUnitRepository timesheetReferenceUnitRepository;
    private final TimesheetWeekRepository timesheetWeekRepository;

    public EmployeeController(EmployeeRepository employeeRepository, AdditionalInfoEmployeeRepository additionalInfoEmployeeRepository,
                              ProjectRepository projectRepository, AuthorityRepository authorityRepository,
                              TimesheetReferenceUnitRepository timesheetReferenceUnitRepository, TimesheetWeekRepository timesheetWeekRepository) {
        this.employeeRepository = employeeRepository;
        this.additionalInfoEmployeeRepository = additionalInfoEmployeeRepository;
        this.projectRepository = projectRepository;
        this.authorityRepository = authorityRepository;
        this.timesheetReferenceUnitRepository = timesheetReferenceUnitRepository;
        this.timesheetWeekRepository = timesheetWeekRepository;
    }

    @ModelAttribute("employees")
    public List<Employee> populateEmployees() {
        return employeeRepository.findAll();
    }

    //////////////////////

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String showAllEmployees(HttpSession session) {
        Employee employee = (Employee) session.getAttribute("loggedInUser");
        if (employee.getAdmin()) {
            return "admin/employees/employeesList";
        } else {
            return "user/employees/employeesList";
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String showAddForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "admin/employees/employeeAdd";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddForm(@ModelAttribute @Valid Employee employee, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/employees/employeeAdd";
        }
        if (!isEmailUnique(employee.getEmailLogin())) {
            model.addAttribute("errorNoUniqueEmail", "this email is already in use");
            return "admin/employees/employeeAdd";
        }
        employee.setPassword(BCrypt.hashpw(employee.getPassword(), BCrypt.gensalt()));
        setAuthority(employee);
        employeeRepository.save(employee);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{employeeId}", method = RequestMethod.GET)
    public String delete(@PathVariable Long employeeId, Model model, HttpSession session) {
        Employee loggedInUser = (Employee) session.getAttribute("loggedInUser");
        if (loggedInUser.getId() == employeeId) {
            model.addAttribute("deleteErrorIsAdmin", "Cannot delete admin account!");
            return "admin/employees/employeesList";
        }
        if (projectRepository.findAllByEmployeeId(employeeId).size() >= 1) {
            model.addAttribute("deleteErrorProjectExists", "Cannot delete this employee - delete or update related project first!");
            return "admin/employees/employeesList";
        }
        deleteEmployee(employeeId);
        return "redirect:../list";
    }


    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Employee employee = employeeRepository.findFirstById(id);
        if (employee == null) {
            model.addAttribute("error", "Update Error");
            return "error";
        }
        model.addAttribute("employee", employee);
        return "admin/employees/employeeUpdate";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateForm(@ModelAttribute @Valid Employee employee, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/employees/employeeUpdate";
        }
        if (!isEmailUnique(employee.getEmailLogin())) {
            model.addAttribute("errorNoUniqueEmail", "this email is already in use");
            return "admin/employees/employeeUpdate";
        }
        setAuthority(employee);
        employeeRepository.save(employee);
        return "redirect:list";
    }

    @RequestMapping(value = "/update-password/{employeeId}", method = RequestMethod.GET)
    public String showUpdatePasswordForm(@PathVariable Long employeeId, Model model) {
        Employee employee = employeeRepository.findFirstById(employeeId);
        if (employee == null) {
            model.addAttribute("error", "Update Error");
            return "error";
        }
        model.addAttribute("employee", employee);
        return "admin/employees/employeeUpdatePassword";
    }

    @RequestMapping(value = "/update-password/{employeeId}", method = RequestMethod.POST)
    public String processUpdatePasswordForm(@PathVariable Long employeeId, @RequestParam String password, Model model) {
        Employee employee = employeeRepository.findFirstById(employeeId);
        if (StringUtils.isBlank(password) || password.length() < 8 || password.length() > 60) {
            model.addAttribute("employee", employee);
            model.addAttribute("errorPasswordInput", "Enter a new password: must be between 8 and 60 characters!");
            return "admin/employees/employeeUpdatePassword";
        }
        employee.setPassword(password);
        employee.setPassword(BCrypt.hashpw(employee.getPassword(), BCrypt.gensalt()));
        employeeRepository.save(employee);
        return "redirect:../list";
    }

////////////////////////////////////////////

    private void setAuthority(@Valid @ModelAttribute Employee employee) {
        Authority authority = null;
        if (employee.getAdmin() == true) {
            authority = authorityRepository.findFirstById(1);
        } else {
            authority = authorityRepository.findFirstById(2);
        }
        Set<Authority> employeeAuthorities = employee.getAuthorities();
        employeeAuthorities.add(authority);
        employee.setAuthorities(employeeAuthorities);
    }

    private boolean isEmailUnique(String userEmail) {
        List<Employee> employees = employeeRepository.findAll();
        boolean result = true;
        for (Employee e : employees) {
            if (e.getEmailLogin().equals(userEmail)) {
                result = false;
                break;
            }
        }
        return result;
    }

    private void deleteEmployee(@PathVariable Long employeeId) {
        Employee employeeToDelete = employeeRepository.findFirstById(employeeId);
        List<TimesheetReferenceUnit> employeesTimesheets = timesheetReferenceUnitRepository.findAllByEmployeeId(employeeToDelete.getId());
        if (employeesTimesheets.size() > 0) {
            for (TimesheetReferenceUnit t : employeesTimesheets) {
                timesheetReferenceUnitRepository.delete(t);
            }
        }
        employeeRepository.deleteById(employeeId);
        if (employeeToDelete.getAdditionalInfo() != null) {
            Long infoId = employeeToDelete.getAdditionalInfo().getId();
            additionalInfoEmployeeRepository.deleteById(infoId);
        }
    }

}
