package pl.qceyco.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.qceyco.employee.Employee;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/clients")

public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String showAllClients(HttpSession session, Model model) {
        Employee employee = (Employee) session.getAttribute("loggedInUser");
        if (employee.getAdmin()) {
            return "admin/clients/clientsList";
        } else {
            return "user/clients/clientsList";
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String showChoiceOfLegalStatusForm() {
        return "admin/clients/clientChooseLegalStatus";
    }

    @RequestMapping(value = "/delete/{clientId}", method = RequestMethod.GET)
    public String delete(@PathVariable Long clientId, Model model) {
        if (clientService.getAnyProjectByClientId(clientId) != null) {
            model.addAttribute("deleteErrorProjectExists", "Cannot delete this client - delete related project first!");
            return "admin/clients/clientsList";
        }
        clientService.deleteClient(clientId);
        return "redirect:../list";
    }

    @ModelAttribute("clients")
    public List<Client> populateClients() {
        return clientService.getAllClients();
    }


}
