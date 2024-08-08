package matt.pass.mojaryba.domain.user.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = UserNickValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface UserNickConstraint {
    String message() default "Podana nazwa użytkownika jest już zajęta";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
