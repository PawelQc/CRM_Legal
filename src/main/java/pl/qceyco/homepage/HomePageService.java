package pl.qceyco.homepage;

import org.springframework.stereotype.Service;
import pl.qceyco.employee.Employee;
import pl.qceyco.employee.EmployeeRepository;
import pl.qceyco.project.Project;
import pl.qceyco.project.ProjectRepository;
import pl.qceyco.timesheet.unit.TimesheetUnit;
import pl.qceyco.timesheet.unit.TimesheetUnitRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.temporal.TemporalAdjusters.firstInMonth;

@Service
public class HomePageService {

    private final ProjectRepository projectRepository;
    private final TimesheetUnitRepository timesheetUnitRepository;
    private final EmployeeRepository employeeRepository;

    public HomePageService(ProjectRepository projectRepository, TimesheetUnitRepository timesheetUnitRepository, EmployeeRepository employeeRepository) {
        this.projectRepository = projectRepository;
        this.timesheetUnitRepository = timesheetUnitRepository;
        this.employeeRepository = employeeRepository;
    }

    //HOME ADMIN ######################################################################################################
    AdminHomeParams processAdminHome() {
        LocalDate thisMonthFirstMonday = LocalDate.now().with(firstInMonth(DayOfWeek.MONDAY));
        return AdminHomeParams.builder()
                .employeesAndUtilisation(getEmployeesUtilisationData(thisMonthFirstMonday, thisMonthFirstMonday.plusDays(27)))
                .previousMonday(getPreviousMonday())
                .projectsAndHours(getProjectsToHoursData(thisMonthFirstMonday, thisMonthFirstMonday.plusDays(27)))
                .thisMonthFirstMonday(thisMonthFirstMonday)
                .timesheets(getTimesheetsForRecentWeek())
                .build();
    }

    private Map<String, Integer> getEmployeesUtilisationData(LocalDate startDate, LocalDate endDate) {
        Map<String, Integer> employeesAndUtilisation = new HashMap<>();
        List<Employee> employees = employeeRepository.findAll();
        for (Employee e : employees) {
            List<TimesheetUnit> timesheets = timesheetUnitRepository.findAllByEmployeeIdInSearchPeriod(e.getId(), startDate, endDate);
            Integer nonBillableHours = countNonBillableHours(timesheets);
            Integer billableHours = countBillableHours(timesheets);
            Integer workTimeUtilizationLevel = getWorkTimeUtilisationLevelAsInt(nonBillableHours, billableHours);
            employeesAndUtilisation.put(e.getNameDisplay(), workTimeUtilizationLevel);
        }
        return employeesAndUtilisation;
    }

    private List<TimesheetUnit> getTimesheetsForRecentWeek() {
        LocalDate previousMonday = getPreviousMonday();
        return timesheetUnitRepository.findAllInSearchPeriod(previousMonday, previousMonday.plusDays(1));
    }

    private LocalDate getPreviousMonday() {
        return LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.MONDAY)).minusDays(7);
    }

    private Integer countBillableHours(List<TimesheetUnit> timesheets) {
        int billableHours = 0;
        billableHours = timesheets.stream()
                .filter(t -> t.getProject().isBillable())
                .mapToInt(TimesheetUnit::countWeekHours)
                .sum();
        return billableHours;
    }

    private Integer countNonBillableHours(List<TimesheetUnit> timesheets) {
        int nonBillableHours = 0;
        nonBillableHours = timesheets.stream()
                .filter(t -> !t.getProject().isBillable())
                .mapToInt(TimesheetUnit::countWeekHours)
                .sum();
        return nonBillableHours;
    }

    private Map<String, Integer> getProjectsToHoursData(LocalDate startDate, LocalDate endDate) {
        Map<String, Integer> projectsAndHours = new HashMap<>();
        List<Project> projects = projectRepository.findAll();
        for (Project p : projects) {
            Integer hours = getProjectHours(startDate, endDate, p);
            projectsAndHours.put(p.getSignature(), hours);
        }
        return projectsAndHours;
    }

    private Integer getProjectHours(LocalDate startDate, LocalDate endDate, Project project) {
        int hours = 0;
        List<TimesheetUnit> projectTimesheets = timesheetUnitRepository.findAllByProjectInSearchPeriod(project.getId(), startDate, endDate);
        hours = projectTimesheets.stream()
                .mapToInt(TimesheetUnit::countWeekHours)
                .sum();
        return hours;
    }

    private Integer getWorkTimeUtilisationLevelAsInt(Integer nonBillableHours, Integer billableHours) {
        double workTimeUtilizationLevelD = billableHours.doubleValue() / (billableHours + nonBillableHours) * 100;
        if (Double.isNaN(workTimeUtilizationLevelD)) {
            workTimeUtilizationLevelD = 0.0;
        }
        workTimeUtilizationLevelD = Math.floor(workTimeUtilizationLevelD);
        return (int) workTimeUtilizationLevelD;
    }

    //HOME USER ######################################################################################################
    UserHomeParams processUserHome(Employee loggedInUser) {
        Long userId = loggedInUser.getId();
        LocalDate previousMonthFirstMonday = LocalDate.now().minusMonths(1).with(firstInMonth(DayOfWeek.MONDAY));
        List<TimesheetUnit> timesheetsPreviousMonth = timesheetUnitRepository.findAllByEmployeeIdInSearchPeriod(userId, previousMonthFirstMonday, previousMonthFirstMonday.plusDays(27));
        int valueOfRenderedServices = loggedInUser.getAdditionalInfo().getHourlyRateChargingClients() * countBillableHours(timesheetsPreviousMonth);
        boolean isMonthlyTargetAchieved = false;
        double bonusAmountD = 0.0;
        if (valueOfRenderedServices >= loggedInUser.getAdditionalInfo().getTargetBudget()) {
            isMonthlyTargetAchieved = true;
            bonusAmountD = (loggedInUser.getAdditionalInfo().getBonus() * (valueOfRenderedServices - loggedInUser.getAdditionalInfo().getTargetBudget())) / 100.0;
        }
        return UserHomeParams.builder()
                .projectsOfUser(projectRepository.findAllByEmployeeId(userId))
                .amountOfBillableHours(countBillableHours(timesheetsPreviousMonth))
                .amountOfNonBillableHours(countNonBillableHours(timesheetsPreviousMonth))
                .monthlyTargetAchieved(isMonthlyTargetAchieved)
                .bonusAmount(getBonusAmountAsInt(bonusAmountD))
                .previousMonthFirstMonday(previousMonthFirstMonday)
                .recentTimesheet(timesheetUnitRepository.findFirstByEmployeeIdOrderByIdDesc(userId))
                .valueOfRenderedServices(loggedInUser.getAdditionalInfo().getHourlyRateChargingClients() * countBillableHours(timesheetsPreviousMonth))
                .workTimeUtilizationLevel(getWorkTimeUtilisationLevelAsInt(countNonBillableHours(timesheetsPreviousMonth), countBillableHours(timesheetsPreviousMonth)))
                .build();
    }

    private Integer getBonusAmountAsInt(Double bonusAmount) {
        bonusAmount = Math.floor(bonusAmount);
        return bonusAmount.intValue();
    }
}







