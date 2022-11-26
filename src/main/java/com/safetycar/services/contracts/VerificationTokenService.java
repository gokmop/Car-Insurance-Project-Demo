package com.safetycar.services.contracts;

import com.safetycar.models.VerificationToken;
import com.safetycar.models.users.User;

public interface VerificationTokenService {

    void createVerificationToken(VerificationToken token);

    VerificationToken getVerificationToken(String VerificationToken);

    VerificationToken getNewUnsavedToken(User user);


}
