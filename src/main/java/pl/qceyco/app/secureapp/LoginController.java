package pl.qceyco.app.secureapp;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.qceyco.app.employee.Employee;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }

    @RequestMapping("/login-success")
    public String loginSuccess(Model model, HttpSession session) {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        validatePrinciple(authentication.getPrincipal());
        Employee loggedInUser = ((UserPrincipal) authentication.getPrincipal()).getUserDetails();

//todo - loggeninuser==>zalogowany uzytkownaik!! na tej podstawie będę weryfikował w widokach - dodaj tę infomację do sesji/ewentualnie całego usera
        return "loginSuccess";
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