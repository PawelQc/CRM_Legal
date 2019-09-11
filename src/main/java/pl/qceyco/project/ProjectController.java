package pl.qceyco.project;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.qceyco.client.Client;
import pl.qceyco.employee.Employee;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/projects")

public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @ModelAttribute("clients")
    public List<Client> populateClients() {
        return projectService.getAllClients();
    }

    @ModelAttribute("employees")
    public List<Employee> populateEmployees() {
        return projectService.getAllEmployees();
    }

    @ModelAttribute("projects")
    public List<Project> populateProjects() {
        return projectService.getAllProjects();
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String showAllProjects(HttpSession session) {
        Employee employee = (Employee) session.getAttribute("loggedInUser");
        if (employee.getAdmin()) {
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
        projectService.save(project);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{projectId}", method = RequestMethod.GET)
    public String delete(@PathVariable Long projectId) {
        projectService.delete(projectId);
        return "redirect:../list";
    }

    @RequestMapping(value = "/update/{projectId}", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable Long projectId, Model model) {
        Project project = projectService.getProjectById(projectId);
        if (project == null) {
            model.addAttribute("error", "Error: something went wrong... Cause: problem with update - wrong id");
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
        projectService.save(project);
        return "redirect:list";
    }


}
