package com.safetycar.services;

import com.safetycar.models.VerificationToken;
import com.safetycar.models.users.User;
import com.safetycar.models.users.UserDetails;
import com.safetycar.repositories.UserDetailsRepository;
import com.safetycar.repositories.UserRepository;
import com.safetycar.exceptions.AccountNotActivatedException;
import com.safetycar.exceptions.DuplicateEntityException;
import com.safetycar.exceptions.EntityNotFoundException;
import com.safetycar.exceptions.ExpiredException;
import com.safetycar.repositories.filter.UserSpec;
import com.safetycar.services.contracts.VerificationTokenService;
import com.safetycar.services.factories.UserSpecFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.*;

import static com.safetycar.SafetyCarTestObjectsFactory.*;
import static com.safetycar.util.Constants.Views.QueryConstants.ID;
import static com.safetycar.util.Constants.Views.QueryConstants.SORT_PARAMETER;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private UserSpecFactory mockUserSpecFactory;

    @Mock
    private UserDetailsRepository mockDetailsRepository;

    @Mock
    private VerificationTokenService tokenService;

    private User testUser;
    private UserDetails testUserDetails;
    private List<User> userList;
    private List<UserDetails> userDetailsList;
    private VerificationToken testToken;

    @BeforeEach
    void init() {
        testUser = getUser();
        testUserDetails = testUser.getUserDetails();
        testToken = getToken(testUser);
        userList = Collections.singletonList(testUser);
        userDetailsList = Collections.singletonList(testUserDetails);
    }

    @Test
    public void create_ShouldCreateUser_IfUserIsUnique() {
        //arrange
        Mockito.when(mockUserRepository.existsByUserName(Mockito.any()))
                .thenReturn(false);
        //act
        userService.create(testUser);
        //assert
        Mockito.verify(mockUserRepository,
                Mockito.times(1)).save(testUser);
    }

    @Test
    public void create_ShouldThrow_WhenDuplicateUser() {
        //arrange
        Mockito.when(mockUserRepository.existsByUserName(Mockito.any()))
                .thenReturn(true);
        //act, assert
        Assertions.assertThrows(DuplicateEntityException.class,
                () -> userService.create(testUser));
        Mockito.verify(mockUserRepository,
                Mockito.times(0)).save(testUser);
    }

    @Test
    public void update_ShouldUpdate_WhenDetailsUnique() {
        //arrange
        Mockito.when(mockUserRepository.existsByUserDetails(Mockito.any()))
                .thenReturn(false);
        //act
        userService.update(testUser);
        //assert
        Mockito.verify(mockUserRepository,
                Mockito.times(1)).save(testUser);
    }

    @Test
    public void update_ShouldThrow_WhenDuplicateDetails() {
        //arrange
        Mockito.when(mockUserRepository.existsByUserDetails(Mockito.any()))
                .thenReturn(true);
        //act, assert
        Assertions.assertThrows(DuplicateEntityException.class,
                () -> userService.update(testUser));
        Mockito.verify(mockUserRepository,
                Mockito.times(0)).save(testUser);
    }

    @Test
    public void get_ShouldReturnUser_WhenUserExists() {
        //arrange
        Mockito.when(mockUserRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.of(testUser));
        //act
        User actual = userService.userByEmail(testUser.getUserName());
        //assert
        Assertions.assertEquals(testUser, actual);

    }

    @Test
    public void get_ShouldThrow_WhenUserDoesNotExist() {
        //arrange
        Mockito.when(mockUserRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.empty());

        //act, assert
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> userService.userByEmail(testUser.getUserName()));
    }

    @Test
    public void get_ShouldThrow_WhenAccountNotActive() {
        //arrange
        testUser.setEnabled(false);
        Mockito.when(mockUserRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.of(testUser));
        //act, assert
        Assertions.assertThrows(AccountNotActivatedException.class,
                () -> userService.userByEmail(testUser.getUserName()));
    }

    @Test
    public void getUserByToken_ShouldReturnUser_WhenTokenNotExpired() {
        //arrange
        Mockito.when(tokenService.getVerificationToken(Mockito.anyString())).thenReturn(testToken);
        //act
        User actual = userService.getUserByToken(Mockito.anyString());
        //assert
        Assertions.assertEquals(testUser, actual);
    }

    @Test
    public void getUserByToken_ShouldThrow_WhenTokenExpired() {
        //arrange
        Mockito.when(tokenService.getVerificationToken(Mockito.anyString()))
                .thenThrow(ExpiredException.class);
        //act, assert
        Assertions.assertThrows(ExpiredException.class,
                () -> userService.getUserByToken(Mockito.anyString()));
    }

    @Test
    public void getUserByToken_ShouldThrow_WhenTokenInvalid() {
        //arrange
        Mockito.when(tokenService.getVerificationToken(Mockito.anyString()))
                .thenThrow(EntityNotFoundException.class);
        //act, assert
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> userService.getUserByToken(Mockito.anyString()));
    }

    @Test
    public void getAll_ShouldReturnCollection() {
        //arrange
        Mockito.when(mockUserRepository.findAll()).thenReturn(userList);
        //act
        Iterable<User> actual = userService.getAll();
        //assert
        Assertions.assertEquals(actual, userList);
    }

    @Test
    public void existsByMail_ShouldReturn() {
        //arrange
        boolean expected = false;
        Mockito.when(mockUserRepository.existsByUserName(Mockito.any()))
                .thenReturn(expected);
        //act
        boolean actual = userService.existsByMail(Mockito.anyString());
        //assert
        Mockito.verify(mockUserRepository, Mockito.times(1))
                .existsByUserName(Mockito.any());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getAllDetails_ShouldReturnCollection() {
        //arrange
        Map<String, String> filter = new HashMap<>();
        UserSpec spec = new UserSpec(filter);
        spec.addSpec(SORT_PARAMETER, ID);
        Sort sort = spec.getSort();
        Mockito.when(mockUserSpecFactory.getUserSpec()).thenReturn(spec);
        Mockito.when(mockUserRepository.findAll(spec, sort)).thenReturn(userList);
        //act
        List<UserDetails> actual = userService.getAllDetails();
        //assert
        Assertions.assertEquals(userDetailsList, actual);
    }

    @Test
    public void search_ShouldReturnCollection() {
        //arrange
        Map<String, String> filter = new HashMap<>();
        UserSpec spec = new UserSpec(filter);
        Sort sort = spec.getSort();
        Mockito.when(mockUserSpecFactory.getUserSpec(filter)).thenReturn(spec);
        Mockito.when(mockUserRepository.findAll(spec, sort)).thenReturn(userList);
        //act
        List<UserDetails> actual = userService.searchDetails(filter);
        //assert
        Assertions.assertEquals(userDetailsList, actual);
    }

    @Test
    public void getUserDetails_ShouldThrow_WhenItDoesNotExist() {
        //arrange
        Mockito.when(mockDetailsRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.empty());
        //act, assert
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> userService.getUserDetails(Mockito.anyInt()));

    }

    @Test
    public void getUserDetails_ShouldReturnDetails_WhenTheyExist() {
        //arrange
        Mockito.when(mockDetailsRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(testUserDetails));
        //act
        UserDetails actual = userService.getUserDetails(Mockito.anyInt());
        // assert
        Assertions.assertEquals(testUserDetails, actual);

    }


}
