package pl.qceyco.app.legalcase;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.qceyco.app.client.Client;
import pl.qceyco.app.client.ClientsAllRepository;
import pl.qceyco.app.employee.Employee;
import pl.qceyco.app.employee.EmployeeRepository;

import java.util.List;

@Controller
@RequestMapping("/legal-cases")

public class LegalCaseController {

    private final ClientsAllRepository clientsAllRepository;
    private final EmployeeRepository employeeRepository;
    private final LegalCaseRepository legalCaseRepository;

    public LegalCaseController(ClientsAllRepository clientsAllRepository, EmployeeRepository employeeRepository, LegalCaseRepository legalCaseRepository) {
        this.clientsAllRepository = clientsAllRepository;
        this.employeeRepository = employeeRepository;
        this.legalCaseRepository = legalCaseRepository;
    }

    @ModelAttribute("clients")
    public List<Client> populateClients() {
        return clientsAllRepository.findAll();
    }

    @ModelAttribute("employees")
    public List<Employee> populateEmployees() {
        return employeeRepository.findAll();
    }

    @ModelAttribute("legalCases")
    public List<LegalCase> populateLegalCases() {
        return legalCaseRepository.findAllCasesWithProjectTeamMembers();
    }

    //////////////////////

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String showAllCases() {
        return "legalCases/casesList";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String showAddForm(Model model) {
        model.addAttribute("legalCase", new LegalCase());
        return "legalCases/caseAdd";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddForm(@ModelAttribute @Validated LegalCase legalCase, BindingResult result) {
        if (result.hasErrors()) {
            return "legalCases/caseAdd";
        }
        legalCaseRepository.save(legalCase);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable Long id) {
        legalCaseRepository.deleteById(id);
        return "redirect:../list";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable Long id, Model model) {
        LegalCase legalCase = legalCaseRepository.findCaseWithProjectTeamMembers(id);
        if (legalCase == null) {
            model.addAttribute("error", "Update Error");
            return "error";
        }
        model.addAttribute("legalCase", legalCase);
        return "legalCases/caseUpdate";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateForm(@ModelAttribute @Validated LegalCase legalCase, BindingResult result) {
        if (result.hasErrors()) {
            return "legalCases/caseUpdate";
        }
        legalCaseRepository.save(legalCase);
        return "redirect:list";
    }


}
