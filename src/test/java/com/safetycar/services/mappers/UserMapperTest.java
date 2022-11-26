package com.safetycar.services.mappers;

import com.safetycar.models.users.User;
import com.safetycar.models.users.UserDetails;
import com.safetycar.web.dto.mappers.UserMapper;
import com.safetycar.web.dto.user.CreateDetailsDto;
import com.safetycar.web.dto.user.CreateUserDto;
import com.safetycar.web.dto.user.ShowUserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Set;

import static com.safetycar.SafetyCarTestObjectsFactory.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class UserMapperTest {

    @InjectMocks
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    private User testUser;
    private ShowUserDto testShowDto;
    private CreateDetailsDto testCreateDetailsDto;
    private CreateUserDto testCreateUserDto;

    @BeforeEach
    void init() {
        testUser = getUser();
        testShowDto = getShowUserDto(testUser);
        testCreateUserDto = getCreateUserDto(testUser);
        testShowDto = getShowUserDto(testUser);
        testCreateDetailsDto = getCreateDetailsDto(testUser);
    }

    @Test
    public void fromDto_withCreateUserDto_ShouldReturn_User() {
        //arrange
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn(testUser.getPassword());
        UserDetails expected = new UserDetails();
        expected.setUserName(testUser.getUserName());
        testUser.setUserDetails(expected);
        testUser.setEnabled(false);
        //act
        User actual = userMapper.fromDto(testCreateUserDto);
        //assert
        Assertions.assertEquals(testUser, actual);
    }

    @Test
    public void fromDto_withCreateDetailsDto_ShouldReturn_UserDetails() {
        //arrange, act
        UserDetails actual = userMapper.fromDto(testCreateDetailsDto, testUser.getUserDetails());
        //assert
        Assertions.assertEquals(testUser.getUserDetails(), actual);

    }

    @Test
    public void toDto_WithUser_ShouldReturn_CreateDetailsDto() {
        //arrange, act
        CreateDetailsDto actual = userMapper.toDto(testUser);
        //assert
        Assertions.assertEquals(testCreateDetailsDto, actual);
    }

    @Test
    public void toDto_WithUserDetails_ShouldReturn_ShowUserDto() {
        //arrange, act
        ShowUserDto actual = userMapper.toDto(testUser.getUserDetails());
        //assert
        Assertions.assertEquals(testShowDto, actual);
    }

    @Test
    public void toDto_WithUserDetailsIterable_ShouldReturn_ShowUserDtoCollection() {
        //arrange, act
        Collection<ShowUserDto> expected = new LinkedList<>(Collections.singleton(testShowDto));
        Collection<ShowUserDto> actual = userMapper.toDto(Collections.singleton(testUser.getUserDetails()));
        //assert
        Assertions.assertEquals(expected, actual);
    }

}
