package pl.qceyco.employee.userInfo;

import org.springframework.stereotype.Service;
import pl.qceyco.employee.Employee;
import pl.qceyco.employee.EmployeeRepository;
import pl.qceyco.employee.additinalInfo.AdditionalInfoEmployee;
import pl.qceyco.employee.additinalInfo.AdditionalInfoEmployeeRepository;

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
