package pl.qceyco.app.timesheet.referenceUnit;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.qceyco.app.employee.Employee;
import pl.qceyco.app.employee.EmployeeRepository;
import pl.qceyco.app.project.Project;
import pl.qceyco.app.project.ProjectRepository;
import pl.qceyco.app.timesheet.commentary.CommentaryRepository;
import pl.qceyco.app.timesheet.week.TimesheetWeek;
import pl.qceyco.app.timesheet.week.TimesheetWeekRepository;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Controller
@RequestMapping("/timesheets")

public class TimesheetReferenceUnitController {

    private final TimesheetReferenceUnitRepository timesheetReferenceUnitRepository;
    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;
    private final TimesheetWeekRepository timesheetWeekRepository;
    private final CommentaryRepository commentaryRepository;

    public TimesheetReferenceUnitController(TimesheetReferenceUnitRepository timesheetReferenceUnitRepository, ProjectRepository projectRepository, EmployeeRepository employeeRepository, TimesheetWeekRepository timesheetWeekRepository, CommentaryRepository commentaryRepository) {
        this.timesheetReferenceUnitRepository = timesheetReferenceUnitRepository;
        this.projectRepository = projectRepository;
        this.employeeRepository = employeeRepository;
        this.timesheetWeekRepository = timesheetWeekRepository;
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
    public List<TimesheetReferenceUnit> populateTimesheetReferenceUnits() {
        return timesheetReferenceUnitRepository.findAll();
    }

    ///////////////////////

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String showAllTimesheets(@RequestParam(required = false) String mode, @RequestParam(required = false) String mondaySelect,
                                    @RequestParam(required = false) String errorNoTimesheets, HttpSession session, Model model) {
        model.addAttribute("errorNoTimesheets", errorNoTimesheets);
        LocalDate nextMonday = getMondayDate(mode, mondaySelect, 28);
        model.addAttribute("nextMonday", nextMonday);
        Employee employee = (Employee) session.getAttribute("loggedInUser");
        if (employee.getAdmin() == true) {
            return "admin/timesheets/timesheetsList";
        } else {
            List<TimesheetReferenceUnit> timesheets = timesheetReferenceUnitRepository.findAllByEmployeeId(employee.getId());
            model.addAttribute("timesheetReferenceUnitsUser", timesheets);
            List<Project> projectsWhereEmployeeParticipates = projectRepository.findAllByEmployeeId(employee.getId());
            model.addAttribute("projectsWhereEmployeeParticipates", projectsWhereEmployeeParticipates);
            return "user/timesheets/timesheetsList";
        }
    }

    @RequestMapping(value = "/details/{tsRefUId}", method = RequestMethod.GET)
    public String showATimesheetDetails(@PathVariable Long tsRefUId, HttpSession session, Model model) {
        TimesheetReferenceUnit timesheetReferenceUnit = timesheetReferenceUnitRepository.findFirstById(tsRefUId);
        model.addAttribute("timesheetDetails", timesheetReferenceUnit);
        return "timesheets/timesheetDetailsList";
    }


    @RequestMapping(value = "/choose-project", method = RequestMethod.GET)
    public String chooseProject(HttpSession session, Model model) {
        Employee employee = (Employee) session.getAttribute("loggedInUser");
        if (employee.getAdmin() == true) {
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
        TimesheetWeek timesheetWeek = new TimesheetWeek();
        timesheetWeek.setDateMonday(nextMonday);
        model.addAttribute("timesheetWeek", timesheetWeek);
        Project project = projectRepository.findFirstById(projectId);
        model.addAttribute("project", project);
        return "timesheets/timesheetAdd";
    }

    @RequestMapping(value = "/add/{projectId}", method = RequestMethod.POST)
    public String processAddForm(@PathVariable Long projectId, @ModelAttribute @Valid TimesheetWeek timesheetWeek,
                                 BindingResult result, HttpSession session, Model model) {
        if (result.hasErrors()) {
            return "timesheets/timesheetAdd";
        }
        Employee loggedInUser = (Employee) session.getAttribute("loggedInUser");
        if (similarTimesheetExists(projectId, timesheetWeek, model, loggedInUser)) {
            return "timesheets/timesheetAdd";
        }
        TimesheetReferenceUnit timesheetReferenceUnit = setTimesheetReferenceUnitValue(projectId, timesheetWeek, loggedInUser);
        timesheetReferenceUnitRepository.save(timesheetReferenceUnit);
        return "redirect:/timesheets/list";
    }

    @RequestMapping(value = "/delete/{tsRefUId}", method = RequestMethod.GET)
    public String delete(@PathVariable Long tsRefUId) {
        timesheetReferenceUnitRepository.deleteById(tsRefUId);
        return "redirect:/timesheets/list";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable Long id, Model model) {
        TimesheetWeek timesheetWeek = timesheetWeekRepository.findFirstById(id);
        if (timesheetWeek == null) {
            model.addAttribute("error", "Update Error");
            return "error";
        }
        model.addAttribute("timesheetWeek", timesheetWeek);
        return "timesheets/timesheetUpdate";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateForm(@ModelAttribute @Valid TimesheetWeek timesheetWeek, BindingResult result) {
        if (result.hasErrors()) {
            return "timesheets/timesheetUpdate";
        }
        timesheetWeek.setDateMonday(timesheetWeek.getDateMonday().plusDays(1));
        timesheetWeekRepository.save(timesheetWeek);
        return "redirect:list";
    }

    @RequestMapping(value = "/sort-by-project", method = {RequestMethod.POST, RequestMethod.GET})
    public String showTimesheetsByProjectId(@RequestParam Long projectId, @RequestParam(required = false) String mode, @RequestParam(required = false) String mondaySelect,
                                            Model model, HttpSession session) {
        Employee loggedInUser = (Employee) session.getAttribute("loggedInUser");
        if (loggedInUser.getAdmin() == true) {
            List<TimesheetReferenceUnit> timesheets = timesheetReferenceUnitRepository.findAllByProjectIdOrderByEmployeeId(projectId);
            if (timesheets.size() == 0) {
                model.addAttribute("errorNoTimesheets", "Error: There are no timesheets to display!");
                return "redirect:/timesheets/list";
            }
            model.addAttribute("projectId", projectId);
            model.addAttribute("timesheetsChosenProject", timesheets);
            LocalDate nextMonday = getMondayDate(mode, mondaySelect, 28);
            model.addAttribute("nextMonday", nextMonday);
            return "admin/timesheets/timesheetsListOfGivenProject";
        } else {
            List<TimesheetReferenceUnit> timesheets = timesheetReferenceUnitRepository.findAllByProjectIdAndEmployeeId(projectId, loggedInUser.getId());
            if (timesheets.size() == 0) {
                model.addAttribute("errorNoTimesheets", "Error: There are no timesheets to display!");
                return "redirect:/timesheets/list";
            }
            model.addAttribute("timesheets", timesheets);
            return "user/timesheets/timesheetsListOfGivenProject";
        }
    }

    @RequestMapping(value = "/sort-by-employee", method = {RequestMethod.POST, RequestMethod.GET})
    public String showTimesheetsByEmployeeId(@RequestParam Long employeeId, Model model, @RequestParam(required = false) String mode, @RequestParam(required = false) String mondaySelect) {
        List<TimesheetReferenceUnit> timesheets = timesheetReferenceUnitRepository.findAllByEmployeeId(employeeId);
        if (timesheets.size() == 0) {
            model.addAttribute("errorNoTimesheets", "Error: There are no timesheets to display!");
            return "redirect:/timesheets/list";
        }
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

    private TimesheetReferenceUnit setTimesheetReferenceUnitValue(@PathVariable Long projectId, @Valid @ModelAttribute TimesheetWeek timesheetWeek, Employee loggedInUser) {
        timesheetWeek.setDateMonday(timesheetWeek.getDateMonday().plusDays(1));
        timesheetWeekRepository.save(timesheetWeek);
        Project project = projectRepository.findFirstByIdWithProjectTeamMembers(projectId);
        TimesheetReferenceUnit timesheetReferenceUnit = new TimesheetReferenceUnit();
        timesheetReferenceUnit.setTimesheetWeek(timesheetWeek);
        timesheetReferenceUnit.setEmployee(loggedInUser);
        timesheetReferenceUnit.setProject(project);
        return timesheetReferenceUnit;
    }

    private boolean similarTimesheetExists(@PathVariable Long projectId, @Valid @ModelAttribute TimesheetWeek timesheetWeek, Model model, Employee loggedInUser) {
        TimesheetReferenceUnit timesheetSimilarInDB = timesheetReferenceUnitRepository
                .findFirstByEmployeeIdAndProjectIdForSpecificWeek(loggedInUser.getId(), projectId, timesheetWeek.getDateMonday(), timesheetWeek.getDateMonday().plusDays(1));
        if (timesheetSimilarInDB != null) {
            model.addAttribute("errorSimilarTsExists", "Error: there is already existing timesheet for this project and date!");
            model.addAttribute("timesheetSimilarInDB", timesheetSimilarInDB);
            return true;
        }
        return false;
    }

}


