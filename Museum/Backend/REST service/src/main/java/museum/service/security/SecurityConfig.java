package museum.service.security;

import museum.service.filters.JwtAuthorizationFilter;
import museum.service.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
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
import org.springframework.security.web.authentication.logout.LogoutHandler;


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
        private final LogoutHandler customLogoutHandler;
        private final AdminTokenAuthenticationProvider adminTokenAuthenticationProvider;

        public MvcSecurityConfig(LogoutHandler customLogoutHandler, AdminTokenAuthenticationProvider adminTokenAuthenticationProvider)
        {
            this.customLogoutHandler = customLogoutHandler;
            this.adminTokenAuthenticationProvider = adminTokenAuthenticationProvider;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception
        {
            http.csrf().disable()
                    .antMatcher("/admin/**")
                    .authorizeRequests()
                    .antMatchers("/index.html", "/", "/css/*", "/js/*").permitAll()
                    //.antMatchers("/admin/**").hasAuthority("ADMIN")
                    .antMatchers(HttpMethod.GET, "/admin/login").permitAll()
                    .anyRequest().authenticated();
                    /*.and()
                    .authenticationProvider(adminTokenAuthenticationProvider);*/
                    /*.and()
                    .logout()
                        .logoutUrl("/logout")
                        .addLogoutHandler(customLogoutHandler)
                        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID");*/
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
        private final LogoutHandler customLogoutHandler;
        private final JwtAuthorizationFilter authorizationFilter;

        public RestSecurityConfig(CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder, LogoutHandler customLogoutHandler, JwtAuthorizationFilter authorizationFilter)
        {
            this.customUserDetailsService = customUserDetailsService;
            this.passwordEncoder = passwordEncoder;
            this.customLogoutHandler = customLogoutHandler;
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

            /*http
                    .antMatcher("/api/v1/**")
					.cors().and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                    .and()
                    .authorizeRequests()
                    //.antMatchers("/index.html", "/", "/css/*", "/js/*").permitAll()
                    .antMatchers(HttpMethod.POST,"/api/v1/user").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/v1/transactions").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .httpBasic()
                    .and()
                        .logout()
                        .logoutUrl("/api/v1/logout")
                        .addLogoutHandler(customLogoutHandler)
                        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID");*/
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
