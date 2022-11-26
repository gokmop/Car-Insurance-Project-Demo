package com.safetycar.security;




import com.safetycar.exceptions.AccountNotActivatedException;
import com.safetycar.exceptions.EntityNotFoundException;
import com.safetycar.models.Role;
import com.safetycar.models.users.User;
import com.safetycar.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.safetycar.util.Constants.ConfigConstants.USER_DETAILS_SERVICE;

@Service(USER_DETAILS_SERVICE)
@Transactional
public class MySpringUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public MySpringUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {

        try {
            final User user = userService.userByEmail(email);

            return new org.springframework.security.core.userdetails.User(user.getUserName(),
                    user.getPassword(),
                    !user.isDisabled(),
                    true,
                    true,
                    true,
                    getAuthorities(user.getRoles()));

        } catch (final EntityNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        } catch (final AccountNotActivatedException e) {
            throw new DisabledException(e.getMessage());
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    public static List<String> getPrivileges(final Collection<Role> roles) {
        final List<String> privileges = new ArrayList<>();
        for (final Role role : roles) {
            privileges.add(role.getAuthority());
        }
        return privileges;
    }

    public static List<GrantedAuthority> getGrantedAuthorities(final List<String> roles) {
        final List<GrantedAuthority> authorities = new ArrayList<>();
        for (final String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

}