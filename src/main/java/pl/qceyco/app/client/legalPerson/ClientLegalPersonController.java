package pl.qceyco.app.client.legalPerson;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/clients/legal-person")

public class ClientLegalPersonController {

    private final ClientLegalPersonRepository clientLegalPersonRepository;

    public ClientLegalPersonController(ClientLegalPersonRepository clientLegalPersonRepository) {
        this.clientLegalPersonRepository = clientLegalPersonRepository;
    }

   /* @ModelAttribute("clientsLP")
    public List<ClientLegalPerson> populateClientsLP() {
        return clientLegalPersonRepository.findAll();
    }

    //////////////////////

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String showAllClientsLP() {
        return "clients/clientsList";
    }*/

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String showAddForm(Model model) {
        model.addAttribute("clientLegalPerson", new ClientLegalPerson());
        return "clients/legalPerson/clientLegalPersonAdd";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddForm(@ModelAttribute @Validated ClientLegalPerson clientLegalPerson, BindingResult result) {
        if (result.hasErrors()) {
            return "clients/legalPerson/clientLegalPersonAdd";
        }
        clientLegalPersonRepository.save(clientLegalPerson);
        return "redirect:/clients/list";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable Long id, Model model) {
        ClientLegalPerson clientLegalPerson = clientLegalPersonRepository.findFirstById(id);
        if (clientLegalPerson == null) {
            model.addAttribute("error", "Update Error");
            return "error";
        }
        model.addAttribute("clientLegalPerson", clientLegalPerson);
        return "clients/legalPerson/clientLegalPersonUpdate";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateForm(@ModelAttribute @Validated ClientLegalPerson clientLegalPerson, BindingResult result) {
        if (result.hasErrors()) {
            return "clients/legalPerson/clientLegalPersonUpdate";
        }
        clientLegalPersonRepository.save(clientLegalPerson);
        return "redirect:/clients/list";
    }


}
