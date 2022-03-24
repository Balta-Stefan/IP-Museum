package com.virtualbank.security;

import com.virtualbank.services.ApiUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    private final ApiUserDetailsService apiUserDetailsService;

    @Value("${bcrypt.strength}")
    private int bcryptStrength;

    public SecurityConfig(ApiUserDetailsService apiUserDetailsService)
    {
        this.apiUserDetailsService = apiUserDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.cors().and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/v1/company").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/person").permitAll()
                .antMatchers(HttpMethod.PATCH, "/api/v1/payments/**").permitAll()
                .antMatchers("/gui").permitAll()
                .anyRequest().authenticated().and().httpBasic();
    }

    /*@Override
    public void configure(WebSecurity web) throws Exception
    {
        web.ignoring()
                .antMatchers(HttpMethod.POST, "/api/v1/company")
                .antMatchers(HttpMethod.POST, "/api/v1/person")
                .regexMatchers(HttpMethod.POST, "/api/v1/payments/[0-9a-z-]+")
                .antMatchers("/gui");
    }*/

    @Bean
    public CorsFilter corsFilter()
    {
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(List.of("*"));
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addAllowedMethod("PATCH");
        source.registerCorsConfiguration("/api/v1/payments/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider()
    {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(apiUserDetailsService);

        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.authenticationProvider(daoAuthenticationProvider());
    }


    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder(bcryptStrength);
    }

}
