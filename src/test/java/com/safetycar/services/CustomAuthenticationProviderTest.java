package com.safetycar.services;

import com.safetycar.models.users.User;
import com.safetycar.security.CustomAuthenticationProvider;
import com.safetycar.services.contracts.UserService;
import com.safetycar.exceptions.AccountNotActivatedException;
import com.safetycar.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static com.safetycar.SafetyCarTestObjectsFactory.getSpringUser;
import static com.safetycar.SafetyCarTestObjectsFactory.getUser;
import static com.safetycar.security.MySpringUserDetailsService.getAuthorities;

@ExtendWith(MockitoExtension.class)
public class CustomAuthenticationProviderTest {

    @InjectMocks
    private CustomAuthenticationProvider authenticationProvider;

    @Mock
    private UserService mockUserService;
    @Mock
    private UserDetailsService mockDetailsService;
    @Mock
    private DaoAuthenticationProvider mockAuthProvider;
    @Mock
    private PasswordEncoder passwordEncoder;
    private Authentication testAuth;
    private User testUser;

    @BeforeEach
    void init() {
        testUser = getUser();
        List<GrantedAuthority> authorities = new LinkedList<>(getAuthorities(testUser.getRoles()));
        testAuth = new UsernamePasswordAuthenticationToken(testUser, testUser.getPassword(), authorities);
    }

    @Test
    public void throw_AppropriateException_whenUserDoesNotExist() {
        //arrange
        Mockito.when(mockUserService.userByEmail(Mockito.anyString()))
                .thenThrow(EntityNotFoundException.class);
        //act, assert
        Assertions.assertThrows(BadCredentialsException.class,
                () -> authenticationProvider.authenticate(testAuth));
    }

    @Test
    public void throw_AppropriateException_whenAccountNotActive() {
        //arrange
        Mockito.when(mockUserService.userByEmail(Mockito.anyString()))
                .thenThrow(AccountNotActivatedException.class);
        //act, assert
        Assertions.assertThrows(DisabledException.class,
                () -> authenticationProvider.authenticate(testAuth));
    }

    @Test
    public void authenticate_ShouldAuthenticate_WhenCredentialsGood() {
        //arrange
        User testUser = getUser();
        authenticationProvider.setUserDetailsService(mockDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        Mockito.when(passwordEncoder.matches(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        UserDetails testSpringUser = getSpringUser(testUser);
        Mockito.when(mockUserService.userByEmail(Mockito.anyString())).thenReturn(testUser);
        Mockito.when(mockDetailsService.loadUserByUsername(Mockito.any())).thenReturn(testSpringUser);
        //act
        Authentication actual = authenticationProvider.authenticate(testAuth);
        //assert
        Assertions.assertTrue(actual.isAuthenticated());
    }

    @Test
    public void supports_ShouldReturnTrue_WhenCorrectToken() {
        //arrange, act, assert
        Assertions.assertTrue(authenticationProvider.supports(testAuth.getClass()));
    }

}
