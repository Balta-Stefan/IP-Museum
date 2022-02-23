package museum.service.models;

import lombok.Data;
import museum.service.models.enums.Roles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class CustomUserDetails implements UserDetails
{
    private final Integer id;
    //private final String username, password;
    //private final Boolean active;
    private final Roles role;
    private final List<GrantedAuthority> authorities = new ArrayList<>(1);
    private final UserDTO userDTO;

    private Boolean isLoggedIntoAdminApp = false;

    public CustomUserDetails(UserDTO userDTO)
    {
        this.userDTO = userDTO;
        this.role = userDTO.getRole();
        //this.username = userDTO.getUsername();
        this.id = userDTO.getUserID();
        //this.password = userDTO.getPassword();
        //this.active = userDTO.getActive();


        authorities.add(new SimpleGrantedAuthority(role.name()));
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return authorities;
    }

    @Override
    public String getPassword()
    {
        return userDTO.getPassword();
    }

    @Override
    public String getUsername()
    {
        return userDTO.getUsername();
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return userDTO.getActive();
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return userDTO.getActive();
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return userDTO.getActive();
    }

    @Override
    public boolean isEnabled()
    {
        return userDTO.getActive();
    }
}
