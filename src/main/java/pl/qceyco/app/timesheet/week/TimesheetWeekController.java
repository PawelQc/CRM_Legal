package pl.qceyco.app.timesheet.week;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.qceyco.app.project.Project;
import pl.qceyco.app.project.ProjectRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Controller
@RequestMapping("/timesheet")

public class TimesheetWeekController {

    private final TimesheetWeekRepository timesheetWeekRepository;
    private final ProjectRepository projectRepository;

    public TimesheetWeekController(TimesheetWeekRepository timesheetWeekRepository, ProjectRepository projectRepository) {
        this.timesheetWeekRepository = timesheetWeekRepository;
        this.projectRepository = projectRepository;
    }

    @ModelAttribute("projects")
    public List<Project> populateProjects() {
        return projectRepository.findAllWithProjectTeamMembersAndTimesheets();
    }

    @ModelAttribute("timesheets")
    public List<TimesheetWeek> populateTimesheets() {
        return timesheetWeekRepository.findAll();
    }

    ///////////////////////

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String showAllTimesheets() {
        return "timesheets/timesheetsList";
    }

    @RequestMapping(value = "/choose-project", method = RequestMethod.GET)
    public String chooseProject() {
        return "timesheets/timesheetProjectChoiceList";
    }

    @RequestMapping(value = "/add/{projectId}", method = RequestMethod.GET)
    public String showAddForm(@PathVariable Long projectId, Model model, @RequestParam(required = false)
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
        return "timesheets/timesheetAdd";
    }

    @RequestMapping(value = "/add/{projectId}", method = RequestMethod.POST)
    public String processAddForm(@PathVariable Long projectId, @ModelAttribute @Validated TimesheetWeek timesheetWeek, BindingResult result) {
        if (result.hasErrors()) {
            return "timesheets/timesheetAdd";
        }
        //TODO dzięki +1 nie ma błędnego zapisu(prawdopodobnie problem z różnymi strefami czasowymi)
        timesheetWeek.setDateMonday(timesheetWeek.getDateMonday().plusDays(1));
        timesheetWeekRepository.save(timesheetWeek);
        Project project = projectRepository.findFirstByIdWithProjectTeamMembersAndTimesheets(projectId);
        project.getTimesheets().add(timesheetWeek);
        projectRepository.save(project);
        return "redirect:../list";
    }

    @RequestMapping(value = "/delete/{timeId}/{projectId}", method = RequestMethod.GET)
    public String delete(@PathVariable Long timeId, @PathVariable Long projectId) {
        Project project = projectRepository.findFirstByIdWithProjectTeamMembersAndTimesheets(projectId);
        project.getTimesheets().remove(timesheetWeekRepository.findFirstById(timeId));
        projectRepository.save(project);
        timesheetWeekRepository.deleteById(timeId);
        return "redirect:/timesheet/list";
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
    public String processUpdateForm(@ModelAttribute @Validated TimesheetWeek timesheetWeek, BindingResult result) {
        if (result.hasErrors()) {
            return "timesheets/timesheetUpdate";
        }
        //TODO dzięki +1 nie ma błędnego zapisu(prawdopodobnie problem z różnymi strefami czasowymi)
        timesheetWeek.setDateMonday(timesheetWeek.getDateMonday().plusDays(1));
        timesheetWeekRepository.save(timesheetWeek);
        return "redirect:list";
    }


}
