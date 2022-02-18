package museum.service.models.DTOs;

import lombok.Data;
import museum.service.models.enums.Roles;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UserDTO
{
    private Integer userID;

    @NotBlank
    private String firstName, lastName, username, password;

    @NotBlank
    @Email
    private String email;

    private Roles role;

    private Boolean active;

    private List<AccessTokenDTO> tokens;
}
