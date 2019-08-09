package pl.qceyco.app.employee;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.qceyco.app.employee.additinalInfo.AdditionalInfoEmployeeRepository;
import pl.qceyco.app.project.ProjectRepository;

import java.util.List;

@Controller
@RequestMapping("/employees")

public class EmployeeController {

    private final ProjectRepository projectRepository;
    private final AdditionalInfoEmployeeRepository additionalInfoEmployeeRepository;
    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository, AdditionalInfoEmployeeRepository additionalInfoEmployeeRepository, ProjectRepository projectRepository) {
        this.employeeRepository = employeeRepository;
        this.additionalInfoEmployeeRepository = additionalInfoEmployeeRepository;
        this.projectRepository = projectRepository;
    }

    @ModelAttribute("employees")
    public List<Employee> populateEmployees() {
        return employeeRepository.findAll();
    }

    //////////////////////

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String showAllEmployees() {
        return "employees/employeesList";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String showAddForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employees/employeeAdd";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddForm(@ModelAttribute @Validated Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            return "employees/employeeAdd";
        }
        employee.setPassword(BCrypt.hashpw(employee.getPassword(), BCrypt.gensalt()));
        employeeRepository.save(employee);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable Long id, Model model) {
        if (projectRepository.findAllByEmployeeId(id).size() >= 1) {
            model.addAttribute("deleteErrorProjectExists", "Cannot delete this employee - delete related project first!");
            return "employees/employeesList";
        }
        Employee employeeToDelete = employeeRepository.findFirstById(id);
        employeeRepository.deleteById(id);
        if (employeeToDelete.getAdditionalInfo() != null) {
            Long infoId = employeeToDelete.getAdditionalInfo().getId();
            additionalInfoEmployeeRepository.deleteById(infoId);
        }
        return "redirect:../list";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Employee employee = employeeRepository.findFirstById(id);
        if (employee == null) {
            model.addAttribute("error", "Update Error");
            return "error";
        }
        model.addAttribute("employee", employee);
        return "employees/employeeUpdate";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateForm(@ModelAttribute @Validated Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            return "employees/employeeUpdate";
        }
        employee.setPassword(BCrypt.hashpw(employee.getPassword(), BCrypt.gensalt()));
        employeeRepository.save(employee);
        return "redirect:list";
    }


}
