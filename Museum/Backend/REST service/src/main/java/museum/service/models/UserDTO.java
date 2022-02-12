package museum.service.models;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserDTO
{
    private final Integer userID;

    @NotBlank
    private final String firstName, lastName, username;

    @NotBlank
    @Email
    private final String email;

    private final String password;
    private final Roles role;
}
