package pl.qceyco.app.client.additionalInfo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.qceyco.app.client.Client;
import pl.qceyco.app.client.ClientsAllRepository;

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
    public String showDetails(@RequestParam Long clientId, @RequestParam(required = false) Long additionalInfoId, Model model) {
        if (additionalInfoId == null) {
            return "redirect:/clients/additional-info/add/" + clientId;
        }
        AdditionalInfoClient additionalInfoClient = additionalInfoClientRepository.findFirstById(additionalInfoId);
        model.addAttribute("additionalInfoClient", additionalInfoClient);
        return "clients/detailedInfo/clientDetailsList";
    }

    @RequestMapping(value = "/add/{clientId}", method = RequestMethod.GET)
    public String showAddForm(@PathVariable Long clientId, Model model) {
        Client client = clientsAllRepository.findFirstById(clientId);
        model.addAttribute("client", client);
        model.addAttribute("additionalInfoClient", new AdditionalInfoClient());
        return "clients/detailedInfo/clientDetailsAdd";
    }

    @RequestMapping(value = "/add/{clientId}", method = RequestMethod.POST)
    public String processAddForm(@PathVariable Long clientId, @ModelAttribute @Validated AdditionalInfoClient additionalInfoClient, BindingResult result) {
        if (result.hasErrors()) {
            return "clients/detailedInfo/clientDetailsAdd";
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
        return "clients/detailedInfo/clientDetailsUpdate";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateForm(@ModelAttribute @Validated AdditionalInfoClient additionalInfoClient, BindingResult result) {
        if (result.hasErrors()) {
            return "clients/detailedInfo/clientDetailsUpdate";
        }
        additionalInfoClientRepository.save(additionalInfoClient);
        return "redirect:/clients/list";
    }


}
