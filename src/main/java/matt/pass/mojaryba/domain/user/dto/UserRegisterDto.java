package matt.pass.mojaryba.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import matt.pass.mojaryba.domain.user.validate.UserEmailConstraint;
import matt.pass.mojaryba.domain.user.validate.UserNickConstraint;

public class UserRegisterDto {
    @NotBlank
    @Size(min = 3, message = "Nick musi zawierać min 3 znaki")
    @UserNickConstraint
    private String nick;
    @NotBlank
    @Email
    @UserEmailConstraint
    private String email;
    @NotBlank
    @Size(min = 8, message = "Hasło musi zawierać min 8 znaków")
    private String password;

    public UserRegisterDto(String nick, String email, String password) {
        this.nick = nick;
        this.email = email;
        this.password = password;
    }

    public UserRegisterDto() {
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
