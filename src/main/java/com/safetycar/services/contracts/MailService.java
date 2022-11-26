package com.safetycar.services.contracts;

import com.safetycar.models.Policy;
import com.safetycar.web.wrapper.RegistrationMailWrapper;

public interface MailService {

    void sendConfirmationMail(RegistrationMailWrapper wrapper);

    void sendPolicyStatusMail(Policy policy);

}
