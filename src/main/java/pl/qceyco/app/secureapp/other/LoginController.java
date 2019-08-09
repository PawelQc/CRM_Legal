package pl.qceyco.app.secureapp.other;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import pl.qceyco.app.employee.Employee;
import pl.qceyco.app.secureapp.telusko.UserPrincipal;

import javax.servlet.http.HttpSession;

@SessionAttributes({"currentUser"})
@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/loginFailed", method = RequestMethod.GET)
    public String loginError(Model model) {
        model.addAttribute("error", "true");
        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(SessionStatus session) {
        SecurityContextHolder.getContext().setAuthentication(null);
        session.setComplete();
        return "redirect:/welcome";
    }

    @RequestMapping(value = "/postLogin", method = RequestMethod.POST)
    public String postLogin(Model model, HttpSession session) {
        // read principal out of security context and set it to session
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        validatePrinciple(authentication.getPrincipal());
        Employee loggedInUser = ((UserPrincipal) authentication.getPrincipal()).getUserDetails();
        model.addAttribute("currentUser", loggedInUser.getEmailLogin());
        session.setAttribute("userId", loggedInUser.getId());
        return "redirect:/wallPage";
    }

    private void validatePrinciple(Object principal) {
        if (!(principal instanceof UserPrincipal)) {
            throw new IllegalArgumentException("Principal can not be null!");
        }
    }
}