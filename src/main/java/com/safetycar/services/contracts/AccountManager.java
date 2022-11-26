package com.safetycar.services.contracts;

import com.safetycar.models.Offer;
import com.safetycar.models.VerificationToken;
import com.safetycar.web.dto.user.CreateDetailsDto;
import com.safetycar.web.dto.user.CreateUserDto;

import javax.servlet.http.HttpServletRequest;

public interface AccountManager {

    void registerUser(CreateUserDto newUser, Offer offer, HttpServletRequest request);

    void updateDetails(CreateDetailsDto dto, String userName);

    void activateAcc(VerificationToken verificationToken);

    VerificationToken getVerificationToken(String token);

    CreateDetailsDto getDetailsToEdit(String userName);

}
