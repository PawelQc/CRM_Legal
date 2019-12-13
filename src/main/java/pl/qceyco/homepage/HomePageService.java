package pl.qceyco.homepage;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
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
    void processAdminHome(Model model) {
        LocalDate thisMonthFirstMonday = LocalDate.now().with(firstInMonth(DayOfWeek.MONDAY));
        LocalDate endDate = thisMonthFirstMonday.plusDays(27);
        Map<String, Integer> projectsAndHours = getProjectsToHoursData(thisMonthFirstMonday, endDate);
        Map<String, Integer> employeesAndUtilisation = getEmployeesUtilisationData(thisMonthFirstMonday, endDate);
        List<TimesheetUnit> timesheets = getTimesheetsForRecentWeek();
        LocalDate previousMonday = getPreviousMonday();
        adminHomeSetAttributes(model, thisMonthFirstMonday, projectsAndHours, employeesAndUtilisation, previousMonday, timesheets);
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

    private void adminHomeSetAttributes(Model model, LocalDate thisMonthFirstMonday, Map<String, Integer> projectsAndHours, Map<String,
            Integer> employeesAndUtilisation, LocalDate previousMonday, List<TimesheetUnit> timesheets) {
        model.addAttribute("projectsAndHours", projectsAndHours);
        model.addAttribute("employeesAndUtilisation", employeesAndUtilisation);
        model.addAttribute("timesheets", timesheets);
        model.addAttribute("thisMonthFirstMonday", thisMonthFirstMonday);
        model.addAttribute("previousMonday", previousMonday);
    }

//HOME USER ######################################################################################################
    void processUserHome(Employee loggedInUser, Model model) {
        Long userId = loggedInUser.getId();
        List<Project> projectsOfUser = projectRepository.findAllByEmployeeId(userId);
        TimesheetUnit recentTimesheet = timesheetUnitRepository.findFirstByEmployeeIdOrderByIdDesc(userId);
        LocalDate previousMonthFirstMonday = LocalDate.now().minusMonths(1).with(firstInMonth(DayOfWeek.MONDAY));
        LocalDate endDate = previousMonthFirstMonday.plusDays(27);
        List<TimesheetUnit> timesheetsPreviousMonth = timesheetUnitRepository.findAllByEmployeeIdInSearchPeriod(userId, previousMonthFirstMonday, endDate);
        Integer nonBillableHours = countNonBillableHours(timesheetsPreviousMonth);
        Integer billableHours = countBillableHours(timesheetsPreviousMonth);
        Integer workTimeUtilizationLevel = getWorkTimeUtilisationLevelAsInt(nonBillableHours, billableHours);
        Integer targetBudget = loggedInUser.getAdditionalInfo().getTargetBudget();
        Integer hourlyRate = loggedInUser.getAdditionalInfo().getHourlyRateChargingClients();
        Integer valueOfRenderedServices = hourlyRate * billableHours;
        boolean isMonthlyTargetAchieved = false;
        double bonusAmountD = 0.0;
        if (valueOfRenderedServices >= targetBudget) {
            isMonthlyTargetAchieved = true;
            bonusAmountD = (loggedInUser.getAdditionalInfo().getBonus() * (valueOfRenderedServices - targetBudget)) / 100.0;
        }
        Integer bonusAmount = getBonusAmountAsInt(bonusAmountD);
        userHomeSetAttributes(model, projectsOfUser, recentTimesheet, previousMonthFirstMonday, nonBillableHours, billableHours,
                workTimeUtilizationLevel, valueOfRenderedServices, isMonthlyTargetAchieved, bonusAmount);
    }

    private Integer getBonusAmountAsInt(Double bonusAmount) {
        bonusAmount = Math.floor(bonusAmount);
        return bonusAmount.intValue();
    }

    private void userHomeSetAttributes(Model model, List<Project> projectsOfUser, TimesheetUnit recentTimesheet, LocalDate previousMonthFirstMonday,
                                       Integer amountOfNonBillableHours, Integer amountOfBillableHours, Integer workTimeUtilizationLevel,
                                       Integer valueOfRenderedServices, boolean isMonthlyTargetAchieved, Integer bonusAmount) {
        model.addAttribute("valueOfRenderedServices", valueOfRenderedServices);
        model.addAttribute("previousMonthFirstMonday", previousMonthFirstMonday);
        model.addAttribute("amountOfBillableHours", amountOfBillableHours);
        model.addAttribute("amountOfNonBillableHours", amountOfNonBillableHours);
        model.addAttribute("workTimeUtilizationLevel", workTimeUtilizationLevel);
        model.addAttribute("isMonthlyTargetAchieved", isMonthlyTargetAchieved);
        model.addAttribute("bonusAmount", bonusAmount);
        model.addAttribute("projectsOfUser", projectsOfUser);
        model.addAttribute("recentTimesheet", recentTimesheet);
    }


}







