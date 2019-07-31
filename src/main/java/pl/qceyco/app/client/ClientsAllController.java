package pl.qceyco.app.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/clients")

public class ClientsAllController {

    private final ClientsAllRepository clientsAllRepository;

    public ClientsAllController(ClientsAllRepository clientsAllRepository) {
        this.clientsAllRepository = clientsAllRepository;
    }

    @ModelAttribute("clients")
    public List<Client> populateClients() {
        return clientsAllRepository.findAll();
    }

    //////////////////////

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String showAllClients() {
        return "clients/clientsList";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String showChoiceOfLegalStatusForm() {
        return "clients/legalStatusChoice/clientChooseLegalStatus";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable Long id) {
        clientsAllRepository.deleteById(id);
        return "redirect:../list";
    }


}
