package pl.qceyco.client.legalPerson;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("/clients/legal-person")

public class ClientLegalPersonController {

    private final ClientLegalPersonService clientLegalPersonService;

    public ClientLegalPersonController(ClientLegalPersonService clientLegalPersonService) {
        this.clientLegalPersonService = clientLegalPersonService;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String showAddForm(Model model) {
        model.addAttribute("clientLegalPerson", new ClientLegalPerson());
        return "admin/clients/legalPerson/clientLegalPersonAdd";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddForm(@ModelAttribute @Valid ClientLegalPerson clientLegalPerson, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/clients/legalPerson/clientLegalPersonAdd";
        }
        clientLegalPersonService.save(clientLegalPerson);
        return "redirect:/clients/list";
    }

    @RequestMapping(value = "/update/{clientId}", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable Long clientId, Model model) {
        ClientLegalPerson clientLegalPerson = clientLegalPersonService.findClientById(clientId);
        if (clientLegalPerson == null) {
            model.addAttribute("error", "Error: something went wrong... Cause: problem with update - wrong id");
            return "error";
        }
        model.addAttribute("clientLegalPerson", clientLegalPerson);
        return "admin/clients/legalPerson/clientLegalPersonUpdate";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateForm(@ModelAttribute @Valid ClientLegalPerson clientLegalPerson, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/clients/legalPerson/clientLegalPersonUpdate";
        }
        clientLegalPersonService.save(clientLegalPerson);
        return "redirect:/clients/list";
    }


}
