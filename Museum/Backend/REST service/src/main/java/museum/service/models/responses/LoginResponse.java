package museum.service.models.responses;

import lombok.Data;
import lombok.EqualsAndHashCode;
import museum.service.models.DTOs.UserDTO;

@EqualsAndHashCode(callSuper = true)
@Data
public class LoginResponse extends UserDTO
{
    private String jwt;
    private String adminToken;
}
