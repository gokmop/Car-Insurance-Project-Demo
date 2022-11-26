package com.safetycar.services.contracts;

import com.safetycar.models.VerificationToken;

public interface ExpiredTokenDisposal {

    void clearExpired(VerificationToken verificationToken);

}
