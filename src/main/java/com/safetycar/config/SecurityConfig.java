package com.safetycar.config;

import com.safetycar.security.CustomAuthenticationProvider;
import com.safetycar.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.sql.DataSource;

import static com.safetycar.util.Constants.ConfigConstants.COM_SAFETYCAR_SECURITY;
import static com.safetycar.util.Constants.ConfigConstants.USER_DETAILS_SERVICE;

@Configuration
@Order(2)
@ComponentScan(basePackages = COM_SAFETYCAR_SECURITY)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String[] ROLES = {"USER,AGENT"};
    public static final String USER = "USER";
    public static final String AGENT = "AGENT";
    private final DataSource dataSource;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    private static final String[] PUBLIC_MATCHERS = {"/css/**", "/js/**",
            "/assets/img/**", "/img/**", "/", "/index", "/login/**", "/login/confirm/**",
            "/register/**", "/images/**", "/proceed","/api/models/**"};
    private static final String[] USER_MATCHERS = {"/details", "/offer",};
    private static final String[] AGENT_MATCHERS = {"/users", "/user",};
    private static final String[] SHARED_MATCHERS = {"/policy", "/policies"};

    @Autowired
    public SecurityConfig(DataSource dataSource,
                          AuthenticationSuccessHandler authenticationSuccessHandler,
                          @Qualifier(USER_DETAILS_SERVICE) UserDetailsService userDetailsService,
                          UserService userService) {
        this.dataSource = dataSource;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @Bean
    public UserDetailsManager userDetailsManager() {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
        jdbcUserDetailsManager.setDataSource(dataSource);
        return jdbcUserDetailsManager;
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        final CustomAuthenticationProvider authProvider = new CustomAuthenticationProvider(userService);
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers(PUBLIC_MATCHERS).permitAll()
                .antMatchers(USER_MATCHERS).hasRole(USER)
                .antMatchers(AGENT_MATCHERS).hasRole(AGENT)
                .antMatchers(SHARED_MATCHERS).hasAnyRole(ROLES)
//                .antMatchers("/api/**").permitAll()//TODO allowed for testing purposes
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .successHandler(authenticationSuccessHandler)
                .failureUrl("/login/error")
                .loginPage("/login")
                .loginProcessingUrl("/authenticate")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and();
    }


}


