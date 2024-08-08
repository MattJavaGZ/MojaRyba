package matt.pass.mojaryba.domain.user.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import matt.pass.mojaryba.domain.user.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserNickValidator implements ConstraintValidator<UserNickConstraint, String> {
    private UserService userService;

    public UserNickValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void initialize(UserNickConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String nick, ConstraintValidatorContext constraintValidatorContext) {
        return !userService.chechExistByNick(nick);
    }
}
