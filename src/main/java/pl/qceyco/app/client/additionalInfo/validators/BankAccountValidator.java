package pl.qceyco.app.client.additionalInfo.validators;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class BankAccountValidator implements ConstraintValidator<BankAccount, String> {

    @Override
    public void initialize(BankAccount password) {
    }

    @Override
    public boolean isValid(String bankAccount, ConstraintValidatorContext constraintValidatorContext) {
        return bankAccount.length() == 26;
    }


}

