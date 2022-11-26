package com.safetycar.services;

import com.safetycar.models.Offer;
import com.safetycar.models.Policy;
import com.safetycar.models.VerificationToken;
import com.safetycar.models.users.User;
import com.safetycar.web.wrapper.RegistrationMailWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Locale;

import static com.safetycar.SafetyCarTestObjectsFactory.*;
import static com.safetycar.services.MailServiceImpl.*;

@ExtendWith(MockitoExtension.class)
public class MailSendingServiceTest {

    @InjectMocks
    private MailServiceImpl mailService;

    @Mock
    private MessageSource messageSource;

    @Mock
    private JavaMailSender mailSender;

    @Test
    public void confirmRegistrationMail_ShouldSendMail() {
        //arrange
        User user = getUser();
        VerificationToken token = getToken(user);
        Locale locale = Locale.ENGLISH;
        String appUrl = "";
        String text = "appUrl";
        Mockito.when(messageSource.getMessage(Mockito.anyString(), Mockito.any(), Mockito.any()))
                .thenReturn(text);

        RegistrationMailWrapper wrapper =
                new RegistrationMailWrapper(appUrl, locale, token);

        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(REGISTRATION_CONFIRMATION);
        email.setTo(user.getUserName());
        String confirmationUrl
                = wrapper.getAppUrl() + RETURN_URL + token.getToken();
        email.setText(text + "\r\n" + confirmationUrl);

        //act
        mailService.sendConfirmationMail(wrapper);
        // assert
        Mockito.verify(mailSender, Mockito.times(1)).send(email);
    }

    @Test
    public void changeStatusMail_ShouldSendMail() {
        //arrange
        SimpleMailMessage email = new SimpleMailMessage();
        //act
        Offer offer = getOffer();
        User user = getUser();
        Policy policy = getPolicy(user, offer);
        mailService.sendPolicyStatusMail(policy);
        email.setTo(user.getUserName());
        String subject = POLICY_STATUS_CHANGE;
        email.setSubject(subject);
        String message = YOUR_POLICY_S_STATUS_HAS_BEEN_CHANGED_TO + policy.getStatus().getStatus();
        email.setText(message);
        //assert
        Mockito.verify(mailSender, Mockito.times(1)).send(email);
    }


}
