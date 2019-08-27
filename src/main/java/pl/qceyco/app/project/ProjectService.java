package pl.qceyco.app.project;

import org.springframework.stereotype.Service;
import pl.qceyco.app.client.Client;
import pl.qceyco.app.client.ClientRepository;
import pl.qceyco.app.employee.Employee;
import pl.qceyco.app.employee.EmployeeRepository;
import pl.qceyco.app.timesheet.referenceUnit.TimesheetReferenceUnit;
import pl.qceyco.app.timesheet.referenceUnit.TimesheetReferenceUnitRepository;

import java.util.List;

@Service
public class ProjectService {

    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final TimesheetReferenceUnitRepository timesheetReferenceUnitRepository;

    public ProjectService(ClientRepository clientRepository, EmployeeRepository employeeRepository, ProjectRepository projectRepository,
                          TimesheetReferenceUnitRepository timesheetReferenceUnitRepository) {
        this.clientRepository = clientRepository;
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.timesheetReferenceUnitRepository = timesheetReferenceUnitRepository;
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
        List<TimesheetReferenceUnit> timesheets = timesheetReferenceUnitRepository.findAllByProjectId(projectId);
        for (TimesheetReferenceUnit t : timesheets) {
            timesheetReferenceUnitRepository.delete(t);
        }
        projectRepository.deleteById(projectId);
    }

}
