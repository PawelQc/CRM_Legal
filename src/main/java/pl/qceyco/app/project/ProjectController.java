package pl.qceyco.app.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.qceyco.app.client.Client;
import pl.qceyco.app.client.ClientsAllRepository;
import pl.qceyco.app.employee.Employee;
import pl.qceyco.app.employee.EmployeeRepository;
import pl.qceyco.app.timesheet.referenceUnit.TimesheetReferenceUnit;
import pl.qceyco.app.timesheet.referenceUnit.TimesheetReferenceUnitRepository;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/projects")

public class ProjectController {

    private final ClientsAllRepository clientsAllRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final TimesheetReferenceUnitRepository timesheetReferenceUnitRepository;

    public ProjectController(ClientsAllRepository clientsAllRepository, EmployeeRepository employeeRepository, ProjectRepository projectRepository, TimesheetReferenceUnitRepository timesheetReferenceUnitRepository) {
        this.clientsAllRepository = clientsAllRepository;
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.timesheetReferenceUnitRepository = timesheetReferenceUnitRepository;
    }

    @ModelAttribute("clients")
    public List<Client> populateClients() {
        return clientsAllRepository.findAll();
    }

    @ModelAttribute("employees")
    public List<Employee> populateEmployees() {
        return employeeRepository.findAll();
    }

    @ModelAttribute("projects")
    public List<Project> populateProjects() {
        return projectRepository.findAllWithProjectTeamMembers();
    }

    //////////////////////

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String showAllProjects(HttpSession session) {
        Employee employee = (Employee) session.getAttribute("loggedInUser");
        if (employee.getAdmin() == true) {
            return "admin/projects/projectsList";
        } else {
            return "user/projects/projectsList";
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String showAddForm(Model model) {
        model.addAttribute("project", new Project());
        return "admin/projects/projectAdd";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddForm(@ModelAttribute @Valid Project project, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/projects/projectAdd";
        }
        if (project.getClient().getId() == null) {
            model.addAttribute("errorClientChoice", "choose a client");
            return "admin/projects/projectAdd";
        }
        projectRepository.save(project);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{projectId}", method = RequestMethod.GET)
    public String delete(@PathVariable Long projectId) {
        List <TimesheetReferenceUnit> timesheets = timesheetReferenceUnitRepository.findAllByProjectId(projectId);
        for (TimesheetReferenceUnit t : timesheets) {
            timesheetReferenceUnitRepository.delete(t);
        }
        projectRepository.deleteById(projectId);
        return "redirect:../list";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Project project = projectRepository.findFirstByIdWithProjectTeamMembers(id);
        if (project == null) {
            model.addAttribute("error", "Update Error");
            return "error";
        }
        model.addAttribute("project", project);
        return "admin/projects/projectUpdate";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateForm(@ModelAttribute @Valid Project project, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/projects/projectUpdate";
        }
        if (project.getClient().getId() == null) {
            model.addAttribute("errorClientChoice", "choose a client");
            return "admin/projects/projectUpdate";
        }
        projectRepository.save(project);
        return "redirect:list";
    }


}
