package pl.qceyco.employee.userInfo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.qceyco.employee.Employee;
import pl.qceyco.employee.additinalInfo.AdditionalInfoEmployee;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/user/")

public class UserInfoController {

    private final UserInfoService userInfoService;

    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String showDetails(Model model, HttpSession session) {
        Employee loggedInUser = (Employee) session.getAttribute("loggedInUser");
        Employee employee = userInfoService.getEmployeeById(loggedInUser.getId());
        AdditionalInfoEmployee additionalInfoEmployee = userInfoService.getAdditionalInfoById(loggedInUser.getAdditionalInfo().getId());
        model.addAttribute("employee", employee);
        model.addAttribute("additionalInfo", additionalInfoEmployee);
        if (loggedInUser.isAdmin()) {
            return "admin/info/adminInfo";
        } else {
            return "user/info/userInfo";
        }
    }

    @RequestMapping(value = "/info/update/{infoId}", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable Long infoId, Model model) {
        AdditionalInfoEmployee additionalInfoEmployee = userInfoService.getAdditionalInfoById(infoId);
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
        userInfoService.saveUpdate(additionalInfoEmployee);
        return "redirect:/user/info";
    }


}
