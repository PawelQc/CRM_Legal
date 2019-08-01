package pl.qceyco.app.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.qceyco.app.client.additionalInfo.AdditionalInfoClient;
import pl.qceyco.app.client.additionalInfo.AdditionalInfoClientRepository;

import java.util.List;

@Controller
@RequestMapping("/clients")

public class ClientsAllController {

    private final ClientsAllRepository clientsAllRepository;
    private final AdditionalInfoClientRepository additionalInfoClientRepository;

    public ClientsAllController(ClientsAllRepository clientsAllRepository, AdditionalInfoClientRepository additionalInfoClientRepository) {
        this.clientsAllRepository = clientsAllRepository;
        this.additionalInfoClientRepository = additionalInfoClientRepository;
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
        Client clientToDelete = clientsAllRepository.findFirstById(id);
        clientsAllRepository.deleteById(id);
        if (clientToDelete.getAdditionalInfo() != null) {
            Long infoId = clientToDelete.getAdditionalInfo().getId();
            additionalInfoClientRepository.deleteById(infoId);
        }
        return "redirect:../list";
    }


}
