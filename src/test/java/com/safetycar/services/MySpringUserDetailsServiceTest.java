package com.safetycar.services;

import com.safetycar.security.MySpringUserDetailsService;
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
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static com.safetycar.SafetyCarTestObjectsFactory.getSpringUser;
import static com.safetycar.SafetyCarTestObjectsFactory.getUser;

@ExtendWith(MockitoExtension.class)
public class MySpringUserDetailsServiceTest {

    @InjectMocks
    private MySpringUserDetailsService userDetailsService;

    @Mock
    private UserService mockUserService;

    private com.safetycar.models.users.User testUser;
    private User springUser;

    @BeforeEach
    void init() {
        testUser = getUser();
        springUser = getSpringUser(testUser);
    }

    @Test
    public void throw_AppropriateException_whenUserDoesNotExist() {
        //arrange
        Mockito.when(mockUserService.userByEmail(Mockito.any()))
                .thenThrow(EntityNotFoundException.class);
        //act, assert
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(Mockito.any()));
    }

    @Test
    public void throw_AppropriateException_whenAccountNotActive() {
        //arrange
        Mockito.when(mockUserService.userByEmail(Mockito.any()))
                .thenThrow(AccountNotActivatedException.class);
        //act, assert
        Assertions.assertThrows(DisabledException.class,
                () -> userDetailsService.loadUserByUsername(Mockito.any()));
    }

    @Test
    public void loadByUsername_ShouldReturnSpringUser_WhenUserValid() {
        //arrange
        Mockito.when(mockUserService.userByEmail(Mockito.any()))
                .thenReturn(testUser);
        //act
        UserDetails actual = userDetailsService
                .loadUserByUsername(Mockito.any());
        //assert
        Assertions.assertEquals(springUser, actual);

    }


}
