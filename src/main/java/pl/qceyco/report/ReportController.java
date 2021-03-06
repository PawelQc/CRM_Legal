package pl.qceyco.report;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.qceyco.client.Client;
import pl.qceyco.employee.Employee;
import pl.qceyco.project.Project;
import pl.qceyco.timesheet.unit.TimesheetUnit;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/reports")

public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @ModelAttribute("projects")
    public List<Project> populateProjects() {
        return reportService.getAllBillableProjects();
    }

    @ModelAttribute("employees")
    public List<Employee> populateEmployees() {
        return reportService.getAllEmployees();
    }

    @ModelAttribute("clients")
    public List<Client> populateClients() {
        return reportService.getAllClients();
    }

    @ModelAttribute("timesheetUnitsAll")
    public List<TimesheetUnit> populateTimesheetUnits() {
        return reportService.getAllTimesheets();
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

    @RequestMapping(value = "/export-timesheets/form", method = RequestMethod.GET)
    public String showEmployeeChoiceForm() {
        return "reports/excelExport/excelExportForm";
    }

    @RequestMapping(value = "/monthly-employee-report/process", method = RequestMethod.POST)
    public String processMonthlyEmployeeReport(@RequestParam Long employeeId, @RequestParam String startDate, Model model) {
        if (employeeId == null || StringUtils.isBlank(startDate)) {
            model.addAttribute("errorNotSufficientData", "Error: Indicate all data requested in order to generate a report!");
            return "reports/employeeReport/reportEmployeeReportForm";
        }
        LocalDate selectedMonday = LocalDate.parse(startDate);
        LocalDate firstMondayInMonth = reportService.getFirstMondayOfMonth(selectedMonday);
        if (!selectedMonday.equals(firstMondayInMonth)) {
            model.addAttribute("errorInvalidData", "Error: Selected date is not a first Monday of a month!");
            return "reports/employeeReport/reportEmployeeReportForm";
        }
        EmployeeReport employeeReport = reportService.employeeReportProcess(selectedMonday, employeeId);
        model.addAttribute("employeeReport", employeeReport);

        return "reports/employeeReport/reportEmployeeReportGenerated";
    }

    @RequestMapping(value = "/export-timesheets/process", method = RequestMethod.POST)
    public String processTimesheetExportReport(@RequestParam Long employeeId, @RequestParam String startDate,
                                               @RequestParam String endDate, Model model) {
        if (employeeId == null || StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate)) {
            model.addAttribute("errorNotSufficientData", "Error: Indicate all data requested in order to generate a report!");
            return "reports/excelExport/excelExportForm";
        }
        LocalDate selectedMonday = LocalDate.parse(startDate);
        LocalDate selectedSunday = LocalDate.parse(endDate);
        if (!selectedMonday.getDayOfWeek().equals(DayOfWeek.MONDAY) || !selectedSunday.getDayOfWeek().equals(DayOfWeek.SUNDAY)
                || selectedMonday.isAfter(selectedSunday)) {
            model.addAttribute("errorInvalidData", "Error: Selected dates are incorrect!");
            return "reports/excelExport/excelExportForm";
        }
        reportService.exportTimesheetsProcess(selectedMonday, selectedSunday, employeeId, model);
        return "reports/excelExport/excelExportMessage";
    }

    @RequestMapping(value = "/project-report/process", method = RequestMethod.POST)
    public String processProjectReport(@RequestParam Long projectId, Model model) {
        if (projectId == null) {
            model.addAttribute("errorNotSufficientData", "Error: Indicate all data requested in order to generate a report!");
            return "reports/projectReport/reportProjectReportForm";
        }
        ProjectReport projectReport = reportService.projectReportProcess(projectId);
        model.addAttribute("projectReport", projectReport);
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
        LocalDate firstMondayInMonth = reportService.getFirstMondayOfMonth(selectedMonday);
        if (!selectedMonday.equals(firstMondayInMonth)) {
            model.addAttribute("errorInvalidData", "Error: Selected date is not a first Monday of a month!");
            return "reports/invoicePreview/invoicePreviewForm";
        }
        InvoiceReport invoiceReport = reportService.invoicePreviewProcess(selectedMonday, clientId);
        model.addAttribute("invoiceReport", invoiceReport);
        return "reports/invoicePreview/invoicePreviewGenerated";
    }


}