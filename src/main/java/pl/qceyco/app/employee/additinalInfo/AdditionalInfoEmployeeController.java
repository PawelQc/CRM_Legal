package pl.qceyco.app.employee.additinalInfo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.qceyco.app.client.Client;
import pl.qceyco.app.client.additionalInfo.AdditionalInfoClient;
import pl.qceyco.app.employee.Employee;
import pl.qceyco.app.employee.EmployeeRepository;

@Controller
@RequestMapping("/employees/additional-info")

public class AdditionalInfoEmployeeController {

    private final AdditionalInfoEmployeeRepository additionalInfoEmployeeRepository;
    private final EmployeeRepository employeeRepository;

    public AdditionalInfoEmployeeController(AdditionalInfoEmployeeRepository additionalInfoEmployeeRepository, EmployeeRepository employeeRepository) {
        this.additionalInfoEmployeeRepository = additionalInfoEmployeeRepository;
        this.employeeRepository = employeeRepository;
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String showDetails(@RequestParam Long employeeId, @RequestParam(required = false) Long additionalInfoId, Model model) {
        if (additionalInfoId == null) {
            return "redirect:/employees/additional-info/add/" + employeeId;
        }
        AdditionalInfoEmployee additionalInfoEmployee = additionalInfoEmployeeRepository.findFirstById(additionalInfoId);
        model.addAttribute("additionalInfoEmployee", additionalInfoEmployee);
        return "employees/detailedInfo/employeeDetailsList";
    }

    @RequestMapping(value = "/add/{employeeId}", method = RequestMethod.GET)
    public String showAddForm(@PathVariable Long employeeId, Model model) {
        Employee employee = employeeRepository.findFirstById(employeeId);
        model.addAttribute("employee", employee);
        model.addAttribute("additionalInfoEmployee", new AdditionalInfoEmployee());
        return "employees/detailedInfo/employeeDetailsAdd";
    }

    @RequestMapping(value = "/add/{employeeId}", method = RequestMethod.POST)
    public String processAddForm(@PathVariable Long employeeId, @ModelAttribute @Validated AdditionalInfoEmployee additionalInfoEmployee, BindingResult result) {
        if (result.hasErrors()) {
            return "employees/detailedInfo/employeeDetailsAdd";
        }
        additionalInfoEmployeeRepository.save(additionalInfoEmployee);
        Employee employee = employeeRepository.findFirstById(employeeId);
        employee.setAdditionalInfo(additionalInfoEmployee);
        employeeRepository.save(employee);
        return "redirect:/employees/list";
    }

    @RequestMapping(value = "/update/{infoId}", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable Long infoId, Model model) {
        AdditionalInfoEmployee additionalInfoEmployee = additionalInfoEmployeeRepository.findFirstById(infoId);
        if (additionalInfoEmployee == null) {
            model.addAttribute("error", "Update Error");
            return "error";
        }
        model.addAttribute("additionalInfoEmployee", additionalInfoEmployee);
        return "employees/detailedInfo/employeeDetailsUpdate";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateForm(@ModelAttribute @Validated AdditionalInfoEmployee additionalInfoEmployee, BindingResult result) {
        if (result.hasErrors()) {
            return "employees/detailedInfo/employeeDetailsUpdate";
        }
        additionalInfoEmployeeRepository.save(additionalInfoEmployee);
        return "redirect:/employees/list";
    }


}
