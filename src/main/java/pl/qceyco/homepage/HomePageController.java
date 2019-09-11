package pl.qceyco.homepage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.qceyco.employee.Employee;

import javax.servlet.http.HttpSession;

@Controller
public class HomePageController {

    private final HomePageService homePageService;

    public HomePageController(HomePageService homePageService) {
        this.homePageService = homePageService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showStartPage() {
        return "start";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String showHomePage(HttpSession session, Model model) {
        Employee loggedInUser = (Employee) session.getAttribute("loggedInUser");
        if (loggedInUser.getAdmin()) {
            homePageService.processAdminHome(model);
            return "admin/homeAdmin";
        } else {
            homePageService.processUserHome(loggedInUser, model);
            return "user/homeUser";
        }
    }

}







