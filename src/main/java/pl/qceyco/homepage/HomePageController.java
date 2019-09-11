package pl.qceyco.homepage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.qceyco.employee.Employee;

import javax.servlet.http.HttpSession;

@Controller
public class HomePageController {

    private final HomePageService homePageService;

    public HomePageController(HomePageService homePageService) {
        this.homePageService = homePageService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showStartPage() {
        return "start";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String showHomePage(HttpSession session, Model model) {
        Employee loggedInUser = (Employee) session.getAttribute("loggedInUser");
        if (loggedInUser.getAdmin()) {
            homePageService.processAdminHome(model);
            return "admin/homeAdmin";
        } else {
            homePageService.processUserHome(loggedInUser, model);
            return "user/homeUser";
        }
    }


    //TODO BĘDĘ REZYGNOWAŁ Z TYCH DIAGRAMÓW WIĘC NIE MA POTRZEBY POATRZENIA NA TEN KOD (NIE BYŁ POPRAWIANY)
//    @RequestMapping(value = "/home/chart-project-hours", method = RequestMethod.GET)
//    public void generateProjectsChart(HttpServletResponse response) {
//        response.setContentType("image/jpeg");
//        try (OutputStream out = response.getOutputStream()) {
//            LocalDate thisMonthFirstMonday = LocalDate.now().with(firstInMonth(DayOfWeek.MONDAY));
//            LocalDate endDate = thisMonthFirstMonday.plusDays(27);
//            List<Project> projects = projectRepository.findAll();
//            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//            for (Project p : projects) {
//                Integer hours = getProjectHours(thisMonthFirstMonday, endDate, p);
//                dataset.setValue(hours, "Hours", p.getSignature());
//            }
//            JFreeChart chart = ChartFactory.createBarChart("Work hours on projects: " + thisMonthFirstMonday.toString() + " - " + endDate.toString(),
//                    "Projects", "Hours", dataset, PlotOrientation.VERTICAL,
//                    false, true, false);
//            setChartView(chart);
//            generateChart(out, chart);
//        } catch (IOException e) {
//        }
//    }
//
//    @RequestMapping(value = "/home/chart-total-hours", method = RequestMethod.GET)
//    public void generateTotalHoursChart(HttpServletResponse response) {
//        response.setContentType("image/jpeg");
//        try (OutputStream out = response.getOutputStream()) {
//            LocalDate thisMonthFirstMonday = LocalDate.now().with(firstInMonth(DayOfWeek.MONDAY));
//            LocalDate endDate = thisMonthFirstMonday.plusDays(27);
//            List<Project> projects = projectRepository.findAll();
//            DefaultPieDataset dataset = new DefaultPieDataset();
//            for (Project p : projects) {
//                Integer hours = getProjectHours(thisMonthFirstMonday, endDate, p);
//                dataset.setValue(p.getSignature(), hours);
//            }
//            JFreeChart chart = ChartFactory.createPieChart("Total hours on projects: " + thisMonthFirstMonday.toString() + " - " + endDate.toString(), dataset,
//                    true, true, false);
//            chart.setBackgroundPaint(Color.yellow);
//            generateChart(out, chart);
//        } catch (IOException e) {
//        }
//    }
//
//    @RequestMapping(value = "/home/chart-employees-hours", method = RequestMethod.GET)
//    public void generateEmployeesChart(HttpServletResponse response) {
//        response.setContentType("image/jpeg");
//        try (OutputStream out = response.getOutputStream()) {
//            LocalDate thisMonthFirstMonday = LocalDate.now().with(firstInMonth(DayOfWeek.MONDAY));
//            LocalDate endDate = thisMonthFirstMonday.plusDays(27);
//            List<Employee> employees = employeeRepository.findAll();
//            Map<String, Integer> employeesAndBillableHours = new HashMap<>();
//            Map<String, Integer> employeesAndNonBillableHours = new HashMap<>();
//            for (Employee e : employees) {
//                List<TimesheetUnit> timesheets = timesheetUnitRepository.findAllByEmployeeIdIn4Weeks(e.getId(), thisMonthFirstMonday, endDate);
//                Integer amountOfNonBillableHours = 0;
//                Integer amountOfBillableHours = 0;
//                for (TimesheetUnit t : timesheets) {
//                    if (!t.getProject().getBillable()) {
//                        amountOfNonBillableHours += t.countWeekHours();
//                    }
//                    if (t.getProject().getBillable()) {
//                        amountOfBillableHours += t.countWeekHours();
//                    }
//                }
//                employeesAndBillableHours.put(e.getNameDisplay(), amountOfBillableHours);
//                employeesAndNonBillableHours.put(e.getNameDisplay(), amountOfNonBillableHours);
//            }
//            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//            fillDatasetWithData(employeesAndBillableHours, dataset, "Billable");
//            fillDatasetWithData(employeesAndNonBillableHours, dataset, "Non-billable");
//            JFreeChart chart = ChartFactory.createBarChart3D("Comparison between employees - billable vs non-billable hours for: " + thisMonthFirstMonday.toString() + " - " + endDate.toString(),
//                    "Employees", "Hours", dataset, PlotOrientation.VERTICAL, true, true, false);
//            setChartView(chart);
//            generateChart(out, chart);
//        } catch (IOException e) {
//        }
//    }
//
//
//    @RequestMapping(value = "/home/chart-employees-utilisation", method = RequestMethod.GET)
//    public void generateEmployeesUtilisationChart(HttpServletResponse response) {
//        response.setContentType("image/jpeg");
//        try (OutputStream out = response.getOutputStream()) {
//            LocalDate thisMonthFirstMonday = LocalDate.now().with(firstInMonth(DayOfWeek.MONDAY));
//            LocalDate endDate = thisMonthFirstMonday.plusDays(27);
//            List<Employee> employees = employeeRepository.findAll();
//            Map<String, Integer> employeesAndUtilisation = new HashMap<>();
//            for (Employee e : employees) {
//                List<TimesheetUnit> timesheets = timesheetUnitRepository.findAllByEmployeeIdIn4Weeks(e.getId(), thisMonthFirstMonday, endDate);
//                Integer amountOfNonBillableHours = 0;
//                Integer amountOfBillableHours = 0;
//                for (TimesheetUnit t : timesheets) {
//                    if (!t.getProject().getBillable()) {
//                        amountOfNonBillableHours += t.countWeekHours();
//                    }
//                    if (t.getProject().getBillable()) {
//                        amountOfBillableHours += t.countWeekHours();
//                    }
//                }
//                Integer workTimeUtilizationLevel = getWorkTimeUtilisationLevelAsInt(amountOfNonBillableHours, amountOfBillableHours);
//                employeesAndUtilisation.put(e.getNameDisplay(), workTimeUtilizationLevel);
//            }
//            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//            fillDatasetWithData(employeesAndUtilisation, dataset, "Work time utilisation in % (billable vs whole work time)");
//            JFreeChart chart = ChartFactory.createBarChart3D("Comparison between employees - work time utilisation for: " + thisMonthFirstMonday.toString() + " - " + endDate.toString(),
//                    "Employees", "%", dataset, PlotOrientation.VERTICAL, true, true, false);
//            setChartView(chart);
//            generateChart(out, chart);
//        } catch (IOException e) {
//        }
//    }


}







