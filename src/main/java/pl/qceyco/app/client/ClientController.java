package pl.qceyco.app.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.qceyco.app.client.legalPersonality.LegalPersonality;
import pl.qceyco.app.client.legalPersonality.LegalPersonalityRepository;
import pl.qceyco.app.employee.Employee;
import pl.qceyco.app.employee.EmployeeRepository;

import java.util.List;

@Controller
@RequestMapping("/clients")

public class ClientController {

    private final LegalPersonalityRepository legalPersonalityRepository;
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;

    public ClientController(ClientRepository clientRepository, EmployeeRepository employeeRepository, LegalPersonalityRepository legalPersonalityRepository) {
        this.clientRepository = clientRepository;
        this.employeeRepository = employeeRepository;
        this.legalPersonalityRepository = legalPersonalityRepository;
    }

    @ModelAttribute("clients")
    public List<Client> populateClients() {
        return clientRepository.findAll();
    }

    @ModelAttribute("employees")
    public List<Employee> populateEmployees() {
        return employeeRepository.findAll();
    }

    @ModelAttribute("legalPersonality")
    public List<LegalPersonality> populateLegalForms() {
        return legalPersonalityRepository.findAll();
    }

    //////////////////////

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String showAllClients() {
        return "clients/clientsList";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String showAddForm(Model model) {
        model.addAttribute("client", new Client());
        return "clients/clientAdd";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddForm(@ModelAttribute @Validated Client client, BindingResult result) {
        if (result.hasErrors()) {
            return "clients/clientAdd";
        }
        clientRepository.save(client);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable Long id) {
        clientRepository.deleteById(id);
        return "redirect:../list";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Client client = clientRepository.findFirstClientById(id);
        if (client == null) {
            model.addAttribute("error", "Update Error");
            return "error";
        }
        model.addAttribute("client", client);
        return "clients/clientUpdate";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateForm(@ModelAttribute @Validated Client client, BindingResult result) {
        if (result.hasErrors()) {
            return "clients/clientUpdate";
        }
        clientRepository.save(client);
        return "redirect:list";
    }


}
