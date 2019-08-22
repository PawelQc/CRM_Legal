package pl.qceyco.app.charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.qceyco.app.employee.EmployeeRepository;
import pl.qceyco.app.project.Project;
import pl.qceyco.app.project.ProjectRepository;
import pl.qceyco.app.timesheet.referenceUnit.TimesheetReferenceUnit;
import pl.qceyco.app.timesheet.referenceUnit.TimesheetReferenceUnitRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.time.temporal.TemporalAdjusters.firstInMonth;

@Controller
public class ChartsController {

    private final ProjectRepository projectRepository;
    private final TimesheetReferenceUnitRepository timesheetReferenceUnitRepository;
    private final EmployeeRepository employeeRepository;

    public ChartsController(ProjectRepository projectRepository, TimesheetReferenceUnitRepository timesheetReferenceUnitRepository, EmployeeRepository employeeRepository) {
        this.projectRepository = projectRepository;
        this.timesheetReferenceUnitRepository = timesheetReferenceUnitRepository;
        this.employeeRepository = employeeRepository;
    }


    @RequestMapping(value = "/charts", method = RequestMethod.GET)
    public void handleReports(HttpServletResponse response) {
        response.setContentType("image/jpeg");
        try (OutputStream out = response.getOutputStream()) {
            LocalDate thisMonthFirstMonday = LocalDate.now().with(firstInMonth(DayOfWeek.MONDAY));
            LocalDate endDate = thisMonthFirstMonday.plusDays(27);
            List<Project> projects = projectRepository.findAll();
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            for (Project p : projects) {
                Integer hours = 0;
                List<TimesheetReferenceUnit> projectTimesheets = timesheetReferenceUnitRepository.findAllByProjectIn4Weeks(p.getId(), thisMonthFirstMonday, endDate);
                for (TimesheetReferenceUnit t : projectTimesheets) {
                    hours += t.countWeekHours();
                }
                dataset.setValue(hours, "Hours", p.getSignature());
            }

            JFreeChart chart = ChartFactory.createBarChart("Work hours on projects: " + thisMonthFirstMonday.toString() + " - " + endDate.toString(),
                    "Projects", "Hours", dataset, PlotOrientation.VERTICAL,
                    false, true, false);
            try {
                ChartUtilities.writeChartAsJPEG(out, chart, 1200, 720);
            } catch (IOException e) {
                System.err.println("Problem occurred creating chart.");
            }
        } catch (IOException e) {
            System.err.println("Problem occurred creating chart.");
        }


//        return "chart";
    }


}







