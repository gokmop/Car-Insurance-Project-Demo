package com.safetycar.services.managers;

import com.safetycar.models.Offer;
import com.safetycar.models.VerificationToken;
import com.safetycar.models.users.User;
import com.safetycar.models.users.UserDetails;
import com.safetycar.services.AccountManagerImpl;
import com.safetycar.services.contracts.MailService;
import com.safetycar.services.contracts.OfferAppendingService;
import com.safetycar.services.contracts.UserService;
import com.safetycar.services.contracts.VerificationTokenService;
import com.safetycar.web.dto.user.CreateDetailsDto;
import com.safetycar.web.dto.user.CreateUserDto;
import com.safetycar.web.dto.mappers.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.safetycar.SafetyCarTestObjectsFactory.*;

@ExtendWith(MockitoExtension.class)
public class AccountManagerTest {

    @InjectMocks
    private AccountManagerImpl accountManager;

    @Mock
    private UserMapper mockUserMapper;
    @Mock
    private MailService mockMailService;
    @Mock
    private VerificationTokenService mockTokenService;
    @Mock
    private OfferAppendingService mockOfferAppendingService;
    @Mock
    private UserService mockUserService;
    @Mock
    private HttpServletRequest testRequest;
    @Mock
    private HttpSession testSession;

    private User testUser;
    private UserDetails testDetails;
    private Offer testOffer;
    private VerificationToken testToken;
    private CreateUserDto testCreateDto;
    private CreateDetailsDto testDetailsDto;

    @BeforeEach
    void init() {
        testOffer = getOffer();
        testUser = getUser();
        testDetails = testUser.getUserDetails();
        testToken = getToken(testUser);
        testDetailsDto = getDetailsDto(testUser);
    }

    @Test
    public void registerUser_ShouldRegisterUser() {
        //arrange
        Mockito.when(mockUserMapper.fromDto(testCreateDto))
                .thenReturn(testUser);
        Mockito.when(testRequest.getSession()).thenReturn(testSession);
        Mockito.when(mockTokenService.getNewUnsavedToken(testUser))
                .thenReturn(testToken);
        //act
        accountManager.registerUser(testCreateDto, testOffer, testRequest);
        //assert
        Mockito.verify(mockUserService, Mockito.times(1))
                .create(testUser);
        Mockito.verify(mockOfferAppendingService, Mockito.times(1))
                .appendOffer(testOffer, testUser);
        Mockito.verify(mockTokenService, Mockito.times(1))
                .createVerificationToken(testToken);
        Mockito.verify(mockMailService, Mockito.times(1))
                .sendConfirmationMail(Mockito.any());
    }

    @Test
    public void activateAccount_ShouldActivate() {
        //arrange, act
        accountManager.activateAcc(testToken);
        //assert
        Mockito.verify(mockUserService, Mockito.times(1))
                .update(testUser);
    }

    @Test
    public void getVerificationToken_ShouldGet() {
        //arrange
        Mockito.when(mockTokenService.getVerificationToken(Mockito.anyString()))
                .thenReturn(testToken);
        //act
        VerificationToken actual = accountManager.getVerificationToken(Mockito.anyString());
        //assert
        Assertions.assertEquals(testToken, actual);
    }

    @Test
    public void getDetailsToEdit_ShouldGetDetailsDto() {
        //arrange
        Mockito.when(mockUserMapper.toDto(testUser)).thenReturn(testDetailsDto);
        Mockito.when(mockUserService.userByEmail(Mockito.anyString()))
                .thenReturn(testUser);
        //act
        CreateDetailsDto actual = accountManager.getDetailsToEdit(Mockito.anyString());
        //assert
        Assertions.assertEquals(testDetailsDto, actual);
    }

    @Test
    public void updateDetails_ShouldUpdate() {
        //arrange
        Mockito.when(mockUserService.userByEmail(Mockito.anyString()))
                .thenReturn(testUser);
        Mockito.when(mockUserMapper.fromDto(testDetailsDto, testDetails))
                .thenReturn(testDetails);
        //act
        accountManager.updateDetails(testDetailsDto,Mockito.anyString());
        //assert
        Mockito.verify(mockUserService,Mockito.times(1))
                .update(testUser);
    }

}
