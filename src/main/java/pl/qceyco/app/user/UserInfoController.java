package pl.qceyco.app.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.qceyco.app.employee.Employee;
import pl.qceyco.app.employee.EmployeeRepository;
import pl.qceyco.app.employee.additinalInfo.AdditionalInfoEmployee;
import pl.qceyco.app.employee.additinalInfo.AdditionalInfoEmployeeRepository;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/user/")

public class UserInfoController {

    private final AdditionalInfoEmployeeRepository additionalInfoEmployeeRepository;
    private final EmployeeRepository employeeRepository;

    public UserInfoController(AdditionalInfoEmployeeRepository additionalInfoEmployeeRepository, EmployeeRepository employeeRepository) {
        this.additionalInfoEmployeeRepository = additionalInfoEmployeeRepository;
        this.employeeRepository = employeeRepository;
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String showDetails(Model model, HttpSession session) {
        Employee loggedInUser = (Employee) session.getAttribute("loggedInUser");
        Employee employee = employeeRepository.findFirstById(loggedInUser.getId());
        AdditionalInfoEmployee additionalInfoEmployee = additionalInfoEmployeeRepository.findFirstById(loggedInUser.getId());
        model.addAttribute("employee", employee);
        model.addAttribute("additionalInfo", additionalInfoEmployee);
        if (loggedInUser.getAdmin()) {
            return "admin/info/adminInfo";
        } else {
            return "user/info/userInfo";
        }
    }

    @RequestMapping(value = "/info/update/{infoId}", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable Long infoId, Model model) {
        AdditionalInfoEmployee additionalInfoEmployee = additionalInfoEmployeeRepository.findFirstById(infoId);
        if (additionalInfoEmployee == null) {
            model.addAttribute("error", "Update Error");
            return "error";
        }
        model.addAttribute("additionalInfoEmployee", additionalInfoEmployee);
        return "admin/info/infoUpdate";
    }

    @RequestMapping(value = "/info/update", method = RequestMethod.POST)
    public String processUpdateForm(@ModelAttribute @Valid AdditionalInfoEmployee additionalInfoEmployee, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/info/infoUpdate";
        }
        additionalInfoEmployeeRepository.save(additionalInfoEmployee);
        return "redirect:/user/info";
    }


}
