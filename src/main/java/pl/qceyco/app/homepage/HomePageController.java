package pl.qceyco.app.homepage;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.qceyco.app.employee.Employee;
import pl.qceyco.app.employee.EmployeeRepository;
import pl.qceyco.app.project.Project;
import pl.qceyco.app.project.ProjectRepository;
import pl.qceyco.app.timesheet.unit.TimesheetUnit;
import pl.qceyco.app.timesheet.unit.TimesheetUnitRepository;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
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
    private final TimesheetUnitRepository timesheetUnitRepository;
    private final EmployeeRepository employeeRepository;

    public HomePageController(ProjectRepository projectRepository, TimesheetUnitRepository timesheetUnitRepository, EmployeeRepository employeeRepository) {
        this.projectRepository = projectRepository;
        this.timesheetUnitRepository = timesheetUnitRepository;
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
                Integer hours = getProjectHours(thisMonthFirstMonday, endDate, p);
                projectsAndHours.put(p.getSignature(), hours);
            }

            List<Employee> employees = employeeRepository.findAll();
            Map<String, Integer> employeesAndUtilisation = new HashMap<>();
            for (Employee e : employees) {
                List<TimesheetUnit> timesheets = timesheetUnitRepository.findAllByEmployeeIdIn4Weeks(e.getId(), thisMonthFirstMonday, endDate);
                Integer amountOfNonBillableHours = 0;
                Integer amountOfBillableHours = 0;
                for (TimesheetUnit t : timesheets) {
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
            List<TimesheetUnit> timesheets = timesheetUnitRepository.findAllInRecentWeek(previousMonday, previousMonday.plusDays(1));
            adminHomeSetAttributes(model, thisMonthFirstMonday, projectsAndHours, employeesAndUtilisation, previousMonday, timesheets);
            return "admin/homeAdmin";

        } else {
            Long userId = loggedInUser.getId();
            List<Project> projectsOfUser = projectRepository.findAllByEmployeeId(userId);
            TimesheetUnit recentTimesheet = timesheetUnitRepository.findFirstByEmployeeIdOrderByIdDesc(userId);
            LocalDate previousMonthFirstMonday = LocalDate.now().minusMonths(1).with(firstInMonth(DayOfWeek.MONDAY));
            LocalDate endDate = previousMonthFirstMonday.plusDays(27);

            List<TimesheetUnit> timesheets = timesheetUnitRepository.findAllByEmployeeIdIn4Weeks(userId, previousMonthFirstMonday, endDate);
            Integer amountOfNonBillableHours = 0;
            Integer amountOfBillableHours = 0;
            for (TimesheetUnit t : timesheets) {
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
            userHomeSetAttributes(model, projectsOfUser, recentTimesheet, previousMonthFirstMonday, amountOfNonBillableHours, amountOfBillableHours, workTimeUtilizationLevel, valueOfRenderedServices, isMonthlyTargetAchieved, bonusAmount);
            return "user/homeUser";
        }
    }

    @RequestMapping(value = "/home/chart-project-hours", method = RequestMethod.GET)
    public void generateProjectsChart(HttpServletResponse response) {
        response.setContentType("image/jpeg");
        try (OutputStream out = response.getOutputStream()) {
            LocalDate thisMonthFirstMonday = LocalDate.now().with(firstInMonth(DayOfWeek.MONDAY));
            LocalDate endDate = thisMonthFirstMonday.plusDays(27);
            List<Project> projects = projectRepository.findAll();
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (Project p : projects) {
                Integer hours = getProjectHours(thisMonthFirstMonday, endDate, p);
                dataset.setValue(hours, "Hours", p.getSignature());
            }
            JFreeChart chart = ChartFactory.createBarChart("Work hours on projects: " + thisMonthFirstMonday.toString() + " - " + endDate.toString(),
                    "Projects", "Hours", dataset, PlotOrientation.VERTICAL,
                    false, true, false);
            setChartView(chart);
            generateChart(out, chart);
        } catch (IOException e) {
        }
    }

    @RequestMapping(value = "/home/chart-total-hours", method = RequestMethod.GET)
    public void generateTotalHoursChart(HttpServletResponse response) {
        response.setContentType("image/jpeg");
        try (OutputStream out = response.getOutputStream()) {
            LocalDate thisMonthFirstMonday = LocalDate.now().with(firstInMonth(DayOfWeek.MONDAY));
            LocalDate endDate = thisMonthFirstMonday.plusDays(27);
            List<Project> projects = projectRepository.findAll();
            DefaultPieDataset dataset = new DefaultPieDataset();
            for (Project p : projects) {
                Integer hours = getProjectHours(thisMonthFirstMonday, endDate, p);
                dataset.setValue(p.getSignature(), hours);
            }
            JFreeChart chart = ChartFactory.createPieChart("Total hours on projects: " + thisMonthFirstMonday.toString() + " - " + endDate.toString(), dataset,
                    true, true, false);
            chart.setBackgroundPaint(Color.yellow);
            generateChart(out, chart);
        } catch (IOException e) {
        }
    }

    @RequestMapping(value = "/home/chart-employees-hours", method = RequestMethod.GET)
    public void generateEmployeesChart(HttpServletResponse response) {
        response.setContentType("image/jpeg");
        try (OutputStream out = response.getOutputStream()) {
            LocalDate thisMonthFirstMonday = LocalDate.now().with(firstInMonth(DayOfWeek.MONDAY));
            LocalDate endDate = thisMonthFirstMonday.plusDays(27);
            List<Employee> employees = employeeRepository.findAll();
            Map<String, Integer> employeesAndBillableHours = new HashMap<>();
            Map<String, Integer> employeesAndNonBillableHours = new HashMap<>();
            for (Employee e : employees) {
                List<TimesheetUnit> timesheets = timesheetUnitRepository.findAllByEmployeeIdIn4Weeks(e.getId(), thisMonthFirstMonday, endDate);
                Integer amountOfNonBillableHours = 0;
                Integer amountOfBillableHours = 0;
                for (TimesheetUnit t : timesheets) {
                    if (!t.getProject().getBillable()) {
                        amountOfNonBillableHours += t.countWeekHours();
                    }
                    if (t.getProject().getBillable()) {
                        amountOfBillableHours += t.countWeekHours();
                    }
                }
                employeesAndBillableHours.put(e.getNameDisplay(), amountOfBillableHours);
                employeesAndNonBillableHours.put(e.getNameDisplay(), amountOfNonBillableHours);
            }
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            fillDatasetWithData(employeesAndBillableHours, dataset, "Billable");
            fillDatasetWithData(employeesAndNonBillableHours, dataset, "Non-billable");
            JFreeChart chart = ChartFactory.createBarChart3D("Comparison between employees - billable vs non-billable hours for: " + thisMonthFirstMonday.toString() + " - " + endDate.toString(),
                    "Employees", "Hours", dataset, PlotOrientation.VERTICAL, true, true, false);
            setChartView(chart);
            generateChart(out, chart);
        } catch (IOException e) {
        }
    }


    @RequestMapping(value = "/home/chart-employees-utilisation", method = RequestMethod.GET)
    public void generateEmployeesUtilisationChart(HttpServletResponse response) {
        response.setContentType("image/jpeg");
        try (OutputStream out = response.getOutputStream()) {
            LocalDate thisMonthFirstMonday = LocalDate.now().with(firstInMonth(DayOfWeek.MONDAY));
            LocalDate endDate = thisMonthFirstMonday.plusDays(27);
            List<Employee> employees = employeeRepository.findAll();
            Map<String, Integer> employeesAndUtilisation = new HashMap<>();
            for (Employee e : employees) {
                List<TimesheetUnit> timesheets = timesheetUnitRepository.findAllByEmployeeIdIn4Weeks(e.getId(), thisMonthFirstMonday, endDate);
                Integer amountOfNonBillableHours = 0;
                Integer amountOfBillableHours = 0;
                for (TimesheetUnit t : timesheets) {
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
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            fillDatasetWithData(employeesAndUtilisation, dataset, "Work time utilisation in % (billable vs whole work time)");
            JFreeChart chart = ChartFactory.createBarChart3D("Comparison between employees - work time utilisation for: " + thisMonthFirstMonday.toString() + " - " + endDate.toString(),
                    "Employees", "%", dataset, PlotOrientation.VERTICAL, true, true, false);
            setChartView(chart);
            generateChart(out, chart);
        } catch (IOException e) {
        }
    }

    private void userHomeSetAttributes(Model model, List<Project> projectsOfUser, TimesheetUnit recentTimesheet, LocalDate previousMonthFirstMonday, Integer amountOfNonBillableHours, Integer amountOfBillableHours, Integer workTimeUtilizationLevel, Integer valueOfRenderedServices, boolean isMonthlyTargetAchieved, Integer bonusAmount) {
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

    private void adminHomeSetAttributes(Model model, LocalDate thisMonthFirstMonday, Map<String, Integer> projectsAndHours, Map<String, Integer> employeesAndUtilisation, LocalDate previousMonday, List<TimesheetUnit> timesheets) {
        model.addAttribute("projectsAndHours", projectsAndHours);
        model.addAttribute("employeesAndUtilisation", employeesAndUtilisation);
        model.addAttribute("timesheets", timesheets);
        model.addAttribute("thisMonthFirstMonday", thisMonthFirstMonday);
        model.addAttribute("previousMonday", previousMonday);
    }

    private Integer getProjectHours(LocalDate thisMonthFirstMonday, LocalDate endDate, Project p) {
        Integer hours = 0;
        List<TimesheetUnit> projectTimesheets = timesheetUnitRepository.findAllByProjectIn4Weeks(p.getId(), thisMonthFirstMonday, endDate);
        for (TimesheetUnit t : projectTimesheets) {
            hours += t.countWeekHours();
        }
        return hours;
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

    private void fillDatasetWithData(Map<String, Integer> employeesAndBillableHours, DefaultCategoryDataset dataset, String description) {
        for (Map.Entry<String, Integer> entry : employeesAndBillableHours.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            dataset.setValue(value, description, key);
        }
    }

    private void setChartView(JFreeChart chart) {
        chart.setBackgroundPaint(Color.yellow);
        CategoryPlot p = chart.getCategoryPlot();
        p.setBackgroundPaint(Color.black);
    }

    private void generateChart(OutputStream out, JFreeChart chart) {
        try {
            ChartUtilities.writeChartAsJPEG(out, chart, 1200, 720);
        } catch (IOException e) {
        }
    }


}







