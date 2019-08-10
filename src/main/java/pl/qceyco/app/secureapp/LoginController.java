package pl.qceyco.app.secureapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }

    @RequestMapping("/login-success")
    public String loginSuccess() {
        return "loginSuccess";
    }

    @RequestMapping("/logout-success")
    public String logoutPage() {
        return "logout";
    }


}