package pl.qceyco.client.additionalInfo.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = BankAccountValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BankAccount {

    String message() default "incorrect bank account length - should be 26 digits";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}