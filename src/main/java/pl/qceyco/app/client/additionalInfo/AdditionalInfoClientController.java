package pl.qceyco.app.client.additionalInfo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.qceyco.app.client.Client;
import pl.qceyco.app.client.ClientsAllRepository;
import pl.qceyco.app.employee.Employee;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/clients/additional-info")

public class AdditionalInfoClientController {

    private final AdditionalInfoClientRepository additionalInfoClientRepository;
    private final ClientsAllRepository clientsAllRepository;

    public AdditionalInfoClientController(AdditionalInfoClientRepository additionalInfoClientRepository, ClientsAllRepository clientsAllRepository) {
        this.additionalInfoClientRepository = additionalInfoClientRepository;
        this.clientsAllRepository = clientsAllRepository;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String showDetails(@RequestParam Long clientId, @RequestParam(required = false) Long additionalInfoId, Model model, HttpSession session) {
        if (additionalInfoId == null) {
            return "redirect:/clients/additional-info/add/" + clientId;
        }
        Client client = clientsAllRepository.findFirstById(clientId);
        AdditionalInfoClient additionalInfoClient = additionalInfoClientRepository.findFirstById(additionalInfoId);
        model.addAttribute("additionalInfoClient", additionalInfoClient);
        model.addAttribute("client", client);
        Employee employee = (Employee) session.getAttribute("loggedInUser");
        if (employee.getAdmin() == true) {
            return "admin/clients/detailedInfo/clientDetailsList";
        } else {
            return "user/clients/detailedInfo/clientDetailsList";
        }
    }

    @RequestMapping(value = "/add/{clientId}", method = RequestMethod.GET)
    public String showAddForm(@PathVariable Long clientId, Model model) {
        Client client = clientsAllRepository.findFirstById(clientId);
        model.addAttribute("client", client);
        model.addAttribute("additionalInfoClient", new AdditionalInfoClient());
        return "admin/clients/detailedInfo/clientDetailsAdd";
    }

    @RequestMapping(value = "/add/{clientId}", method = RequestMethod.POST)
    public String processAddForm(@PathVariable Long clientId, @ModelAttribute @Valid AdditionalInfoClient additionalInfoClient, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/clients/detailedInfo/clientDetailsAdd";
        }
        additionalInfoClientRepository.save(additionalInfoClient);
        Client client = clientsAllRepository.findFirstById(clientId);
        client.setAdditionalInfo(additionalInfoClient);
        clientsAllRepository.save(client);
        return "redirect:/clients/list";
    }

    @RequestMapping(value = "/update/{infoId}", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable Long infoId, Model model) {
        AdditionalInfoClient additionalInfoClient = additionalInfoClientRepository.findFirstById(infoId);
        if (additionalInfoClient == null) {
            model.addAttribute("error", "Update Error");
            return "error";
        }
        model.addAttribute("additionalInfoClient", additionalInfoClient);
        return "admin/clients/detailedInfo/clientDetailsUpdate";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateForm(@ModelAttribute @Valid AdditionalInfoClient additionalInfoClient, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/clients/detailedInfo/clientDetailsUpdate";
        }
        additionalInfoClientRepository.save(additionalInfoClient);
        return "redirect:/clients/list";
    }


}
