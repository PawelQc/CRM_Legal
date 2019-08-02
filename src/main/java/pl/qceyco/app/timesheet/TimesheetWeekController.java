package pl.qceyco.app.timesheet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.qceyco.app.legalcase.LegalCase;
import pl.qceyco.app.legalcase.LegalCaseRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Controller
@RequestMapping("/timesheet")

public class TimesheetWeekController {

    private final TimesheetWeekRepository timesheetWeekRepository;
    private final LegalCaseRepository legalCaseRepository;

    public TimesheetWeekController(TimesheetWeekRepository timesheetWeekRepository, LegalCaseRepository legalCaseRepository) {
        this.timesheetWeekRepository = timesheetWeekRepository;
        this.legalCaseRepository = legalCaseRepository;
    }

    @ModelAttribute("legalCases")
    public List<LegalCase> populateCases() {
        return legalCaseRepository.findAllCasesWithProjectTeamMembersAndTimesheets();
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

    @RequestMapping(value = "/choose-case", method = RequestMethod.GET)
    public String chooseCase() {
        return "timesheets/timesheetCaseChoiceList";
    }

    @RequestMapping(value = "/add/{caseId}", method = RequestMethod.GET)
    public String showAddForm(@PathVariable Long caseId, Model model, @RequestParam(required = false)
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

    @RequestMapping(value = "/add/{caseId}", method = RequestMethod.POST)
    public String processAddForm(@PathVariable Long caseId, @ModelAttribute @Validated TimesheetWeek timesheetWeek, BindingResult result) {
        if (result.hasErrors()) {
            return "timesheets/timesheetAdd";
        }
        //TODO dzięki +1 nie ma błędnego zapisu(prawdopodobnie problem z różnymi strefami czasowymi)
        timesheetWeek.setDateMonday(timesheetWeek.getDateMonday().plusDays(1));
        timesheetWeekRepository.save(timesheetWeek);
        LegalCase legalCase = legalCaseRepository.findCaseWithProjectTeamMembersAndTimesheets(caseId);
        legalCase.getTimesheets().add(timesheetWeek);
        legalCaseRepository.save(legalCase);
        return "redirect:../list";
    }

    @RequestMapping(value = "/delete/{timeId}/{caseId}", method = RequestMethod.GET)
    public String delete(@PathVariable Long timeId, @PathVariable Long caseId) {
        LegalCase legalCase = legalCaseRepository.findCaseWithProjectTeamMembersAndTimesheets(caseId);
        legalCase.getTimesheets().remove(timesheetWeekRepository.findFirstById(timeId));
        legalCaseRepository.save(legalCase);
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
