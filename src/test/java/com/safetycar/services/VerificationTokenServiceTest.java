package com.safetycar.services;

import com.safetycar.exceptions.EntityNotFoundException;
import com.safetycar.exceptions.ExpiredException;
import com.safetycar.models.VerificationToken;
import com.safetycar.models.users.User;
import com.safetycar.repositories.TokenRepository;
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
import org.springframework.context.MessageSource;


import static com.safetycar.SafetyCarTestObjectsFactory.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class VerificationTokenServiceTest {

    @InjectMocks
    private VerificationTokenServiceImpl tokenService;

    @Mock
    private TokenRepository mockTokenRepository;

    @Mock
    private MessageSource mockMessageSource;
    @Mock
    private ExpiredTokenDisposalImpl tokenDisposal;

    private User testUser;
    private VerificationToken testToken;

    @BeforeEach
    void init() {
        testUser = getUser();
        testToken = getToken(testUser);
    }

    @Test
    public void getVerificationToken_ShouldReturnToken() {
        //arrange
        Mockito.when(mockTokenRepository.findByToken(Mockito.anyString()))
                .thenReturn(testToken);
        //act
        VerificationToken actual = tokenService.getVerificationToken(Mockito.anyString());
        //assert
        Assertions.assertEquals(actual, testToken);
    }

    @Test
    public void getVerificationToken_ShouldThrow_WhenTokenInvalid() {
        //arrange
        Mockito.when(mockTokenRepository.findByToken(Mockito.anyString()))
                .thenReturn(null);
        //act, assert
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> tokenService.getVerificationToken(Mockito.anyString()));
    }

    @Test
    public void getVerificationToken_ShouldThrow_WhenTokenExpired() {
        //arrange
        testToken = getExpiredToken(testUser);
        Mockito.when(mockTokenRepository.findByToken(Mockito.anyString()))
                .thenReturn(testToken);
        Mockito.when(mockMessageSource.getMessage(Mockito.anyString(), Mockito.any(), Mockito.any()))
                .thenReturn("message");
        //act, assert
        Assertions.assertThrows(ExpiredException.class,
                () -> tokenService.getVerificationToken(Mockito.anyString()));
        Mockito.verify(tokenDisposal, Mockito.times(1)).clearExpired(testToken);
    }

    @Test
    public void createVerificationToken_ShouldCreateToken() {
        //arrange, act
        tokenService.createVerificationToken(Mockito.any());
        //assert
        Mockito.verify(mockTokenRepository, Mockito.times(1)).save(Mockito.any());

    }

    @Test
    public void getNewToken_ShouldGet() {
        //arrange, act
        VerificationToken actual = tokenService.getNewUnsavedToken(testUser);
        //assert
        Assertions.assertEquals(testToken.getUser(), actual.getUser());
        Assertions.assertEquals(testToken.getId(), actual.getId());
        Assertions.assertNotEquals(testToken.getToken(), actual.getToken());
    }


}
