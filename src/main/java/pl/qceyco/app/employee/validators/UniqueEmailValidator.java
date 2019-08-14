package pl.qceyco.app.employee.validators;


import org.springframework.beans.factory.annotation.Autowired;
import pl.qceyco.app.employee.Employee;
import pl.qceyco.app.employee.EmployeeRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;


public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void initialize(UniqueEmail uniqueEmail) {
    }

    @Override
    public boolean isValid(String userEmail, ConstraintValidatorContext constraintValidatorContext) {
        List<Employee> employees =
                employeeRepository.findAll();   //todo problem ze wstrzyknieciem - pokazuje ze to jest null - ALTERNATYWNIE ZRÓB WALIDACJĘ W KONTROLERZE
        boolean result = true;
        for (Employee e : employees) {
            if (e.getEmailLogin().equals(userEmail)) {
                result = false;
                break;
            }
        }
        return result;
    }
}

