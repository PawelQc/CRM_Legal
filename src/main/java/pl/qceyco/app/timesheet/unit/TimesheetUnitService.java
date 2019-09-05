package pl.qceyco.app.timesheet.unit;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import pl.qceyco.app.employee.Employee;
import pl.qceyco.app.employee.EmployeeRepository;
import pl.qceyco.app.project.Project;
import pl.qceyco.app.project.ProjectRepository;
import pl.qceyco.app.timesheet.workWeek.WorkWeek;
import pl.qceyco.app.timesheet.workWeek.WorkWeekRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class TimesheetUnitService {

    private final TimesheetUnitRepository timesheetUnitRepository;
    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;
    private final WorkWeekRepository workWeekRepository;

    public TimesheetUnitService(TimesheetUnitRepository timesheetUnitRepository, ProjectRepository projectRepository,
                                EmployeeRepository employeeRepository, WorkWeekRepository workWeekRepository) {
        this.timesheetUnitRepository = timesheetUnitRepository;
        this.projectRepository = projectRepository;
        this.employeeRepository = employeeRepository;
        this.workWeekRepository = workWeekRepository;
    }

    List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    List<Project> getAllProjects() {
        return projectRepository.findAllWithProjectTeamMembers();
    }

    List<TimesheetUnit> getAllTimesheets() {
        return timesheetUnitRepository.findAll();
    }

    List<TimesheetUnit> getUserTimesheets(Long userId) {
        return timesheetUnitRepository.findAllByEmployeeId(userId);
    }

    List<Project> getUserProjects(Long userId) {
        return projectRepository.findAllByEmployeeId(userId);
    }

    TimesheetUnit getTimesheetById(Long timesheetId) {
        return timesheetUnitRepository.findFirstById(timesheetId);
    }

    List<TimesheetUnit> getAllTimesheetsOfGivenProject(Long projectId) {
        return timesheetUnitRepository.findAllByProjectIdOrderByEmployeeId(projectId);
    }

    List<TimesheetUnit> getUserTimesheetsOfGivenProject(Long projectId, Long employeeId) {
        return timesheetUnitRepository.findAllByProjectIdAndEmployeeId(projectId, employeeId);
    }

    Project getProjectById(Long projectId) {
        return projectRepository.findFirstById(projectId);
    }

    WorkWeek getWorkWeekById(Long workWeekId) {
        return workWeekRepository.findFirstById(workWeekId);
    }

    void saveAdd(TimesheetUnit timesheetUnit) {
        timesheetUnitRepository.save(timesheetUnit);
    }

    void saveUpdate(WorkWeek workWeek) {
        workWeekRepository.save(workWeek);
    }

    void deleteById(Long timesheetUnitId) {
        timesheetUnitRepository.deleteById(timesheetUnitId);
    }

    LocalDate getMondayDate(String mode, String mondaySelect, int i) {
        LocalDate nextMonday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        if (mondaySelect != null) {
            nextMonday = LocalDate.parse(mondaySelect);
        }
        if ("prev".equals(mode)) {
            nextMonday = nextMonday.minusDays(i);
        }
        if ("next".equals(mode)) {
            nextMonday = nextMonday.plusDays(i);
        }
        return nextMonday;
    }

    boolean similarTimesheetExists(Long projectId, WorkWeek workWeek, Model model, Employee loggedInUser) {
        TimesheetUnit timesheetSimilarInDB = timesheetUnitRepository
                .findFirstByEmployeeIdAndProjectIdInSearchPeriod(loggedInUser.getId(), projectId, workWeek.getDateMonday(), workWeek.getDateMonday().plusDays(1));
        if (timesheetSimilarInDB != null) {
            model.addAttribute("errorSimilarTsExists", "Error: there is already existing timesheet for this project and date!");
            model.addAttribute("timesheetSimilarInDB", timesheetSimilarInDB);
            Project project = projectRepository.findFirstById(projectId);
            model.addAttribute("project", project);
            return true;
        }
        return false;
    }

    TimesheetUnit setTimesheetUnitValues(Long projectId, WorkWeek workWeek, Employee loggedInUser) {
        workWeek.setDateMonday(workWeek.getDateMonday());
        workWeekRepository.save(workWeek);
        Project project = projectRepository.findFirstByIdWithProjectTeamMembers(projectId);
        TimesheetUnit timesheetUnit = new TimesheetUnit();
        timesheetUnit.setWorkWeek(workWeek);
        timesheetUnit.setEmployee(loggedInUser);
        timesheetUnit.setProject(project);
        return timesheetUnit;
    }

    boolean noTimesheetInDB(List<TimesheetUnit> timesheets, Model model) {
        if (timesheets.size() == 0) {
            model.addAttribute("errorNoTimesheets", "Error: There are no timesheets to display!");
            return true;
        }
        return false;
    }

    boolean noWorkWeekInDB(Model model, WorkWeek workWeek) {
        if (workWeek == null) {
            model.addAttribute("error", "Update Error");
            return true;
        }
        return false;
    }


}


