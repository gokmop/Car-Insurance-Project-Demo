package com.safetycar.web.controllers.mvc;


import com.safetycar.models.Offer;
import com.safetycar.models.VerificationToken;
import com.safetycar.services.contracts.AccountManager;
import com.safetycar.web.dto.user.CreateUserDto;
import com.safetycar.exceptions.DuplicateEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.safetycar.util.Constants.OfferConstants.*;
import static com.safetycar.util.Constants.UserConstants.CREATE_USER_DTO;
import static com.safetycar.util.Constants.ValidationConstants.BINDING_RESULT_ERRORS;
import static com.safetycar.util.Constants.ValidationConstants.ERROR_;
import static com.safetycar.util.Constants.Views.*;
import static com.safetycar.util.Constants.Views.QueryConstants.EMAIL;
import static com.safetycar.web.controllers.mvc.MvcOfferController.REGISTER_FIRST;

@Controller
@RequestMapping
public class MvcRegisterController {

    public static final String REGISTER_ENDPOINT = "/register";
    public static final String REGISTER_CONFIRM_ENDPOINT = "/register/confirm";
    public static final String TOKEN = "token";

    private final AccountManager accountManager;

    @Autowired
    public MvcRegisterController(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    @GetMapping(REGISTER_ENDPOINT)
    public String getRegisterPage(Model model,
                                  CreateUserDto dto,
                                  HttpServletRequest request) {
        newRegistration(model, dto, request);
        return REGISTER_VIEW;
    }

    @PostMapping(REGISTER_ENDPOINT)
    public String handleRegisterUser(
            @Valid @ModelAttribute(CREATE_USER_DTO) CreateUserDto user,
            BindingResult bindingResult,
            HttpServletRequest request,
            Model model
    ) {

        if (bindingResult.hasErrors()) {
            newRegistration(model, user, request);
            return REGISTER_VIEW;
        }
        Offer offer = getOffer(request);
        try {
            accountManager.registerUser(user, offer, request);
            request.getSession().removeAttribute(REGISTER_FIRST);
            return EMAIL_CONFIRMATION_VIEW;
        } catch (DuplicateEntityException e) {
            bindingResult.rejectValue(EMAIL, ERROR_ +
                    CREATE_USER_DTO, e.getMessage());

            model.addAttribute(CREATE_USER_DTO, user);
            model.addAttribute(BINDING_RESULT_ERRORS, e.getMessage());
            return REGISTER_VIEW;
        }
    }

    @GetMapping(REGISTER_CONFIRM_ENDPOINT)
    public String confirmRegistration(@RequestParam(TOKEN) String token) {
        VerificationToken verificationToken = accountManager.getVerificationToken(token);
        accountManager.activateAcc(verificationToken);
        return REGISTER_CONFIRMATION_VIEW;
    }

    private void newRegistration(Model model,
                                 CreateUserDto dto,
                                 HttpServletRequest request) {
        String msg = getRegisterFirstMessage(request);
        model.addAttribute(REGISTER_FIRST, msg);
        model.addAttribute(CREATE_USER_DTO, dto);
    }

    @Nullable
    private Offer getOffer(HttpServletRequest request) {
        return (Offer) request.getSession().getAttribute(TRANSFER_OFFER);
    }

    @Nullable
    private String getRegisterFirstMessage(HttpServletRequest request) {
        return (String) request.getSession().getAttribute(REGISTER_FIRST);
    }
}
