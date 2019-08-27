package pl.qceyco.app.employee.userInfo;

import org.springframework.stereotype.Service;
import pl.qceyco.app.employee.Employee;
import pl.qceyco.app.employee.EmployeeRepository;
import pl.qceyco.app.employee.additinalInfo.AdditionalInfoEmployee;
import pl.qceyco.app.employee.additinalInfo.AdditionalInfoEmployeeRepository;

@Service
public class UserInfoService {

    private final AdditionalInfoEmployeeRepository additionalInfoEmployeeRepository;
    private final EmployeeRepository employeeRepository;

    public UserInfoService(AdditionalInfoEmployeeRepository additionalInfoEmployeeRepository, EmployeeRepository employeeRepository) {
        this.additionalInfoEmployeeRepository = additionalInfoEmployeeRepository;
        this.employeeRepository = employeeRepository;
    }

    Employee getEmployeeById(Long employeeId) {
        return employeeRepository.findFirstById(employeeId);
    }

    AdditionalInfoEmployee getAdditionalInfoById(Long infoId) {
        return additionalInfoEmployeeRepository.findFirstById(infoId);
    }

    void saveUpdate(AdditionalInfoEmployee additionalInfoEmployee) {
        additionalInfoEmployeeRepository.save(additionalInfoEmployee);
    }

}
