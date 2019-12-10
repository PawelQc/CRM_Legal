package pl.qceyco.client.additionalInfo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.qceyco.client.Client;
import pl.qceyco.employee.Employee;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/clients/additional-info")

public class AdditionalInfoClientController {

    private final AdditionalInfoClientService additionalInfoClientService;

    public AdditionalInfoClientController(AdditionalInfoClientService additionalInfoClientService) {
        this.additionalInfoClientService = additionalInfoClientService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String showDetails(@RequestParam Long clientId, @RequestParam(required = false) Long additionalInfoId, Model model, HttpSession session) {
        if (additionalInfoId == null) {
            return "redirect:/clients/additional-info/add/" + clientId;
        }
        Client client = additionalInfoClientService.getClientById(clientId);
        AdditionalInfoClient additionalInfoClient = additionalInfoClientService.getAdditionalInfoById(additionalInfoId);
        model.addAttribute("additionalInfoClient", additionalInfoClient);
        model.addAttribute("client", client);
        Employee employee = (Employee) session.getAttribute("loggedInUser");
        if (employee.isAdmin()) {
            return "admin/clients/detailedInfo/clientDetailsList";
        } else {
            return "user/clients/detailedInfo/clientDetailsList";
        }
    }

    @RequestMapping(value = "/add/{clientId}", method = RequestMethod.GET)
    public String showAddForm(@PathVariable Long clientId, Model model) {
        Client client = additionalInfoClientService.getClientById(clientId);
        model.addAttribute("client", client);
        model.addAttribute("additionalInfoClient", new AdditionalInfoClient());
        return "admin/clients/detailedInfo/clientDetailsAdd";
    }

    @RequestMapping(value = "/add/{clientId}", method = RequestMethod.POST)
    public String processAddForm(@PathVariable Long clientId, @ModelAttribute @Valid AdditionalInfoClient additionalInfoClient, BindingResult result, Model model) {
        if (result.hasErrors()) {
            Client client = additionalInfoClientService.getClientById(clientId);
            model.addAttribute("client", client);
            return "admin/clients/detailedInfo/clientDetailsAdd";
        }
        additionalInfoClientService.saveAdd(clientId, additionalInfoClient);
        return "redirect:/clients/list";
    }

    @RequestMapping(value = "/update/{infoId}", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable Long infoId, Model model) {
        AdditionalInfoClient additionalInfoClient = additionalInfoClientService.getAdditionalInfoById(infoId);
        if (additionalInfoClient == null) {
            model.addAttribute("error", "Error: something went wrong... Cause: problem with update - wrong id");
            return "error";
        }
        Client client = additionalInfoClientService.getClientByInfoId(infoId);
        model.addAttribute("client", client);
        model.addAttribute("additionalInfoClient", additionalInfoClient);
        return "admin/clients/detailedInfo/clientDetailsUpdate";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateForm(@ModelAttribute @Valid AdditionalInfoClient additionalInfoClient, BindingResult result, Model model) {
        if (result.hasErrors()) {
            Client client = additionalInfoClientService.getClientByInfoId(additionalInfoClient.getId());
            model.addAttribute("client", client);
            return "admin/clients/detailedInfo/clientDetailsUpdate";
        }
        additionalInfoClientService.saveUpdate(additionalInfoClient);
        return "redirect:/clients/list";
    }


}
