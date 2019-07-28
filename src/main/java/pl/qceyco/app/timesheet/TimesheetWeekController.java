package pl.qceyco.app.timesheet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDate;
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
    public String showAddForm(Model model) {
        //TODO tutaj na sztywno ustalam datę - do zmiany
        TimesheetWeek timesheetWeek = new TimesheetWeek();
        LocalDate monday = LocalDate.now().plusDays(1);
        timesheetWeek.setDateMonday(monday);
        model.addAttribute("timesheetWeek", timesheetWeek);
        return "timesheets/timesheetAdd";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddForm(@ModelAttribute @Validated TimesheetWeek timesheetWeek, BindingResult result) {
        if (result.hasErrors()) {
            return "timesheets/timesheetAdd";
        }
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
        timesheetWeekRepository.save(timesheetWeek);
        return "redirect:list";
    }


}
