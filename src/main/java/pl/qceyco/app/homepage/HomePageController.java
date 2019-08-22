package pl.qceyco.app.homepage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.qceyco.app.employee.Employee;
import pl.qceyco.app.employee.EmployeeRepository;
import pl.qceyco.app.project.Project;
import pl.qceyco.app.project.ProjectRepository;
import pl.qceyco.app.timesheet.referenceUnit.TimesheetReferenceUnit;
import pl.qceyco.app.timesheet.referenceUnit.TimesheetReferenceUnitRepository;

import javax.servlet.http.HttpSession;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.temporal.TemporalAdjusters.firstInMonth;

@Controller
public class HomePageController {

    private final ProjectRepository projectRepository;
    private final TimesheetReferenceUnitRepository timesheetReferenceUnitRepository;
    private final EmployeeRepository employeeRepository;

    public HomePageController(ProjectRepository projectRepository, TimesheetReferenceUnitRepository timesheetReferenceUnitRepository, EmployeeRepository employeeRepository) {
        this.projectRepository = projectRepository;
        this.timesheetReferenceUnitRepository = timesheetReferenceUnitRepository;
        this.employeeRepository = employeeRepository;
    }


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showStartPage() {
        return "start";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String showHomePage(HttpSession session, Model model) {
        Employee loggedInUser = (Employee) session.getAttribute("loggedInUser");
        if (loggedInUser.getAdmin()) {
            LocalDate thisMonthFirstMonday = LocalDate.now().with(firstInMonth(DayOfWeek.MONDAY));
            LocalDate endDate = thisMonthFirstMonday.plusDays(27);
            List<Project> projects = projectRepository.findAll();
            Map<String, Integer> projectsAndHours = new HashMap<>();
            for (Project p : projects) {
                Integer hours = 0;
                List<TimesheetReferenceUnit> projectTimesheets = timesheetReferenceUnitRepository.findAllByProjectIn4Weeks(p.getId(), thisMonthFirstMonday, endDate);
                for (TimesheetReferenceUnit t : projectTimesheets) {
                    hours += t.countWeekHours();
                }
                projectsAndHours.put(p.getSignature(), hours);
            }
            List<Employee> employees = employeeRepository.findAll();
            Map<String, Integer> employeesAndUtilisation = new HashMap<>();
            for (Employee e : employees) {
                List<TimesheetReferenceUnit> timesheets = timesheetReferenceUnitRepository.findAllByEmployeeIdIn4Weeks(e.getId(), thisMonthFirstMonday, endDate);
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
                employeesAndUtilisation.put(e.getNameDisplay(), workTimeUtilizationLevel);
            }
            LocalDate previousMonday = LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.MONDAY)).minusDays(7);
            List<TimesheetReferenceUnit> timesheets = timesheetReferenceUnitRepository.findAllInRecentWeek(previousMonday, previousMonday.plusDays(1));
            model.addAttribute("projectsAndHours", projectsAndHours);
            model.addAttribute("employeesAndUtilisation", employeesAndUtilisation);
            model.addAttribute("timesheets", timesheets);
            model.addAttribute("thisMonthFirstMonday", thisMonthFirstMonday);
            model.addAttribute("previousMonday", previousMonday);
            return "admin/homeAdmin";
        } else {
            Long userId = loggedInUser.getId();
            List<Project> projectsOfUser = projectRepository.findAllByEmployeeId(userId);
            TimesheetReferenceUnit recentTimesheet = timesheetReferenceUnitRepository.findFirstByEmployeeIdOrderByIdDesc(userId);
            LocalDate previousMonthFirstMonday = LocalDate.now().minusMonths(1).with(firstInMonth(DayOfWeek.MONDAY));
            LocalDate endDate = previousMonthFirstMonday.plusDays(27);
            List<TimesheetReferenceUnit> timesheets = timesheetReferenceUnitRepository.findAllByEmployeeIdIn4Weeks(userId, previousMonthFirstMonday, endDate);
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
            Integer targetBudget = loggedInUser.getAdditionalInfo().getTargetBudget();
            Integer hourlyRate = loggedInUser.getAdditionalInfo().getHourlyRateChargingClients();
            Integer valueOfRenderedServices = hourlyRate * amountOfBillableHours;
            boolean isMonthlyTargetAchieved = false;
            double bonusAmountD = 0.0;
            if (valueOfRenderedServices >= targetBudget) {
                isMonthlyTargetAchieved = true;
                bonusAmountD = (loggedInUser.getAdditionalInfo().getBonus() * (valueOfRenderedServices - targetBudget)) / 100;
            }
            Integer bonusAmount = getBonusAmountAsInt(bonusAmountD);
            model.addAttribute("valueOfRenderedServices", valueOfRenderedServices);
            model.addAttribute("previousMonthFirstMonday", previousMonthFirstMonday);
            model.addAttribute("amountOfBillableHours", amountOfBillableHours);
            model.addAttribute("amountOfNonBillableHours", amountOfNonBillableHours);
            model.addAttribute("workTimeUtilizationLevel", workTimeUtilizationLevel);
            model.addAttribute("isMonthlyTargetAchieved", isMonthlyTargetAchieved);
            model.addAttribute("bonusAmount", bonusAmount);
            model.addAttribute("projectsOfUser", projectsOfUser);
            model.addAttribute("recentTimesheet", recentTimesheet);
            return "user/homeUser";
        }
    }

    private Integer getWorkTimeUtilisationLevelAsInt(Integer amountOfNonBillableHours, Integer amountOfBillableHours) {
        double workTimeUtilizationLevelD = (double) amountOfBillableHours / (amountOfBillableHours + amountOfNonBillableHours) * 100;
        if (Double.isNaN(workTimeUtilizationLevelD)) {
            workTimeUtilizationLevelD = 0;
        }
        workTimeUtilizationLevelD = Math.floor(workTimeUtilizationLevelD);
        return (Integer) (int) workTimeUtilizationLevelD;
    }

    private Integer getBonusAmountAsInt(double bonusAmountD) {
        bonusAmountD = Math.floor(bonusAmountD);
        return (Integer) (int) bonusAmountD;
    }


}







