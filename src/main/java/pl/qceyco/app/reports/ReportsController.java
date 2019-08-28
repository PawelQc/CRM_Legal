package pl.qceyco.app.reports;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.qceyco.app.client.Client;
import pl.qceyco.app.employee.Employee;
import pl.qceyco.app.project.Project;
import pl.qceyco.app.timesheet.unit.TimesheetUnit;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/reports")

public class ReportsController {

    private final ReportsService reportsService;

    public ReportsController(ReportsService reportsService) {
        this.reportsService = reportsService;
    }

    @ModelAttribute("projects")
    public List<Project> populateProjects() {
        return reportsService.getAllBillableProjects();
    }

    @ModelAttribute("employees")
    public List<Employee> populateEmployees() {
        return reportsService.getAllEmployees();
    }

    @ModelAttribute("clients")
    public List<Client> populateClients() {
        return reportsService.getAllClients();
    }

    @ModelAttribute("timesheetUnitsAll")
    public List<TimesheetUnit> populateTimesheetUnits() {
        return reportsService.getAllTimesheets();
    }

    @RequestMapping(value = "/options", method = RequestMethod.GET)
    public String showReportOptions() {
        return "reports/reportOptions";
    }

    @RequestMapping(value = "/monthly-employee-report/form", method = RequestMethod.GET)
    public String showMonthlyEmployeeReportForm() {
        return "reports/employeeReport/reportEmployeeReportForm";
    }

    @RequestMapping(value = "/project-report/form", method = RequestMethod.GET)
    public String showProjectReportForm() {
        return "reports/projectReport/reportProjectReportForm";
    }

    @RequestMapping(value = "/invoice-preview/form", method = RequestMethod.GET)
    public String showInvoicePreviewForm() {
        return "reports/invoicePreview/invoicePreviewForm";
    }

    @RequestMapping(value = "/monthly-employee-report/process", method = RequestMethod.POST)
    public String processMonthlyEmployeeReport(@RequestParam Long employeeId, @RequestParam String startDate, Model model) {
        if (employeeId == null || StringUtils.isBlank(startDate)) {
            model.addAttribute("errorNotSufficientData", "Error: Indicate all data requested in order to generate a report!");
            return "reports/employeeReport/reportEmployeeReportForm";
        }
        LocalDate selectedMonday = LocalDate.parse(startDate);
        LocalDate firstMondayInMonth = reportsService.getFirstMondayOfMonth(selectedMonday);
        if (!selectedMonday.equals(firstMondayInMonth)) {
            model.addAttribute("errorInvalidData", "Error: Selected date is not a first Monday of a month!");
            return "reports/employeeReport/reportEmployeeReportForm";
        }
        reportsService.employeeReportProcess(selectedMonday, employeeId, model);

        return "reports/employeeReport/reportEmployeeReportGenerated";
    }

    @RequestMapping(value = "/project-report/process", method = RequestMethod.POST)
    public String processProjectReport(@RequestParam Long projectId, Model model) {
        if (projectId == null) {
            model.addAttribute("errorNotSufficientData", "Error: Indicate all data requested in order to generate a report!");
            return "reports/projectReport/reportProjectReportForm";
        }
        reportsService.projectReportProcess(projectId, model);
        return "reports/projectReport/reportProjectReportGenerated";
    }

    @RequestMapping(value = "/invoice-preview/process", method = RequestMethod.POST)
    public String processInvoicePreview(@RequestParam Long clientId, @RequestParam String startDate, Model model) {
        if (clientId == null || StringUtils.isBlank(startDate)) {
            model.addAttribute("errorNotSufficientData", "Error: Indicate all data requested in order to generate an invoice preview!");
            return "reports/invoicePreview/invoicePreviewForm";
        }
        //todo walidacja do zmiany - zmienić w widoku możliwość wyboru tylko określonych dat
        LocalDate selectedMonday = LocalDate.parse(startDate);
        LocalDate firstMondayInMonth = reportsService.getFirstMondayOfMonth(selectedMonday);
        if (!selectedMonday.equals(firstMondayInMonth)) {
            model.addAttribute("errorInvalidData", "Error: Selected date is not a first Monday of a month!");
            return "reports/invoicePreview/invoicePreviewForm";
        }
        reportsService.invoicePreviewProcess(selectedMonday, clientId, model);
        return "reports/invoicePreview/invoicePreviewGenerated";
    }


}