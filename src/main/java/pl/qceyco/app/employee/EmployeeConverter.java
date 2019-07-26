package pl.qceyco.app.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;


public class EmployeeConverter implements Converter<String, Employee> {

    @Autowired
    EmployeeRepository employeeRepository;

    public Employee convert(String s) {
        return employeeRepository.findFirstEmployeeById(Long.parseLong(s));
    }
}
