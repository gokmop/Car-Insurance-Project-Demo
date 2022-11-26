package com.safetycar.services;

import com.safetycar.models.Offer;
import com.safetycar.models.VerificationToken;
import com.safetycar.models.users.User;
import com.safetycar.models.users.UserDetails;
import com.safetycar.services.contracts.*;
import com.safetycar.web.dto.user.CreateDetailsDto;
import com.safetycar.web.dto.user.CreateUserDto;
import com.safetycar.web.dto.mappers.UserMapper;
import com.safetycar.web.wrapper.RegistrationMailWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.safetycar.util.Constants.OfferConstants.TRANSFER_OFFER;


@Service
public class AccountManagerImpl implements AccountManager {

    private final UserService userService;
    private final UserMapper userMapper;
    private final MailService mailService;
    private final OfferAppendingService offerAppendingService;
    private final VerificationTokenService tokenService;

    private static final String HEROKU_URL = "https://safety-car-v1.herokuapp.com";

    @Autowired
    public AccountManagerImpl(UserService userService,
                              UserMapper userMapper,
                              MailService mailService,
                              OfferAppendingService offerAppendingService,
                              VerificationTokenService tokenService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.mailService = mailService;
        this.offerAppendingService = offerAppendingService;
        this.tokenService = tokenService;
    }

    @Override
    @Transactional
    public void registerUser(CreateUserDto user, Offer offer, HttpServletRequest request) {
        User inactiveUser = userMapper.fromDto(user);
        addOfferIfPresent(offer, request, inactiveUser);
        userService.create(inactiveUser);
        sendActivationMail(request, inactiveUser);
    }

    @Override
    public CreateDetailsDto getDetailsToEdit(String userName) {
        User user = userService.userByEmail(userName);
        return userMapper.toDto(user);
    }

    @Override
    public void updateDetails(CreateDetailsDto dto, String userName) {
        User user = userService.userByEmail(userName);
        UserDetails newDetails = user.getUserDetails();
        newDetails = userMapper.fromDto(dto, newDetails);
        user.setUserDetails(newDetails);
        userService.update(user);
    }

    public VerificationToken getVerificationToken(String token) {
        return tokenService.getVerificationToken(token);
    }

    public void activateAcc(VerificationToken verificationToken) {
        User user = verificationToken.getUser();
        user.setEnabled(true);
        userService.update(user);
    }

    private void addOfferIfPresent(Offer offer, HttpServletRequest request, User inactiveUser) {
        HttpSession session = request.getSession();
        session.removeAttribute(TRANSFER_OFFER);
        offerAppendingService.appendOffer(offer, inactiveUser);
    }

    private void sendActivationMail(HttpServletRequest request, User safetyCarUser) {
        String appUrl = HEROKU_URL;

        VerificationToken newToken = tokenService.getNewUnsavedToken(safetyCarUser);
        tokenService.createVerificationToken(newToken);

        RegistrationMailWrapper wrapper =
                new RegistrationMailWrapper(appUrl, request.getLocale(), newToken);
        mailService.sendConfirmationMail(wrapper);
    }

    private static String getMainUrl(HttpServletRequest request) {
        //TODO
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        return scheme + "." + serverName + "/";
    }
}
