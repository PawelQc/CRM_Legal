package pl.qceyco.app.employee.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {

    int min();

    int max();

    String message() default "password should be minimum 8 and maximum 60 characters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}