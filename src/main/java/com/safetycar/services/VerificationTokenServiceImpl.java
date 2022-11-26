package com.safetycar.services;

import com.safetycar.exceptions.EntityNotFoundException;
import com.safetycar.exceptions.ExpiredException;
import com.safetycar.models.VerificationToken;
import com.safetycar.models.users.User;
import com.safetycar.repositories.TokenRepository;
import com.safetycar.services.contracts.ExpiredTokenDisposal;
import com.safetycar.services.contracts.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

import static com.safetycar.util.Constants.ErrorsConstants.INVALID_TOKEN;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private static final String AUTH_MESSAGE_EXPIRED = "auth.message.expired";

    private final TokenRepository tokenRepository;
    private final MessageSource messageSource;
    private final ExpiredTokenDisposal expiredTokenDisposal;

    @Autowired
    public VerificationTokenServiceImpl(TokenRepository tokenRepository,
                                        MessageSource messageSource,
                                        ExpiredTokenDisposal expiredTokenDisposal) {
        this.tokenRepository = tokenRepository;
        this.messageSource = messageSource;
        this.expiredTokenDisposal = expiredTokenDisposal;
    }


    @Override
    public void createVerificationToken(VerificationToken newToken) {
        tokenRepository.save(newToken);
    }

    @Override
    public VerificationToken getNewUnsavedToken(User user) {
        VerificationToken newToken = new VerificationToken();
        newToken.setToken(UUID.randomUUID().toString());
        newToken.setUser(user);
        return newToken;
    }

    @Override
    public VerificationToken getVerificationToken(String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            throw new EntityNotFoundException(INVALID_TOKEN);
        }
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            String message = messageSource.getMessage(AUTH_MESSAGE_EXPIRED, null, Locale.ENGLISH);
            expiredTokenDisposal.clearExpired(verificationToken);
            throw new ExpiredException(message);
        }
        return verificationToken;
    }


}
