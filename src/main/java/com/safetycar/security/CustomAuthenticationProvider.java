package com.safetycar.security;


import com.safetycar.exceptions.AccountNotActivatedException;
import com.safetycar.exceptions.EntityNotFoundException;
import com.safetycar.models.users.User;
import com.safetycar.services.contracts.UserService;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    public static final String INVALID_USERNAME_OR_PASSWORD = "Invalid username or password";

    private final UserService userService;

    public CustomAuthenticationProvider(@Autowired UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        try {
            final User user = userService.userByEmail(auth.getName());
            final Authentication result = super.authenticate(auth);
            //we use our user
            return new UsernamePasswordAuthenticationToken(user,
                    result.getCredentials(),
                    result.getAuthorities());
        } catch (final EntityNotFoundException e) {
            throw new BadCredentialsException(INVALID_USERNAME_OR_PASSWORD);
        } catch (AccountNotActivatedException e) {
            throw new DisabledException(e.getMessage());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}