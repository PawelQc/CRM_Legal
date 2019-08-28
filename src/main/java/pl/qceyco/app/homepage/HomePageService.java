package pl.qceyco.app.homepage;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import pl.qceyco.app.employee.Employee;
import pl.qceyco.app.employee.EmployeeRepository;
import pl.qceyco.app.project.Project;
import pl.qceyco.app.project.ProjectRepository;
import pl.qceyco.app.timesheet.unit.TimesheetUnit;
import pl.qceyco.app.timesheet.unit.TimesheetUnitRepository;

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
            List<TimesheetUnit> timesheets = timesheetUnitRepository.findAllByEmployeeIdIn4Weeks(e.getId(), startDate, endDate);
            Integer nonBillableHours = countNonBillableHours(timesheets);
            Integer billableHours = countBillableHours(timesheets);
            Integer workTimeUtilizationLevel = getWorkTimeUtilisationLevelAsInt(nonBillableHours, billableHours);
            employeesAndUtilisation.put(e.getNameDisplay(), workTimeUtilizationLevel);
        }
        return employeesAndUtilisation;
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

    private List<TimesheetUnit> getTimesheetsForRecentWeek() {
        LocalDate previousMonday = getPreviousMonday();
        List<TimesheetUnit> timesheets = timesheetUnitRepository.findAllInRecentWeek(previousMonday, previousMonday.plusDays(1));
        return timesheets;
    }

    private LocalDate getPreviousMonday() {
        LocalDate previousMonday = LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.MONDAY)).minusDays(7);
        return previousMonday;
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

    private Integer getProjectHours(LocalDate startDate, LocalDate endDate, Project project) {
        Integer hours = 0;
        List<TimesheetUnit> projectTimesheets = timesheetUnitRepository.findAllByProjectIn4Weeks(project.getId(), startDate, endDate);
        for (TimesheetUnit t : projectTimesheets) {
            hours += t.countWeekHours();
        }
        return hours;
    }

    private Integer getWorkTimeUtilisationLevelAsInt(Integer nonBillableHours, Integer billableHours) {
        double workTimeUtilizationLevelD = (double) billableHours / (billableHours + nonBillableHours) * 100;
        if (Double.isNaN(workTimeUtilizationLevelD)) {
            workTimeUtilizationLevelD = 0;
        }
        workTimeUtilizationLevelD = Math.floor(workTimeUtilizationLevelD);
        return (Integer) (int) workTimeUtilizationLevelD;
    }

    private void adminHomeSetAttributes(Model model, LocalDate thisMonthFirstMonday, Map<String, Integer> projectsAndHours, Map<String,
            Integer> employeesAndUtilisation, LocalDate previousMonday, List<TimesheetUnit> timesheets) {
        model.addAttribute("projectsAndHours", projectsAndHours);
        model.addAttribute("employeesAndUtilisation", employeesAndUtilisation);
        model.addAttribute("timesheets", timesheets);
        model.addAttribute("thisMonthFirstMonday", thisMonthFirstMonday);
        model.addAttribute("previousMonday", previousMonday);
    }

    void processUserHome(Employee loggedInUser, Model model) {
        Long userId = loggedInUser.getId();
        List<Project> projectsOfUser = projectRepository.findAllByEmployeeId(userId);
        TimesheetUnit recentTimesheet = timesheetUnitRepository.findFirstByEmployeeIdOrderByIdDesc(userId);
        LocalDate previousMonthFirstMonday = LocalDate.now().minusMonths(1).with(firstInMonth(DayOfWeek.MONDAY));
        LocalDate endDate = previousMonthFirstMonday.plusDays(27);
        List<TimesheetUnit> timesheetsPreviousMonth = timesheetUnitRepository.findAllByEmployeeIdIn4Weeks(userId, previousMonthFirstMonday, endDate);
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
            bonusAmountD = (loggedInUser.getAdditionalInfo().getBonus() * (valueOfRenderedServices - targetBudget)) / 100;
        }
        Integer bonusAmount = getBonusAmountAsInt(bonusAmountD);
        userHomeSetAttributes(model, projectsOfUser, recentTimesheet, previousMonthFirstMonday, nonBillableHours, billableHours,
                workTimeUtilizationLevel, valueOfRenderedServices, isMonthlyTargetAchieved, bonusAmount);
    }

    private Integer getBonusAmountAsInt(double bonusAmountD) {
        bonusAmountD = Math.floor(bonusAmountD);
        return (Integer) (int) bonusAmountD;
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







