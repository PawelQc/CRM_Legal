package pl.qceyco.app.employee;

import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import pl.qceyco.app.employee.additinalInfo.AdditionalInfoEmployeeRepository;
import pl.qceyco.app.project.Project;
import pl.qceyco.app.project.ProjectRepository;
import pl.qceyco.app.secureApp.Authority;
import pl.qceyco.app.secureApp.AuthorityRepository;
import pl.qceyco.app.timesheet.referenceUnit.TimesheetReferenceUnit;
import pl.qceyco.app.timesheet.referenceUnit.TimesheetReferenceUnitRepository;

import java.util.List;
import java.util.Set;

@Service
public class EmployeeService {

    private final AuthorityRepository authorityRepository;
    private final ProjectRepository projectRepository;
    private final AdditionalInfoEmployeeRepository additionalInfoEmployeeRepository;
    private final EmployeeRepository employeeRepository;
    private final TimesheetReferenceUnitRepository timesheetReferenceUnitRepository;

    public EmployeeService(EmployeeRepository employeeRepository, AdditionalInfoEmployeeRepository additionalInfoEmployeeRepository,
                           ProjectRepository projectRepository, AuthorityRepository authorityRepository,
                           TimesheetReferenceUnitRepository timesheetReferenceUnitRepository) {
        this.employeeRepository = employeeRepository;
        this.additionalInfoEmployeeRepository = additionalInfoEmployeeRepository;
        this.projectRepository = projectRepository;
        this.authorityRepository = authorityRepository;
        this.timesheetReferenceUnitRepository = timesheetReferenceUnitRepository;
    }

    List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    boolean emailIsUniqueAdd(String userEmail) {
        List<Employee> employees = employeeRepository.findAll();
        boolean result = true;
        for (Employee e : employees) {
            if (e.getEmailLogin().equals(userEmail)) {
                result = false;
                break;
            }
        }
        return result;
    }

    boolean emailIsUniqueUpdate(String newEmail, String previousEmail) {
        List<Employee> employees = employeeRepository.findAllExceptForCurrentEmployee(previousEmail);
        boolean result = true;
        for (Employee e : employees) {
            if (e.getEmailLogin().equals(newEmail)) {
                result = false;
                break;
            }
        }
        return result;
    }

    void encryptPassword(Employee employee) {
        employee.setPassword(BCrypt.hashpw(employee.getPassword(), BCrypt.gensalt()));
    }

    void setAuthority(Employee employee) {
        Authority authority = null;
        if (employee.getAdmin() == true) {
            authority = authorityRepository.findFirstByName("ROLE_ADMIN");
        } else {
            authority = authorityRepository.findFirstByName("ROLE_USER");
        }
        Set<Authority> employeeAuthorities = employee.getAuthorities();
        employeeAuthorities.add(authority);
        employee.setAuthorities(employeeAuthorities);
    }

    void save(Employee employee) {
        employeeRepository.save(employee);
    }

    List<Project> getAllProjectsWhereEmployeeParticipates(Long employeeId) {
        return projectRepository.findAllByEmployeeId(employeeId);
    }

    void delete(Long employeeId) {
        Employee employeeToDelete = employeeRepository.findFirstById(employeeId);
        List<TimesheetReferenceUnit> employeesTimesheets = timesheetReferenceUnitRepository.findAllByEmployeeId(employeeToDelete.getId());
        if (employeesTimesheets.size() > 0) {
            for (TimesheetReferenceUnit t : employeesTimesheets) {
                timesheetReferenceUnitRepository.delete(t);
            }
        }
        employeeRepository.deleteById(employeeId);
        if (employeeToDelete.getAdditionalInfo() != null) {
            Long infoId = employeeToDelete.getAdditionalInfo().getId();
            additionalInfoEmployeeRepository.deleteById(infoId);
        }
    }

    Employee getEmployeeById(Long employeeId) {
        return employeeRepository.findFirstById(employeeId);
    }

    boolean passwordFailedValidation(String password) {
        return StringUtils.isBlank(password) || password.length() < 8 || password.length() > 60;
    }

}
