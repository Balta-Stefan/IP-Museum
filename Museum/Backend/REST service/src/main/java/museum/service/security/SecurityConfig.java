package museum.service.security;

import museum.service.filters.PostAuthenticationLoggingFilter;
import museum.service.filters.PreAuthenticationLoggingFilter;
import museum.service.filters.JwtAuthorizationFilter;
import museum.service.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


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
        private final AdminTokenAuthenticationProvider adminTokenAuthenticationProvider;

        public MvcSecurityConfig(AdminTokenAuthenticationProvider adminTokenAuthenticationProvider)
        {
            this.adminTokenAuthenticationProvider = adminTokenAuthenticationProvider;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception
        {
            http
                    .antMatcher("/admin/**")
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/index.html", "/", "/css/*", "/js/*").permitAll()
                    .antMatchers(HttpMethod.GET, "/admin/login").permitAll()
                    .antMatchers("/admin/**").hasAuthority("ADMIN")
                    .anyRequest().authenticated()
                    .and()
                    .logout(logout -> logout
                                    .logoutUrl("/admin/logout")
                                    .clearAuthentication(true)
                                    .invalidateHttpSession(true)
                                    .deleteCookies("JSESSIONID")
                                    .logoutSuccessUrl("/"));
        }


        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception
        {
            auth.authenticationProvider(adminTokenAuthenticationProvider);
        }
    }

    @Configuration
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    @Order(1)
    public static class RestSecurityConfig extends WebSecurityConfigurerAdapter
    {
        private final UserDetailsService customUserDetailsService;
        private final PasswordEncoder passwordEncoder;
        private final JwtAuthorizationFilter authorizationFilter;

        public RestSecurityConfig(CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder, JwtAuthorizationFilter authorizationFilter)
        {
            this.customUserDetailsService = customUserDetailsService;
            this.passwordEncoder = passwordEncoder;
            this.authorizationFilter = authorizationFilter;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception
        {
            auth.authenticationProvider(daoAuthenticationProvider());
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception
        {
            http
                    .antMatcher("/api/v1/**")
                    .cors().and().csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.POST,"/api/v1/user").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/v1/transactions").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/v1/session/login").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
                    //.addFilterBefore(preAuthLoggingFilter, JwtAuthorizationFilter.class); // this will be applied to MVC endpoints too, despite the antMatcher for api endpoint above....
        }

        @Bean
        public FilterRegistrationBean<JwtAuthorizationFilter> apiJwtFilter()
        {
            FilterRegistrationBean<JwtAuthorizationFilter> registrationBean = new FilterRegistrationBean<>();

            registrationBean.setFilter(authorizationFilter);
            registrationBean.addUrlPatterns("/api/v1/*");

            return registrationBean;
        }

        @Bean
        public DaoAuthenticationProvider daoAuthenticationProvider()
        {
            DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
            daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
            daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);

            return daoAuthenticationProvider;
        }

        @Override
        @Bean
        public AuthenticationManager authenticationManagerBean() throws Exception
        {
            return super.authenticationManagerBean();
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
