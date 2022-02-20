package museum.service.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import museum.service.models.enums.Roles;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class UserDTO
{
    private Integer userID;

    @NotBlank
    private String firstName, lastName, username;

    @NotBlank
    @Email
    private String email;

    private Boolean active;

    private String password;
    private Roles role;
    private String token;
}
