package pl.qceyco.app.timesheet.unit;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.qceyco.app.employee.Employee;
import pl.qceyco.app.employee.EmployeeRepository;
import pl.qceyco.app.project.Project;
import pl.qceyco.app.project.ProjectRepository;
import pl.qceyco.app.timesheet.workWeek.commentary.CommentaryRepository;
import pl.qceyco.app.timesheet.workWeek.WorkWeek;
import pl.qceyco.app.timesheet.workWeek.WorkWeekRepository;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Controller
@RequestMapping("/timesheets")

public class TimesheetUnitController {

    private final TimesheetUnitRepository timesheetUnitRepository;
    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;
    private final WorkWeekRepository workWeekRepository;
    private final CommentaryRepository commentaryRepository;

    public TimesheetUnitController(TimesheetUnitRepository timesheetUnitRepository, ProjectRepository projectRepository,
                                   EmployeeRepository employeeRepository, WorkWeekRepository workWeekRepository, CommentaryRepository commentaryRepository) {
        this.timesheetUnitRepository = timesheetUnitRepository;
        this.projectRepository = projectRepository;
        this.employeeRepository = employeeRepository;
        this.workWeekRepository = workWeekRepository;
        this.commentaryRepository = commentaryRepository;
    }

    @ModelAttribute("projects")
    public List<Project> populateProjects() {
        return projectRepository.findAllWithProjectTeamMembers();
    }

    @ModelAttribute("employees")
    public List<Employee> populateEmployees() {
        return employeeRepository.findAll();
    }

    @ModelAttribute("timesheetReferenceUnitsAll")
    public List<TimesheetUnit> populateTimesheetReferenceUnits() {
        return timesheetUnitRepository.findAll();
    }

    ///////////////////////

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String showAllTimesheets(@RequestParam(required = false) String mode, @RequestParam(required = false) String mondaySelect,
                                    @RequestParam(required = false) String errorNoTimesheets, HttpSession session, Model model) {
        model.addAttribute("errorNoTimesheets", errorNoTimesheets);
        LocalDate nextMonday = getMondayDate(mode, mondaySelect, 28);
        model.addAttribute("nextMonday", nextMonday);
        Employee employee = (Employee) session.getAttribute("loggedInUser");
        if (employee.getAdmin()) {
            return "admin/timesheets/timesheetsList";
        } else {
            List<TimesheetUnit> timesheets = timesheetUnitRepository.findAllByEmployeeId(employee.getId());
            model.addAttribute("timesheetReferenceUnitsUser", timesheets);
            List<Project> projectsWhereEmployeeParticipates = projectRepository.findAllByEmployeeId(employee.getId());
            model.addAttribute("projectsWhereEmployeeParticipates", projectsWhereEmployeeParticipates);
            return "user/timesheets/timesheetsList";
        }
    }

    @RequestMapping(value = "/details/{tsRefUId}", method = RequestMethod.GET)
    public String showATimesheetDetails(@PathVariable Long tsRefUId, HttpSession session, Model model) {
        TimesheetUnit timesheetUnit = timesheetUnitRepository.findFirstById(tsRefUId);
        model.addAttribute("timesheetDetails", timesheetUnit);
        return "timesheets/timesheetDetailsList";
    }

    @RequestMapping(value = "/choose-project", method = RequestMethod.GET)
    public String chooseProject(HttpSession session, Model model) {
        Employee employee = (Employee) session.getAttribute("loggedInUser");
        if (employee.getAdmin()) {
            return "admin/timesheets/timesheetProjectChoiceList";
        } else {
            List<Project> projectsWhereEmployeeParticipates = projectRepository.findAllByEmployeeId(employee.getId());
            model.addAttribute("projectsWhereEmployeeParticipates", projectsWhereEmployeeParticipates);
            return "user/timesheets/timesheetProjectChoiceList";
        }
    }

    @RequestMapping(value = "/add/{projectId}", method = RequestMethod.GET)
    public String showAddForm(@PathVariable Long projectId, Model model, @RequestParam(required = false) String mode,
                              @RequestParam(required = false) String mondaySelect) {
        LocalDate nextMonday = getMondayDate(mode, mondaySelect, 7);
        WorkWeek workWeek = new WorkWeek();
        workWeek.setDateMonday(nextMonday);
        model.addAttribute("timesheetWeek", workWeek);
        Project project = projectRepository.findFirstById(projectId);
        model.addAttribute("project", project);
        return "timesheets/timesheetAdd";
    }

    @RequestMapping(value = "/add/{projectId}", method = RequestMethod.POST)
    public String processAddForm(@PathVariable Long projectId, @ModelAttribute @Valid WorkWeek workWeek,
                                 BindingResult result, HttpSession session, Model model) {
        if (result.hasErrors()) {
            return "timesheets/timesheetAdd";
        }
        Employee loggedInUser = (Employee) session.getAttribute("loggedInUser");
        if (similarTimesheetExists(projectId, workWeek, model, loggedInUser)) {
            Project project = projectRepository.findFirstById(projectId);
            model.addAttribute("project", project);
            return "timesheets/timesheetAdd";
        }
        TimesheetUnit timesheetUnit = setTimesheetReferenceUnitValue(projectId, workWeek, loggedInUser);
        timesheetUnitRepository.save(timesheetUnit);
        return "redirect:/timesheets/list";
    }

    @RequestMapping(value = "/delete/{tsRefUId}", method = RequestMethod.GET)
    public String delete(@PathVariable Long tsRefUId) {
        timesheetUnitRepository.deleteById(tsRefUId);
        return "redirect:/timesheets/list";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable Long id, Model model) {
        WorkWeek workWeek = workWeekRepository.findFirstById(id);
        if (checkIfRecordExist(model, workWeek == null, "error", "Update Error")) return "error";
        model.addAttribute("timesheetWeek", workWeek);
        return "timesheets/timesheetUpdate";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateForm(@ModelAttribute @Valid WorkWeek workWeek, BindingResult result) {
        if (result.hasErrors()) {
            return "timesheets/timesheetUpdate";
        }
        workWeek.setDateMonday(workWeek.getDateMonday().plusDays(1));
        workWeekRepository.save(workWeek);
        return "redirect:list";
    }

    @RequestMapping(value = "/sort-by-project", method = {RequestMethod.POST, RequestMethod.GET})
    public String showTimesheetsByProjectId(@RequestParam Long projectId, @RequestParam(required = false) String mode, @RequestParam(required = false) String mondaySelect,
                                            Model model, HttpSession session) {
        Employee loggedInUser = (Employee) session.getAttribute("loggedInUser");
        if (loggedInUser.getAdmin()) {
            List<TimesheetUnit> timesheets = timesheetUnitRepository.findAllByProjectIdOrderByEmployeeId(projectId);
            if (checkIfRecordExist(model, timesheets.size() == 0, "errorNoTimesheets", "Error: There are no timesheets to display!"))
                return "redirect:/timesheets/list";
            model.addAttribute("projectId", projectId);
            model.addAttribute("timesheetsChosenProject", timesheets);
            LocalDate nextMonday = getMondayDate(mode, mondaySelect, 28);
            model.addAttribute("nextMonday", nextMonday);
            return "admin/timesheets/timesheetsListOfGivenProject";
        } else {
            List<TimesheetUnit> timesheets = timesheetUnitRepository.findAllByProjectIdAndEmployeeId(projectId, loggedInUser.getId());
            if (checkIfRecordExist(model, timesheets.size() == 0, "errorNoTimesheets", "Error: There are no timesheets to display!"))
                return "redirect:/timesheets/list";
            model.addAttribute("timesheets", timesheets);
            return "user/timesheets/timesheetsListOfGivenProject";
        }
    }

    @RequestMapping(value = "/sort-by-employee", method = {RequestMethod.POST, RequestMethod.GET})
    public String showTimesheetsByEmployeeId(@RequestParam Long employeeId, Model model, @RequestParam(required = false) String mode, @RequestParam(required = false) String mondaySelect) {
        List<TimesheetUnit> timesheets = timesheetUnitRepository.findAllByEmployeeId(employeeId);
        if (checkIfRecordExist(model, timesheets.size() == 0, "errorNoTimesheets", "Error: There are no timesheets to display!"))
            return "redirect:/timesheets/list";
        model.addAttribute("employeeId", employeeId);
        model.addAttribute("timesheetsChosenUser", timesheets);
        LocalDate nextMonday = getMondayDate(mode, mondaySelect, 28);
        model.addAttribute("nextMonday", nextMonday);
        return "admin/timesheets/timesheetsListOfGivenEmployee";
    }

    ////////////////////////////////////////////////

    private LocalDate getMondayDate(@RequestParam(required = false) String mode, @RequestParam(required = false) String mondaySelect, int i) {
        LocalDate nextMonday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        if (mondaySelect != null) {
            nextMonday = LocalDate.parse(mondaySelect);
        }
        if ("prev".equals(mode)) {
            nextMonday = nextMonday.minusDays(i);
        }
        if ("next".equals(mode)) {
            nextMonday = nextMonday.plusDays(i);
        }
        return nextMonday;
    }

    private TimesheetUnit setTimesheetReferenceUnitValue(@PathVariable Long projectId, @Valid @ModelAttribute WorkWeek workWeek, Employee loggedInUser) {
        workWeek.setDateMonday(workWeek.getDateMonday().plusDays(1));
        workWeekRepository.save(workWeek);
        Project project = projectRepository.findFirstByIdWithProjectTeamMembers(projectId);
        TimesheetUnit timesheetUnit = new TimesheetUnit();
        timesheetUnit.setWorkWeek(workWeek);
        timesheetUnit.setEmployee(loggedInUser);
        timesheetUnit.setProject(project);
        return timesheetUnit;
    }

    private boolean similarTimesheetExists(@PathVariable Long projectId, @Valid @ModelAttribute WorkWeek workWeek, Model model, Employee loggedInUser) {
        TimesheetUnit timesheetSimilarInDB = timesheetUnitRepository
                .findFirstByEmployeeIdAndProjectIdForSpecificWeek(loggedInUser.getId(), projectId, workWeek.getDateMonday(), workWeek.getDateMonday().plusDays(1));
        if (timesheetSimilarInDB != null) {
            model.addAttribute("errorSimilarTsExists", "Error: there is already existing timesheet for this project and date!");
            model.addAttribute("timesheetSimilarInDB", timesheetSimilarInDB);
            return true;
        }
        return false;
    }

    private boolean checkIfRecordExist(Model model, boolean condition, String errorNoTimesheets, String info) {
        if (condition) {
            model.addAttribute(errorNoTimesheets, info);
            return true;
        }
        return false;
    }

}


