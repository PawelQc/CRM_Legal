package pl.qceyco.app.timesheet.referenceUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.qceyco.app.employee.Employee;
import pl.qceyco.app.employee.EmployeeRepository;
import pl.qceyco.app.project.Project;
import pl.qceyco.app.project.ProjectRepository;
import pl.qceyco.app.timesheet.week.TimesheetWeek;
import pl.qceyco.app.timesheet.week.TimesheetWeekRepository;

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

    public TimesheetReferenceUnitController(TimesheetReferenceUnitRepository timesheetReferenceUnitRepository, ProjectRepository projectRepository, EmployeeRepository employeeRepository, TimesheetWeekRepository timesheetWeekRepository) {
        this.timesheetReferenceUnitRepository = timesheetReferenceUnitRepository;
        this.projectRepository = projectRepository;
        this.employeeRepository = employeeRepository;
        this.timesheetWeekRepository = timesheetWeekRepository;
    }

    @ModelAttribute("projects")
    public List<Project> populateProjects() {
        return projectRepository.findAllWithProjectTeamMembers();
    }

    @ModelAttribute("employees")
    public List<Employee> populateEmployees() {
        return employeeRepository.findAll();
    }

    @ModelAttribute("timesheetReferenceUnits")
    public List<TimesheetReferenceUnit> populateTimesheetReferenceUnits() {
        return timesheetReferenceUnitRepository.findAll();
    }

    ///////////////////////

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String showAllTimesheets() {
        return "timesheetsNEW/timesheetsList";
    }

    @RequestMapping(value = "/choose-project", method = RequestMethod.GET)
    public String chooseProject() {
        return "timesheetsNEW/timesheetProjectChoiceList";
    }

    @RequestMapping(value = "/choose-employee/{projectId}", method = RequestMethod.GET)
    public String chooseEmployee(@PathVariable Long projectId, Model model) {
        Project project = projectRepository.findFirstByIdWithProjectTeamMembers(projectId);
        model.addAttribute("project", project);
        return "timesheetsNEW/timesheetEmployeeChoiceList";
    }

    @RequestMapping(value = "/add/{projectId}/{employeeId}", method = RequestMethod.GET)
    public String showAddForm(@PathVariable Long projectId, @PathVariable Long employeeId, Model model, @RequestParam(required = false)
            String mode, @RequestParam(required = false) String mondaySelect) {
        LocalDate monday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        if (mondaySelect != null) {
            monday = LocalDate.parse(mondaySelect);
        }
        if ("prev".equals(mode)) {
            monday = monday.minusDays(7);
        }
        if ("next".equals(mode)) {
            monday = monday.plusDays(7);
        }
        TimesheetWeek timesheetWeek = new TimesheetWeek();
        timesheetWeek.setDateMonday(monday);
        model.addAttribute("timesheetWeek", timesheetWeek);
        return "timesheetsNEW/timesheetAdd";
    }

    @RequestMapping(value = "/add/{projectId}/{employeeId}", method = RequestMethod.POST)
    public String processAddForm(@PathVariable Long projectId, @PathVariable Long employeeId, @ModelAttribute @Validated TimesheetWeek timesheetWeek, BindingResult result) {
        if (result.hasErrors()) {
            return "timesheetsNEW/timesheetAdd";
        }
        //TODO dzięki +1 nie ma błędnego zapisu(prawdopodobnie problem z różnymi strefami czasowymi)
        timesheetWeek.setDateMonday(timesheetWeek.getDateMonday().plusDays(1));
        timesheetWeekRepository.save(timesheetWeek);
        Employee employee = employeeRepository.findFirstById(employeeId);
        Project project = projectRepository.findFirstByIdWithProjectTeamMembers(projectId);
        TimesheetReferenceUnit timesheetReferenceUnit = new TimesheetReferenceUnit();
        timesheetReferenceUnit.setTimesheetWeek(timesheetWeek);
        timesheetReferenceUnit.setEmployee(employee);
        timesheetReferenceUnit.setProject(project);
        timesheetReferenceUnitRepository.save(timesheetReferenceUnit);
        return "redirect:/timesheets/list";
    }

    @RequestMapping(value = "/delete/{tsRefUId}/{tsWeekId}", method = RequestMethod.GET)
    public String delete(@PathVariable Long tsRefUId, @PathVariable Long tsWeekId) {
        timesheetReferenceUnitRepository.deleteById(tsRefUId);
        timesheetWeekRepository.deleteById(tsWeekId);
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
        return "timesheetsNEW/timesheetUpdate";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateForm(@ModelAttribute @Validated TimesheetWeek timesheetWeek, BindingResult result) {
        if (result.hasErrors()) {
            return "timesheetsNEW/timesheetUpdate";
        }
        //TODO dzięki +1 nie ma błędnego zapisu(prawdopodobnie problem z różnymi strefami czasowymi)
        timesheetWeek.setDateMonday(timesheetWeek.getDateMonday().plusDays(1));
        timesheetWeekRepository.save(timesheetWeek);
        return "redirect:list";
    }

    @RequestMapping(value = "/sort-by-project", method = RequestMethod.POST)
    public String showTimesheetsByProjectId(@RequestParam Long projectId, Model model) {
        List<TimesheetReferenceUnit> timesheets = timesheetReferenceUnitRepository.findAllByProjectIdOrderByEmployeeId(projectId);
        model.addAttribute("timesheets", timesheets);
        return "timesheetsNEW/timesheetsListOfGivenProject";
    }

    @RequestMapping(value = "/sort-by-employee", method = RequestMethod.POST)
    public String showTimesheetsByEmployeeId(@RequestParam Long employeeId, Model model) {
        List<TimesheetReferenceUnit> timesheets = timesheetReferenceUnitRepository.findAllByEmployeeIdOrderByProjectId(employeeId);
        model.addAttribute("timesheets", timesheets);
        return "timesheetsNEW/timesheetsListOfGivenEmployee";
    }



}
