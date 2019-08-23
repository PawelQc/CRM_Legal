package pl.qceyco.app.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.qceyco.app.client.additionalInfo.AdditionalInfoClientRepository;
import pl.qceyco.app.employee.Employee;
import pl.qceyco.app.project.ProjectRepository;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/clients")

public class ClientsAllController {

    private final ProjectRepository projectRepository;
    private final ClientsAllRepository clientsAllRepository;
    private final AdditionalInfoClientRepository additionalInfoClientRepository;

    public ClientsAllController(ClientsAllRepository clientsAllRepository, AdditionalInfoClientRepository additionalInfoClientRepository, ProjectRepository projectRepository) {
        this.clientsAllRepository = clientsAllRepository;
        this.additionalInfoClientRepository = additionalInfoClientRepository;
        this.projectRepository = projectRepository;
    }

    @ModelAttribute("clients")
    public List<Client> populateClients() {
        return clientsAllRepository.findAll();
    }

    //////////////////////

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String showAllClients(HttpSession session) {
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

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable Long id, Model model) {
        if (projectRepository.findFirstByClientId(id) != null) {
            model.addAttribute("deleteErrorProjectExists", "Cannot delete this client - delete related project first!");
            return "admin/clients/clientsList";
        }
        Client clientToDelete = clientsAllRepository.findFirstById(id);
        clientsAllRepository.deleteById(id);
        if (clientToDelete.getAdditionalInfo() != null) {
            Long infoId = clientToDelete.getAdditionalInfo().getId();
            additionalInfoClientRepository.deleteById(infoId);
        }
        return "redirect:../list";
    }


}
