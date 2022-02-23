package museum.service.security;

import museum.service.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;


@Configuration
@EnableWebSecurity
public class SecurityConfig
{
    @Value("${passwordencoder.strength}")
    private Integer passwordEncoderStrength;

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder(passwordEncoderStrength);
    }


    @Configuration
    @Order(2)
    public static class MvcSecurityConfig extends WebSecurityConfigurerAdapter
    {
        @Override
        protected void configure(HttpSecurity http) throws Exception
        {
            http.csrf().disable()
                    .antMatcher("/admin/**")
                    .authorizeRequests()
                    .antMatchers("/index.html", "/", "/css/*", "/js/*").permitAll()
                    .anyRequest().authenticated();
        }

        @Bean
        public FilterRegistrationBean<MvcTokenAuthFilter> adminDashboardFilter()
        {
            FilterRegistrationBean<MvcTokenAuthFilter> registrationBean = new FilterRegistrationBean<>();

            registrationBean.setFilter(new MvcTokenAuthFilter());
            registrationBean.addUrlPatterns("/admin/*");

            return registrationBean;
        }
    }

    @Configuration
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    @Order(1)
    public static class RestSecurityConfig extends WebSecurityConfigurerAdapter
    {
        private final CustomUserDetailsService customUserDetailsService;
        private final PasswordEncoder passwordEncoder;

        public RestSecurityConfig(CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder)
        {
            this.customUserDetailsService = customUserDetailsService;
            this.passwordEncoder = passwordEncoder;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception
        {
            auth.authenticationProvider(daoAuthenticationProvider());
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception
        {
            http.csrf().disable()
                    .antMatcher("/api/v1/**")
                    .authorizeRequests()
                    //.antMatchers("/index.html", "/", "/css/*", "/js/*").permitAll()
                    .antMatchers(HttpMethod.POST,"/api/v1/user").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/v1/transactions").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .httpBasic();
        }

        @Bean
        public DaoAuthenticationProvider daoAuthenticationProvider()
        {
            DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
            daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
            daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);

            return daoAuthenticationProvider;
        }

        /*
	@Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        //config.addAllowedOrigin("*");
        config.setAllowedOriginPatterns(List.of("*"));

        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "x-auth-token"));
        config.setExposedHeaders(List.of("x-auth-token"));

        //config.addAllowedHeader("*");
        //config.addAllowedMethod("*");
        //config.addAllowedMethod("PATCH");
        source.registerCorsConfiguration("/**", config);
        config.setExposedHeaders(List.of("Authorization"));
        return new CorsFilter(source);
    }*/
    }
}
