package pl.qceyco.app.employee;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
        if (employee.getAdmin() == true) {
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
    public String processAddForm(@ModelAttribute @Valid Employee employee, BindingResult result) {
        if (result.hasErrors()) {
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
            model.addAttribute("deleteErrorAdmin", "Cannot delete admin account!");
            return "admin/employees/employeesList";
        }
        if (projectRepository.findAllByEmployeeId(employeeId).size() >= 1) {
            model.addAttribute("deleteErrorProjectExists", "Cannot delete this employee - delete or update related project first!");
            return "admin/employees/employeesList";
        }
        Employee employeeToDelete = employeeRepository.findFirstById(employeeId);
        List<TimesheetReferenceUnit> employeesTimesheets = timesheetReferenceUnitRepository.findAllByEmployeeId(employeeToDelete.getId());
        if (employeesTimesheets.size() > 0) {
            for (TimesheetReferenceUnit t : employeesTimesheets) {
                timesheetReferenceUnitRepository.delete(t);
                timesheetWeekRepository.deleteById(t.getTimesheetWeek().getId());
            }
        }
        employeeRepository.deleteById(employeeId);
        if (employeeToDelete.getAdditionalInfo() != null) {
            Long infoId = employeeToDelete.getAdditionalInfo().getId();
            additionalInfoEmployeeRepository.deleteById(infoId);
        }
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
    public String processUpdateForm(@ModelAttribute @Valid Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/employees/employeeUpdate";
        }
        //todo przy update pobiera hasło w formie hashu - zmień formularz update - hasło update zrób oddzielnie + sprawdz czy sie zgadza z poprzednim
        employee.setPassword(BCrypt.hashpw(employee.getPassword(), BCrypt.gensalt()));
        setAuthority(employee);
        employeeRepository.save(employee);
        return "redirect:list";
    }

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


}
