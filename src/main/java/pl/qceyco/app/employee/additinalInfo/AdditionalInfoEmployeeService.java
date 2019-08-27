package pl.qceyco.app.employee.additinalInfo;

import org.springframework.stereotype.Service;
import pl.qceyco.app.employee.Employee;
import pl.qceyco.app.employee.EmployeeRepository;

@Service
public class AdditionalInfoEmployeeService {

    private final AdditionalInfoEmployeeRepository additionalInfoEmployeeRepository;
    private final EmployeeRepository employeeRepository;

    public AdditionalInfoEmployeeService(AdditionalInfoEmployeeRepository additionalInfoEmployeeRepository, EmployeeRepository employeeRepository) {
        this.additionalInfoEmployeeRepository = additionalInfoEmployeeRepository;
        this.employeeRepository = employeeRepository;
    }

    Employee getEmployeeById(Long employeeId) {
        return employeeRepository.findFirstById(employeeId);
    }

    AdditionalInfoEmployee getAdditionalInfoById(Long infoId) {
        return additionalInfoEmployeeRepository.findFirstById(infoId);
    }

    void saveAdd(Long employeeId, AdditionalInfoEmployee additionalInfoEmployee) {
        additionalInfoEmployeeRepository.save(additionalInfoEmployee);
        Employee employee = employeeRepository.findFirstById(employeeId);
        employee.setAdditionalInfo(additionalInfoEmployee);
        employeeRepository.save(employee);
    }

    Employee getEmployeeByInfoId(Long infoId) {
        return employeeRepository.findFirstByAdditionalInfo_Id(infoId);
    }

    void saveUpdate(AdditionalInfoEmployee additionalInfoEmployee) {
        additionalInfoEmployeeRepository.save(additionalInfoEmployee);
    }

}
