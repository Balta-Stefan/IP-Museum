package museum.service.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import museum.service.models.enums.Roles;

@Data
@AllArgsConstructor
public class LoginDetailsDTO
{
    private final Integer id;
    private final Roles role;
}
