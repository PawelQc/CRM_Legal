package pl.qceyco.app.client.naturalPerson;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("/clients/natural-person")

public class ClientNaturalPersonController {

    private final ClientNaturalPersonService clientNaturalPersonService;

    public ClientNaturalPersonController(ClientNaturalPersonService clientNaturalPersonService) {
        this.clientNaturalPersonService = clientNaturalPersonService;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String showAddForm(Model model) {
        model.addAttribute("clientNaturalPerson", new ClientNaturalPerson());
        return "admin/clients/naturalPerson/clientNaturalPersonAdd";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddForm(@ModelAttribute @Valid ClientNaturalPerson clientNaturalPerson, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/clients/naturalPerson/clientNaturalPersonAdd";
        }
        clientNaturalPersonService.save(clientNaturalPerson);
        return "redirect:/clients/list";
    }

    @RequestMapping(value = "/update/{clientId}", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable Long clientId, Model model) {
        ClientNaturalPerson clientNaturalPerson = clientNaturalPersonService.findClientById(clientId);
        if (clientNaturalPerson == null) {
            model.addAttribute("error", "Error: something went wrong... Cause: problem with update - wrong id");
            return "error";
        }
        model.addAttribute("clientNaturalPerson", clientNaturalPerson);
        return "admin/clients/naturalPerson/clientNaturalPersonUpdate";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateForm(@ModelAttribute @Valid ClientNaturalPerson clientNaturalPerson, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/clients/naturalPerson/clientNaturalPersonUpdate";
        }
        clientNaturalPersonService.save(clientNaturalPerson);
        return "redirect:/clients/list";
    }

}
