package com.safetycar.security;

import com.safetycar.exceptions.DuplicateEntityException;
import com.safetycar.exceptions.TooManyOffersException;
import com.safetycar.models.Offer;
import com.safetycar.models.users.User;
import com.safetycar.services.contracts.OfferAppendingService;
import com.safetycar.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static com.safetycar.util.Constants.OfferConstants.TRANSFER_OFFER;
import static com.safetycar.web.controllers.mvc.MvcOfferController.OFFER_ERROR;

@Component
public class MyCustomLoginAuthenticationSuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserService userService;
    @Autowired
    private OfferAppendingService appendingService;

    @Override
    public void onAuthenticationSuccess(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final Authentication authentication)
            throws IOException, ServletException {

        final HttpSession session = request.getSession(false);
        if (session != null) {
            Offer offer = (Offer) session.getAttribute(TRANSFER_OFFER);
            String username = authentication.getName();
            try {
                User user = userService.userByEmail(username);
                if (!user.isAdmin()) {
                    appendingService.appendOffer(offer, user);
                }
            } catch (TooManyOffersException | DuplicateEntityException e) {
                session.setAttribute(OFFER_ERROR, e.getMessage());
                session.removeAttribute(TRANSFER_OFFER);
            }
        }
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

}