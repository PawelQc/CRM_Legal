package pl.qceyco.app.client.additionalInfo.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

class BankAccountValidator implements ConstraintValidator<BankAccount, String> {

    @Override
    public void initialize(BankAccount password) {
    }

    @Override
    public boolean isValid(String bankAccount, ConstraintValidatorContext constraintValidatorContext) {
        return bankAccount.length() == 26;
    }


}

