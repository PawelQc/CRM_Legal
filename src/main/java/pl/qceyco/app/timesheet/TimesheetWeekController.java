package pl.qceyco.app.timesheet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Controller
@RequestMapping("/timesheet")

public class TimesheetWeekController {

    private final TimesheetWeekRepository timesheetWeekRepository;

    public TimesheetWeekController(TimesheetWeekRepository timesheetWeekRepository) {
        this.timesheetWeekRepository = timesheetWeekRepository;
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

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String showAddForm(Model model, @RequestParam(required = false) String mode, @RequestParam(required = false) String mondaySelect) {
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

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddForm(@ModelAttribute @Validated TimesheetWeek timesheetWeek, BindingResult result) {
        if (result.hasErrors()) {
            return "timesheets/timesheetAdd";
        }
        //TODO dzięki +1 nie ma błędnego zapisu(prawdopodobnie problem z różnymi strefami czasowymi)
        timesheetWeek.setDateMonday(timesheetWeek.getDateMonday().plusDays(1));
        timesheetWeekRepository.save(timesheetWeek);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable Long id) {
        timesheetWeekRepository.deleteById(id);
        return "redirect:../list";
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
