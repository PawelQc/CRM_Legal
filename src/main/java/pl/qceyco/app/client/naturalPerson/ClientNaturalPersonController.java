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

    private final ClientNaturalPersonRepository clientNaturalPersonRepository;

    public ClientNaturalPersonController(ClientNaturalPersonRepository clientNaturalPersonRepository) {
        this.clientNaturalPersonRepository = clientNaturalPersonRepository;
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
        clientNaturalPersonRepository.save(clientNaturalPerson);
        return "redirect:/clients/list";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable Long id, Model model) {
        ClientNaturalPerson clientNaturalPerson = clientNaturalPersonRepository.findFirstById(id);
        if (clientNaturalPerson == null) {
            model.addAttribute("error", "Update Error");
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
        clientNaturalPersonRepository.save(clientNaturalPerson);
        return "redirect:/clients/list";
    }


}
