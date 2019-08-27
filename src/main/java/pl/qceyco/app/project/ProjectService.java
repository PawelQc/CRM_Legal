package pl.qceyco.app.project;

import org.springframework.stereotype.Service;
import pl.qceyco.app.client.Client;
import pl.qceyco.app.client.ClientRepository;
import pl.qceyco.app.employee.Employee;
import pl.qceyco.app.employee.EmployeeRepository;
import pl.qceyco.app.timesheet.unit.TimesheetUnit;
import pl.qceyco.app.timesheet.unit.TimesheetUnitRepository;

import java.util.List;

@Service
public class ProjectService {

    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final TimesheetUnitRepository timesheetUnitRepository;

    public ProjectService(ClientRepository clientRepository, EmployeeRepository employeeRepository, ProjectRepository projectRepository,
                          TimesheetUnitRepository timesheetUnitRepository) {
        this.clientRepository = clientRepository;
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.timesheetUnitRepository = timesheetUnitRepository;
    }

    List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    List<Project> getAllProjects() {
        return projectRepository.findAllWithProjectTeamMembers();
    }

    Project getProjectById(Long projectId) {
        return projectRepository.findFirstByIdWithProjectTeamMembers(projectId);
    }

    void save(Project project) {
        projectRepository.save(project);
    }

    void delete(Long projectId) {
        List<TimesheetUnit> timesheets = timesheetUnitRepository.findAllByProjectId(projectId);
        for (TimesheetUnit t : timesheets) {
            timesheetUnitRepository.delete(t);
        }
        projectRepository.deleteById(projectId);
    }

}
