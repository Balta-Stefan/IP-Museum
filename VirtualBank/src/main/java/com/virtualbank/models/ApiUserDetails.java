package com.virtualbank.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class ApiUserDetails implements UserDetails
{
    private final boolean enabled;
    private final String username, password;
    private final List<GrantedAuthority> authorities;
    private final Integer id;

    public ApiUserDetails(boolean enabled, String username, String password, List<GrantedAuthority> authorities, Integer id)
    {
        this.enabled = enabled;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.id = id;
    }

    public Integer getId()
    {
        return id;
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
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return enabled;
    }

    @Override
    public boolean isEnabled()
    {
        return enabled;
    }
}
