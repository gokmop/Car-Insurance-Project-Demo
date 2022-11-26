package com.safetycar.services;

import com.safetycar.models.VerificationToken;
import com.safetycar.models.users.User;
import com.safetycar.repositories.RoleRepository;
import com.safetycar.repositories.TokenRepository;
import com.safetycar.repositories.UserRepository;
import com.safetycar.services.contracts.ExpiredTokenDisposal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.safetycar.SafetyCarTestObjectsFactory.getToken;
import static com.safetycar.SafetyCarTestObjectsFactory.getUser;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class ExpiredTokenDisposalTest {

    @InjectMocks
    private ExpiredTokenDisposalImpl tokenDisposal;

    @Mock
    private TokenRepository mockTokenRepository;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private RoleRepository mockRoleRepository;

    private User testUser;
    private VerificationToken testToken;

    @BeforeEach
    void init() {
        testUser = getUser();
        testToken = getToken(testUser);
    }

    @Test
    public void clearExpired_ShouldDeleteTokenAndNotUser_WhenUserIsActive() {
        //arrange, act
        tokenDisposal.clearExpired(testToken);
        //assert
        Mockito.verify(mockTokenRepository, Mockito.times(1)).delete(testToken);
    }

    @Test
    public void clearExpired_ShouldDeleteTokenAndUser_WhenUserNotActive() {
        //arrange, act
        testUser.setEnabled(false);
        tokenDisposal.clearExpired(testToken);
        //assert
        Mockito.verify(mockTokenRepository, Mockito.times(1))
                .delete(testToken);
        Mockito.verify(mockUserRepository, Mockito.times(1))
                .delete(testUser);
        Mockito.verify(mockRoleRepository, Mockito.times(1))
                .deleteAuthority(testUser.getUserName());
    }

}
