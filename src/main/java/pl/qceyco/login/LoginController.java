package pl.qceyco.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import pl.qceyco.employee.Employee;

@Controller
@SessionAttributes("loggedInUser")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }

    @RequestMapping("/login-success")
    public String loginSuccess(Model model) {
        Employee loggedInUser = loginService.getLoggedInUser();
        model.addAttribute("loggedInUser", loggedInUser);
        if (loggedInUser.getAdmin()) {
            return "admin/loginSuccessAdmin";
        } else {
            return "user/loginSuccessUser";
        }
    }

    @RequestMapping("/logout-success")
    public String logoutPage() {
        return "logout";
    }


}


