package pl.qceyco.app.report;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import pl.qceyco.app.client.Client;
import pl.qceyco.app.client.ClientRepository;
import pl.qceyco.app.client.legalPerson.ClientLegalPersonRepository;
import pl.qceyco.app.employee.Employee;
import pl.qceyco.app.employee.EmployeeRepository;
import pl.qceyco.app.project.Project;
import pl.qceyco.app.project.ProjectRepository;
import pl.qceyco.app.timesheet.unit.TimesheetUnit;
import pl.qceyco.app.timesheet.unit.TimesheetUnitRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.TemporalAdjusters.firstInMonth;

@Service
public class ReportService {
    private final TimesheetUnitRepository timesheetUnitRepository;
    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;
    private final ClientLegalPersonRepository clientLegalPersonRepository;

    public ReportService(TimesheetUnitRepository timesheetUnitRepository, ProjectRepository projectRepository,
                         EmployeeRepository employeeRepository, ClientRepository clientRepository, ClientLegalPersonRepository clientLegalPersonRepository) {
        this.timesheetUnitRepository = timesheetUnitRepository;
        this.projectRepository = projectRepository;
        this.employeeRepository = employeeRepository;
        this.clientRepository = clientRepository;
        this.clientLegalPersonRepository = clientLegalPersonRepository;
    }

    List<Project> getAllBillableProjects() {
        return projectRepository.findAllByIsBillableIsTrue();
    }

    List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    List<TimesheetUnit> getAllTimesheets() {
        return timesheetUnitRepository.findAll();
    }

    LocalDate getFirstMondayOfMonth(LocalDate selectedMonday) {
        return selectedMonday.with(firstInMonth(DayOfWeek.MONDAY));
    }

    List<TimesheetUnit> getAllEmployeeTimesheetsFrom4Weeks(Long employeeId, LocalDate start, LocalDate end) {
        return timesheetUnitRepository.findAllByEmployeeIdIn4Weeks(employeeId, start, end);
    }

    void employeeReportProcess(LocalDate selectedMonday, Long employeeId, Model model) {
        LocalDate endDate = selectedMonday.plusDays(27);
        List<TimesheetUnit> timesheets = getAllEmployeeTimesheetsFrom4Weeks(employeeId, selectedMonday, endDate);
        Integer nonBillableHours = countNonBillableHours(timesheets);
        Integer billableHours = countBillableHours(timesheets);
        Integer workTimeUtilizationLevel = getWorkTimeUtilisationLevelAsInt(nonBillableHours, billableHours);
        Employee reportedEmployee = employeeRepository.findFirstById(employeeId);
        Integer targetBudget = reportedEmployee.getAdditionalInfo().getTargetBudget();
        Integer hourlyRate = reportedEmployee.getAdditionalInfo().getHourlyRateChargingClients();
        Integer valueOfRenderedServices = hourlyRate * billableHours;
        boolean isMonthlyTargetAchieved = false;
        double bonusAmountD = 0.0;
        if (valueOfRenderedServices >= targetBudget) {
            isMonthlyTargetAchieved = true;
            bonusAmountD = (reportedEmployee.getAdditionalInfo().getBonus() * (valueOfRenderedServices - targetBudget)) / 100.0;
        }
        Integer bonusAmount = getBonusAmountAsInt(bonusAmountD);
        addModelAttributesEmployeeReport(model, selectedMonday, nonBillableHours, billableHours, workTimeUtilizationLevel,
                reportedEmployee, valueOfRenderedServices, isMonthlyTargetAchieved, bonusAmount);
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

    private Integer getBonusAmountAsInt(double bonusAmountD) {
        bonusAmountD = Math.floor(bonusAmountD);
        return (Integer) (int) bonusAmountD;
    }

    private Integer countBillableHours(List<TimesheetUnit> timesheets) {
        Integer billableHours = 0;
        for (TimesheetUnit t : timesheets) {
            if (t.getProject().getBillable()) {
                billableHours += t.countWeekHours();
            }
        }
        return billableHours;
    }

    private Integer countNonBillableHours(List<TimesheetUnit> timesheets) {
        Integer nonBillableHours = 0;
        for (TimesheetUnit t : timesheets) {
            if (!t.getProject().getBillable()) {
                nonBillableHours += t.countWeekHours();
            }
        }
        return nonBillableHours;
    }

    private Integer getWorkTimeUtilisationLevelAsInt(Integer nonBillableHours, Integer billableHours) {
        double workTimeUtilizationLevelD = (double) billableHours / (billableHours + nonBillableHours) * 100;
        if (Double.isNaN(workTimeUtilizationLevelD)) {
            workTimeUtilizationLevelD = 0;
        }
        workTimeUtilizationLevelD = Math.floor(workTimeUtilizationLevelD);
        return (Integer) (int) workTimeUtilizationLevelD;
    }

    void projectReportProcess(Long projectId, Model model) {
        Project project = projectRepository.findFirstByIdWithProjectTeamMembers(projectId);
        Integer amountOfHours = countProjectHours(projectId);
        Integer clientDefaultHourlyRate = project.getClient().getAdditionalInfo().getHourlyRateIsCharged();
        Integer potentialValueOfRenderedServices = amountOfHours * clientDefaultHourlyRate;
        Integer capOnRemuneration = project.getCapOnRemuneration();
        boolean isProjectProfitable = true;
        if (potentialValueOfRenderedServices > capOnRemuneration) {
            isProjectProfitable = false;
        }
        addModelAttributesProjectReport(model, project, amountOfHours, potentialValueOfRenderedServices, isProjectProfitable);
    }

    private Integer countProjectHours(Long projectId) {
        Integer amountOfHours = 0;
        List<TimesheetUnit> timesheets = timesheetUnitRepository.findAllByProjectIdOrderByEmployeeId(projectId);
        for (TimesheetUnit t : timesheets) {
            amountOfHours += t.countWeekHours();
        }
        return amountOfHours;
    }

    private void addModelAttributesProjectReport(Model model, Project project, Integer amountOfHours, Integer potentialValueOfRenderedServices, boolean isProjectProfitable) {
        model.addAttribute("amountOfHours", amountOfHours);
        model.addAttribute("project", project);
        model.addAttribute("potentialValueOfRenderedServices", potentialValueOfRenderedServices);
        model.addAttribute("isProjectProfitable", isProjectProfitable);
    }

    void invoicePreviewProcess(LocalDate selectedMonday, Long clientId, Model model) {
        LocalDate endDate = selectedMonday.plusDays(27);
        List<TimesheetUnit> timesheets = timesheetUnitRepository.findAllByClientIn4Weeks(clientId, selectedMonday, endDate);
        List<Project> projectsOfClient = projectRepository.findAllByClientId(clientId);
        Client client = clientRepository.findFirstById(clientId);
        Integer amountOfHours = 0;
        for (TimesheetUnit t : timesheets) {
            amountOfHours += t.countWeekHours();
        }
        addModelAttributesInvoicePreview(model, selectedMonday, timesheets, projectsOfClient, client, amountOfHours);
    }

    private void addModelAttributesInvoicePreview(Model model, LocalDate selectedMonday, List<TimesheetUnit> timesheets, List<Project> projectsOfClient, Client client, Integer amountOfHours) {
        model.addAttribute("selectedMonday", selectedMonday);
        model.addAttribute("timesheets", timesheets);
        model.addAttribute("projectsOfClient", projectsOfClient);
        model.addAttribute("client", client);
        model.addAttribute("amountOfHours", amountOfHours);
    }
}









