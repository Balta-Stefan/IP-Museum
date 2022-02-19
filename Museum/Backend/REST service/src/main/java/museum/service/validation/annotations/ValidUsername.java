package museum.service.validation.annotations;

import museum.service.validation.validators.UsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UsernameValidator.class})
public @interface ValidUsername
{
    String message() default "Username must be at least 12 characters long without @, # and /";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
