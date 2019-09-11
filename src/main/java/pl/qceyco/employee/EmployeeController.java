package pl.qceyco.employee;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/employees")

public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String showAllEmployees(HttpSession session, Model model) {
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
        if (!employeeService.emailIsUniqueAdd(employee.getEmailLogin())) {
            model.addAttribute("errorNoUniqueEmail", "this email is already in use");
            return "admin/employees/employeeAdd";
        }
        employeeService.encryptPassword(employee);
        employeeService.setAuthority(employee);
        employeeService.save(employee);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{employeeId}", method = RequestMethod.GET)
    public String delete(@PathVariable Long employeeId, Model model, HttpSession session) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        if (employee.getAdmin()) {
            model.addAttribute("deleteErrorIsAdmin", "Cannot delete admin account!");
            return "admin/employees/employeesList";
        }
        if (employeeService.getAllProjectsWhereEmployeeParticipates(employeeId).size() > 0) {
            model.addAttribute("deleteErrorProjectExists", "Cannot delete this employee" +
                    " - delete or update related project first!");
            return "admin/employees/employeesList";
        }
        employeeService.delete(employeeId);
        return "redirect:../list";
    }

    @RequestMapping(value = "/update/{employeeId}", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable Long employeeId, Model model) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        if (employee == null) {
            model.addAttribute("error", "Error: something went wrong... Cause: problem with update - wrong id");
            return "error";
        }
        model.addAttribute("employee", employee);
        return "admin/employees/employeeUpdate";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateForm(@RequestParam(required = false) String previousEmail, @ModelAttribute @Valid Employee employee,
                                    BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/employees/employeeUpdate";
        }
        if (!employeeService.emailIsUniqueUpdate(employee.getEmailLogin(), previousEmail)) {
            model.addAttribute("errorNoUniqueEmail", "this email is already in use");
            return "admin/employees/employeeUpdate";
        }
        employeeService.setAuthority(employee);
        employeeService.save(employee);
        return "redirect:list";
    }

    @RequestMapping(value = "/update-password/{employeeId}", method = RequestMethod.GET)
    public String showUpdatePasswordForm(@PathVariable Long employeeId, Model model) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        if (employee == null) {
            model.addAttribute("error", "Error: something went wrong... Cause: problem with update - wrong id");
            return "error";
        }
        model.addAttribute("employee", employee);
        return "admin/employees/employeeUpdatePassword";
    }

    @RequestMapping(value = "/update-password/{employeeId}", method = RequestMethod.POST)
    public String processUpdatePasswordForm(@PathVariable Long employeeId, @RequestParam String password, Model model) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        if (employeeService.passwordFailedValidation(password)) {
            model.addAttribute("employee", employee);
            model.addAttribute("errorPasswordInput", "Enter a new password: must be between 8 and 60 characters!");
            return "admin/employees/employeeUpdatePassword";
        }
        employee.setPassword(password);
        employeeService.encryptPassword(employee);
        employeeService.save(employee);
        return "redirect:../list";
    }

    @ModelAttribute("employees")
    public List<Employee> populateEmployees() {
        return employeeService.getAllEmployees();
    }

}
