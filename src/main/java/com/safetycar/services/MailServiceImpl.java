package com.safetycar.services;

import com.safetycar.models.Policy;
import com.safetycar.models.VerificationToken;
import com.safetycar.models.users.User;
import com.safetycar.services.contracts.MailService;
import com.safetycar.web.wrapper.RegistrationMailWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static com.safetycar.util.Constants.ConfigConstants.APPLICATION_PROPERTIES;
import static com.safetycar.util.Constants.ConfigConstants.MESSAGE_SOURCE;

@Service
@PropertySource(APPLICATION_PROPERTIES)
public class MailServiceImpl implements MailService {

    public static final String REGISTRATION_CONFIRMATION = "Registration Confirmation";
    public static final String RETURN_URL = "/register/confirm?token=";
    public static final String APP_URL = "http://localhost:9090";
    public static final String MESSAGE_REG_SUCC = "message.regSucc";
    public static final String POLICY_STATUS_CHANGE = "Policy status change";
    public static final String YOUR_POLICY_S_STATUS_HAS_BEEN_CHANGED_TO = "Your policy's status has been changed to ";

    private final MessageSource messages;
    private final JavaMailSender javaMailSender;

    @Autowired
    public MailServiceImpl(@Qualifier(MESSAGE_SOURCE) MessageSource messages,
                           JavaMailSender javaMailSender) {
        this.messages = messages;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendConfirmationMail(RegistrationMailWrapper wrapper) {
        VerificationToken verificationToken = wrapper.getToken();
        User user = verificationToken.getUser();
        String token = verificationToken.getToken();

        String recipientAddress = user.getUserName();
        String subject = REGISTRATION_CONFIRMATION;
        String confirmationUrl
                = wrapper.getAppUrl() + RETURN_URL + token;
        String message = messages.getMessage(MESSAGE_REG_SUCC, null, wrapper.getLocale());

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + "\r\n" + confirmationUrl);
        javaMailSender.send(email);
    }

    @Override
    public void sendPolicyStatusMail(Policy policy) {
        User owner = policy.getOwner();
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(owner.getUserName());
        String subject = POLICY_STATUS_CHANGE;
        email.setSubject(subject);
        String message = YOUR_POLICY_S_STATUS_HAS_BEEN_CHANGED_TO + policy.getStatus().getStatus();
        email.setText(message);
        javaMailSender.send(email);
    }
}
