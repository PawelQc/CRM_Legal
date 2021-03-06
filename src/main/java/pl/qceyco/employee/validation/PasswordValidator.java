package pl.qceyco.employee.validation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


class PasswordValidator implements ConstraintValidator<Password, String> {

    @Override
    public void initialize(Password password) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return password.length() >= 8 && password.length() <= 60;
    }


}

