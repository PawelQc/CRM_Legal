package pl.qceyco.app.reports;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.qceyco.app.client.Client;
import pl.qceyco.app.client.ClientRepository;
import pl.qceyco.app.client.legalPerson.ClientLegalPersonRepository;
import pl.qceyco.app.employee.Employee;
import pl.qceyco.app.employee.EmployeeRepository;
import pl.qceyco.app.project.Project;
import pl.qceyco.app.project.ProjectRepository;
import pl.qceyco.app.timesheet.referenceUnit.TimesheetReferenceUnit;
import pl.qceyco.app.timesheet.referenceUnit.TimesheetReferenceUnitRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.TemporalAdjusters.firstInMonth;

@Controller
@RequestMapping("/reports")

public class ReportsController {

    private final TimesheetReferenceUnitRepository timesheetReferenceUnitRepository;
    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;
    private final ClientLegalPersonRepository clientLegalPersonRepository;

    public ReportsController(TimesheetReferenceUnitRepository timesheetReferenceUnitRepository, ProjectRepository projectRepository,
                             EmployeeRepository employeeRepository, ClientRepository clientRepository, ClientLegalPersonRepository clientLegalPersonRepository) {
        this.timesheetReferenceUnitRepository = timesheetReferenceUnitRepository;
        this.projectRepository = projectRepository;
        this.employeeRepository = employeeRepository;
        this.clientRepository = clientRepository;
        this.clientLegalPersonRepository = clientLegalPersonRepository;
    }

    @ModelAttribute("projects")
    public List<Project> populateProjects() {
        return projectRepository.findAllByIsBillableIsTrue();
    }

    @ModelAttribute("employees")
    public List<Employee> populateEmployees() {
        return employeeRepository.findAll();
    }

    @ModelAttribute("clients")
    public List<Client> populateClients() {
        return clientRepository.findAll();
    }

    @ModelAttribute("timesheetReferenceUnitsAll")
    public List<TimesheetReferenceUnit> populateTimesheetReferenceUnits() {
        return timesheetReferenceUnitRepository.findAll();
    }

    ///////////////////////

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
        LocalDate firstMondayInMonth = selectedMonday.with(firstInMonth(DayOfWeek.MONDAY));
        if (!selectedMonday.equals(firstMondayInMonth)) {
            model.addAttribute("errorInvalidData", "Error: Selected date is not a first Monday of a month!");
            return "reports/employeeReport/reportEmployeeReportForm";
        }
        LocalDate endDate = selectedMonday.plusDays(27);
        List<TimesheetReferenceUnit> timesheets = timesheetReferenceUnitRepository.findAllByEmployeeIdIn4Weeks(employeeId, selectedMonday, endDate);
        Integer amountOfNonBillableHours = 0;
        Integer amountOfBillableHours = 0;
        for (TimesheetReferenceUnit t : timesheets) {
            if (!t.getProject().getBillable()) {
                amountOfNonBillableHours += t.countWeekHours();
            }
            if (t.getProject().getBillable()) {
                amountOfBillableHours += t.countWeekHours();
            }
        }
        Integer workTimeUtilizationLevel = getWorkTimeUtilisationLevelAsInt(amountOfNonBillableHours, amountOfBillableHours);
        Employee reportedEmployee = employeeRepository.findFirstById(employeeId);
        Integer targetBudget = reportedEmployee.getAdditionalInfo().getTargetBudget();
        Integer hourlyRate = reportedEmployee.getAdditionalInfo().getHourlyRateChargingClients();
        Integer valueOfRenderedServices = hourlyRate * amountOfBillableHours;
        boolean isMonthlyTargetAchieved = false;
        double bonusAmountD = 0.0;
        if (valueOfRenderedServices >= targetBudget) {
            isMonthlyTargetAchieved = true;
            bonusAmountD = (reportedEmployee.getAdditionalInfo().getBonus() * (valueOfRenderedServices - targetBudget)) / 100;
        }
        Integer bonusAmount = getBonusAmountAsInt(bonusAmountD);
        addModelAttributesEmployeeReport(model, selectedMonday, amountOfNonBillableHours, amountOfBillableHours, workTimeUtilizationLevel,
                reportedEmployee, valueOfRenderedServices, isMonthlyTargetAchieved, bonusAmount);
        return "reports/employeeReport/reportEmployeeReportGenerated";
    }

    @RequestMapping(value = "/project-report/process", method = RequestMethod.POST)
    public String processProjectReport(@RequestParam Long projectId, Model model) {
        if (projectId == null) {
            model.addAttribute("errorNotSufficientData", "Error: Indicate all data requested in order to generate a report!");
            return "reports/projectReport/reportProjectReportForm";
        }
        Project project = projectRepository.findFirstByIdWithProjectTeamMembers(projectId);
        Integer amountOfHours = 0;
        List<TimesheetReferenceUnit> timesheets = timesheetReferenceUnitRepository.findAllByProjectIdOrderByEmployeeId(projectId);
        for (TimesheetReferenceUnit t : timesheets) {
            amountOfHours += t.countWeekHours();
        }
        Integer clientDefaultHourlyRate = project.getClient().getAdditionalInfo().getHourlyRateIsCharged();
        Integer potentialValueOfRenderedServices = amountOfHours * clientDefaultHourlyRate;
        Integer capOnRemuneration = project.getCapOnRemuneration();
        boolean isProjectProfitable = true;
        if (potentialValueOfRenderedServices > capOnRemuneration) {
            isProjectProfitable = false;
        }
        addModelAttributesProjectReport(model, project, amountOfHours, potentialValueOfRenderedServices, isProjectProfitable);
        return "reports/projectReport/reportProjectReportGenerated";
    }

    @RequestMapping(value = "/invoice-preview/process", method = RequestMethod.POST)
    public String processInvoicePreview(@RequestParam Long clientId, @RequestParam String startDate, Model model) {
        if (clientId == null || StringUtils.isBlank(startDate)) {
            model.addAttribute("errorNotSufficientData", "Error: Indicate all data requested in order to generate an invoice preview!");
            return "reports/invoicePreview/invoicePreviewForm";
        }
        LocalDate selectedMonday = LocalDate.parse(startDate);
        LocalDate firstMondayInMonth = selectedMonday.with(firstInMonth(DayOfWeek.MONDAY));
        if (!selectedMonday.equals(firstMondayInMonth)) {
            model.addAttribute("errorInvalidData", "Error: Selected date is not a first Monday of a month!");
            return "reports/invoicePreview/invoicePreviewForm";
        }
        LocalDate endDate = selectedMonday.plusDays(27);
        List<TimesheetReferenceUnit> timesheets = timesheetReferenceUnitRepository.findAllByClientIn4Weeks(clientId, selectedMonday, endDate);
        List<Project> projectsOfClient = projectRepository.findAllByClientId(clientId);
        Client client = clientRepository.findFirstById(clientId);
        Integer amountOfHours = 0;
        for (TimesheetReferenceUnit t : timesheets) {
            amountOfHours += t.countWeekHours();
        }
        addModelAttributesInvoicePreview(model, selectedMonday, timesheets, projectsOfClient, client, amountOfHours);
        return "reports/invoicePreview/invoicePreviewGenerated";
    }

////////////////////////////////////////

    private Integer getBonusAmountAsInt(double bonusAmountD) {
        bonusAmountD = Math.floor(bonusAmountD);
        return (Integer) (int) bonusAmountD;
    }

    private Integer getWorkTimeUtilisationLevelAsInt(Integer amountOfNonBillableHours, Integer amountOfBillableHours) {
        double workTimeUtilizationLevelD = (double) amountOfBillableHours / (amountOfBillableHours + amountOfNonBillableHours) * 100;
        if (Double.isNaN(workTimeUtilizationLevelD)) {
            workTimeUtilizationLevelD = 0;
        }
        workTimeUtilizationLevelD = Math.floor(workTimeUtilizationLevelD);
        return (Integer) (int) workTimeUtilizationLevelD;
    }

    private void addModelAttributesEmployeeReport(Model model, LocalDate selectedMonday, Integer amountOfNonBillableHours, Integer amountOfBillableHours, Integer workTimeUtilizationLevel, Employee reportedEmployee, Integer valueOfRenderedServices, boolean isMonthlyTargetAchieved, Integer bonusAmount) {
        model.addAttribute("valueOfRenderedServices", valueOfRenderedServices);
        model.addAttribute("reportedEmployee", reportedEmployee);
        model.addAttribute("selectedMonday", selectedMonday);
        model.addAttribute("amountOfBillableHours", amountOfBillableHours);
        model.addAttribute("amountOfNonBillableHours", amountOfNonBillableHours);
        model.addAttribute("workTimeUtilizationLevel", workTimeUtilizationLevel);
        model.addAttribute("isMonthlyTargetAchieved", isMonthlyTargetAchieved);
        model.addAttribute("bonusAmount", bonusAmount);
    }

    private void addModelAttributesProjectReport(Model model, Project project, Integer amountOfHours, Integer potentialValueOfRenderedServices, boolean isProjectProfitable) {
        model.addAttribute("amountOfHours", amountOfHours);
        model.addAttribute("project", project);
        model.addAttribute("potentialValueOfRenderedServices", potentialValueOfRenderedServices);
        model.addAttribute("isProjectProfitable", isProjectProfitable);
    }

    private void addModelAttributesInvoicePreview(Model model, LocalDate selectedMonday, List<TimesheetReferenceUnit> timesheets, List<Project> projectsOfClient, Client client, Integer amountOfHours) {
        model.addAttribute("selectedMonday", selectedMonday);
        model.addAttribute("timesheets", timesheets);
        model.addAttribute("projectsOfClient", projectsOfClient);
        model.addAttribute("client", client);
        model.addAttribute("amountOfHours", amountOfHours);
    }

}