package pl.qceyco.app.employee.validation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class PasswordValidator implements ConstraintValidator<Password, String> {

    private int max;
    private int min;

    @Override
    public void initialize(Password password) {
        this.max = password.max();
        this.min = password.min();
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return password.length() >= 8 && password.length() <= 60;
    }


}

