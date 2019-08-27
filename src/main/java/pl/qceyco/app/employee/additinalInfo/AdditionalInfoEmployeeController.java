package pl.qceyco.app.employee.additinalInfo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.qceyco.app.employee.Employee;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/employees/additional-info")

public class AdditionalInfoEmployeeController {

    private final AdditionalInfoEmployeeService additionalInfoEmployeeService;

    public AdditionalInfoEmployeeController(AdditionalInfoEmployeeService additionalInfoEmployeeService) {
        this.additionalInfoEmployeeService = additionalInfoEmployeeService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String showDetails(@RequestParam Long employeeId, @RequestParam(required = false) Long additionalInfoId,
                              Model model, HttpSession session) {
        if (additionalInfoId == null) {
            return "redirect:/employees/additional-info/add/" + employeeId;
        }
        Employee employee = additionalInfoEmployeeService.getEmployeeById(employeeId);
        model.addAttribute("employee", employee);
        AdditionalInfoEmployee additionalInfoEmployee = additionalInfoEmployeeService.getAdditionalInfoById(additionalInfoId);
        model.addAttribute("additionalInfoEmployee", additionalInfoEmployee);
        Employee loggedInUser = (Employee) session.getAttribute("loggedInUser");
        if (loggedInUser.getAdmin()) {
            return "admin/employees/detailedInfo/employeeDetailsList";
        } else {
            return "user/employees/detailedInfo/employeeDetailsList";
        }
    }

    @RequestMapping(value = "/add/{employeeId}", method = RequestMethod.GET)
    public String showAddForm(@PathVariable Long employeeId, Model model) {
        Employee employee = additionalInfoEmployeeService.getEmployeeById(employeeId);
        model.addAttribute("employee", employee);
        model.addAttribute("additionalInfoEmployee", new AdditionalInfoEmployee());
        return "admin/employees/detailedInfo/employeeDetailsAdd";
    }

    @RequestMapping(value = "/add/{employeeId}", method = RequestMethod.POST)
    public String processAddForm(@PathVariable Long employeeId, @ModelAttribute @Valid AdditionalInfoEmployee additionalInfoEmployee, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/employees/detailedInfo/employeeDetailsAdd";
        }
        additionalInfoEmployeeService.saveAdd(employeeId, additionalInfoEmployee);
        return "redirect:/employees/list";
    }

    @RequestMapping(value = "/update/{infoId}", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable Long infoId, Model model) {
        AdditionalInfoEmployee additionalInfoEmployee = additionalInfoEmployeeService.getAdditionalInfoById(infoId);
        if (additionalInfoEmployee == null) {
            model.addAttribute("error", "Update Error");
            return "error";
        }
        Employee employee = additionalInfoEmployeeService.getEmployeeByInfoId(infoId);
        model.addAttribute("employee", employee);
        model.addAttribute("additionalInfoEmployee", additionalInfoEmployee);
        return "admin/employees/detailedInfo/employeeDetailsUpdate";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateForm(@ModelAttribute @Valid AdditionalInfoEmployee additionalInfoEmployee, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/employees/detailedInfo/employeeDetailsUpdate";
        }
        additionalInfoEmployeeService.saveUpdate(additionalInfoEmployee);
        return "redirect:/employees/list";
    }


}
