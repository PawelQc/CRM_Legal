package pl.qceyco.app.secureApp;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import pl.qceyco.app.employee.Employee;

@Controller
@SessionAttributes("loggedInUser")
public class LoginController {

    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }

    @RequestMapping("/login-success")
    public String loginSuccess(Model model) {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        validatePrinciple(authentication.getPrincipal());
        Employee loggedInUser = ((UserPrincipal) authentication.getPrincipal()).getUserDetails();
        model.addAttribute("loggedInUser", loggedInUser);
        if (loggedInUser.getAdmin() == true) {
            return "admin/loginSuccessAdmin";
        } else {
            return "user/loginSuccessUser";
        }
    }

    @RequestMapping("/logout-success")
    public String logoutPage() {
        return "logout";
    }

    private void validatePrinciple(Object principal) {
        if (!(principal instanceof UserPrincipal)) {
            throw new IllegalArgumentException("Principal can not be null!");
        }
    }

}


