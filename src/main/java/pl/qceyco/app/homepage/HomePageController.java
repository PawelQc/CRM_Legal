package pl.qceyco.app.homepage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.qceyco.app.employee.Employee;

import javax.servlet.http.HttpSession;

@Controller
public class HomePageController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showStartPage() {
        return "start";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String showHomePage(HttpSession session) {
        Employee employee = (Employee) session.getAttribute("loggedInUser");
        if (employee.getAdmin() == true) {
            return "admin/homeAdmin";
        } else {
            return "user/homeUser";
        }
    }
}







