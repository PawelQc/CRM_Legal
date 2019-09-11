package pl.qceyco.timesheet.unit;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.qceyco.employee.Employee;
import pl.qceyco.project.Project;
import pl.qceyco.timesheet.workWeek.WorkWeek;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/timesheets")

public class TimesheetUnitController {

    private final TimesheetUnitService timesheetUnitService;

    public TimesheetUnitController(TimesheetUnitService timesheetUnitService) {
        this.timesheetUnitService = timesheetUnitService;
    }

    @ModelAttribute("projects")
    public List<Project> populateProjects() {
        return timesheetUnitService.getAllProjects();
    }

    @ModelAttribute("employees")
    public List<Employee> populateEmployees() {
        return timesheetUnitService.getAllEmployees();
    }

    @ModelAttribute("timesheetsAll")
    public List<TimesheetUnit> populateTimesheetUnits() {
        return timesheetUnitService.getAllTimesheets();
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String showAllTimesheets(@RequestParam(required = false) String mode, @RequestParam(required = false) String mondaySelect,
                                    @RequestParam(required = false) String errorNoTimesheets, HttpSession session, Model model) {
        model.addAttribute("errorNoTimesheets", errorNoTimesheets);
        LocalDate nextMonday = timesheetUnitService.getMondayDate(mode, mondaySelect, 28);
        model.addAttribute("nextMonday", nextMonday);
        Employee loggedInUser = (Employee) session.getAttribute("loggedInUser");
        if (loggedInUser.getAdmin()) {
            return "admin/timesheets/timesheetsList";
        } else {
            List<TimesheetUnit> timesheets = timesheetUnitService.getUserTimesheets(loggedInUser.getId());
            model.addAttribute("timesheetsUser", timesheets);
            List<Project> projectsWhereEmployeeParticipates = timesheetUnitService.getUserProjects(loggedInUser.getId());
            model.addAttribute("projectsUser", projectsWhereEmployeeParticipates);
            return "user/timesheets/timesheetsList";
        }
    }

    @RequestMapping(value = "/details/{timesheetId}", method = RequestMethod.GET)
    public String showATimesheetDetails(@PathVariable Long timesheetId, Model model) {
        TimesheetUnit timesheetUnit = timesheetUnitService.getTimesheetById(timesheetId);
        model.addAttribute("timesheetDetails", timesheetUnit);
        return "timesheets/timesheetDetailsList";
    }

    @RequestMapping(value = "/choose-project", method = RequestMethod.GET)
    public String chooseProject(HttpSession session, Model model) {
        Employee loggedInUser = (Employee) session.getAttribute("loggedInUser");
        if (loggedInUser.getAdmin()) {
            return "admin/timesheets/timesheetProjectChoiceList";
        } else {
            List<Project> projectsWhereEmployeeParticipates = timesheetUnitService.getUserProjects(loggedInUser.getId());
            model.addAttribute("projectsUser", projectsWhereEmployeeParticipates);
            return "user/timesheets/timesheetProjectChoiceList";
        }
    }

    @RequestMapping(value = "/add/{projectId}", method = RequestMethod.GET)
    public String showAddForm(@PathVariable Long projectId, Model model, @RequestParam(required = false) String mode,
                              @RequestParam(required = false) String mondaySelect) {
        LocalDate nextMonday = timesheetUnitService.getMondayDate(mode, mondaySelect, 7);
        WorkWeek workWeek = new WorkWeek();
        workWeek.setDateMonday(nextMonday);
        model.addAttribute("workWeek", workWeek);
        Project project = timesheetUnitService.getProjectById(projectId);
        model.addAttribute("project", project);
        return "timesheets/timesheetAdd";
    }

    @RequestMapping(value = "/add/{projectId}", method = RequestMethod.POST)
    public String processAddForm(@PathVariable Long projectId, @ModelAttribute @Valid WorkWeek workWeek,
                                 BindingResult result, HttpSession session, Model model) {
        if (result.hasErrors()) return "timesheets/timesheetAdd";
        Employee loggedInUser = (Employee) session.getAttribute("loggedInUser");
        if (timesheetUnitService.similarTimesheetExists(projectId, workWeek, model, loggedInUser))
            return "timesheets/timesheetAdd";
        TimesheetUnit timesheetUnit = timesheetUnitService.setTimesheetUnitValues(projectId, workWeek, loggedInUser);
        timesheetUnitService.saveAdd(timesheetUnit);
        return "redirect:/timesheets/list";
    }

    @RequestMapping(value = "/delete/{timesheetId}", method = RequestMethod.GET)
    public String delete(@PathVariable Long timesheetId) {
        timesheetUnitService.deleteById(timesheetId);
        return "redirect:/timesheets/list";
    }

    @RequestMapping(value = "/update/{workWeekId}", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable Long workWeekId, Model model) {
        WorkWeek workWeek = timesheetUnitService.getWorkWeekById(workWeekId);
        if (timesheetUnitService.noWorkWeekInDB(model, workWeek)) return "error";
        model.addAttribute("workWeek", workWeek);
        return "timesheets/timesheetUpdate";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateForm(@ModelAttribute @Valid WorkWeek workWeek, BindingResult result) {
        if (result.hasErrors()) return "timesheets/timesheetUpdate";
        workWeek.setDateMonday(workWeek.getDateMonday().plusDays(1));
        timesheetUnitService.saveUpdate(workWeek);
        return "redirect:list";
    }

    @RequestMapping(value = "/sort-by-project", method = {RequestMethod.POST, RequestMethod.GET})
    public String showTimesheetsByProjectId(@RequestParam Long projectId, @RequestParam(required = false) String mode, @RequestParam(required = false) String mondaySelect,
                                            Model model, HttpSession session) {
        Employee loggedInUser = (Employee) session.getAttribute("loggedInUser");
        if (loggedInUser.getAdmin()) {
            List<TimesheetUnit> timesheets = timesheetUnitService.getAllTimesheetsOfGivenProject(projectId);
            if (timesheetUnitService.noTimesheetInDB(timesheets, model)) return "redirect:/timesheets/list";
            model.addAttribute("projectId", projectId);
            model.addAttribute("timesheetsChosenProject", timesheets);
            LocalDate nextMonday = timesheetUnitService.getMondayDate(mode, mondaySelect, 28);
            model.addAttribute("nextMonday", nextMonday);
            return "admin/timesheets/timesheetsListOfGivenProject";
        } else {
            List<TimesheetUnit> timesheets = timesheetUnitService.getUserTimesheetsOfGivenProject(projectId, loggedInUser.getId());
            if (timesheetUnitService.noTimesheetInDB(timesheets, model)) return "redirect:/timesheets/list";
            model.addAttribute("timesheets", timesheets);
            return "user/timesheets/timesheetsListOfGivenProject";
        }
    }

    @RequestMapping(value = "/sort-by-employee", method = {RequestMethod.POST, RequestMethod.GET})
    public String showTimesheetsByEmployeeId(@RequestParam Long employeeId, Model model, @RequestParam(required = false) String mode, @RequestParam(required = false) String mondaySelect) {
        List<TimesheetUnit> timesheets = timesheetUnitService.getUserTimesheets(employeeId);
        if (timesheetUnitService.noTimesheetInDB(timesheets, model)) return "redirect:/timesheets/list";
        model.addAttribute("employeeId", employeeId);
        model.addAttribute("timesheetsChosenUser", timesheets);
        LocalDate nextMonday = timesheetUnitService.getMondayDate(mode, mondaySelect, 28);
        model.addAttribute("nextMonday", nextMonday);
        return "admin/timesheets/timesheetsListOfGivenEmployee";
    }


}


