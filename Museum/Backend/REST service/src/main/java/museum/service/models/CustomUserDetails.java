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
    private final String username, password;
    private final String jwt;
    private final Boolean active;
    private final Roles role;
    private final List<GrantedAuthority> authorities = new ArrayList<>(1);
    //private final UserDTO userDTO;

    private Boolean isLoggedIntoAdminApp = false;

    public CustomUserDetails(Integer id, String username, String password, Boolean active, Roles role, String jwt)
    {
        //this.userDTO = userDTO;
        this.role = role;
        this.username = username;
        this.id = id;
        this.password = password;
        this.active = active;
        this.jwt = jwt;


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
        return password;
    }

    @Override
    public String getUsername()
    {
        return username;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return active;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return active;
    }

    @Override
    public boolean isEnabled()
    {
        return active;
    }
}
