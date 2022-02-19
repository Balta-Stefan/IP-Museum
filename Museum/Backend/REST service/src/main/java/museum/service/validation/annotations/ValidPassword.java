package museum.service.validation.annotations;

import museum.service.validation.validators.CustomPasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CustomPasswordValidator.class})
public @interface ValidPassword
{
    String message() default "Password must be at least 15 characters long, contain lowercase and uppercase letters and a number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
