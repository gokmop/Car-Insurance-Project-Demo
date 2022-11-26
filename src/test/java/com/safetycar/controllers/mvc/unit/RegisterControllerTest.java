package com.safetycar.controllers.mvc.unit;

import com.safetycar.exceptions.DuplicateEntityException;
import com.safetycar.models.Offer;
import com.safetycar.models.VerificationToken;
import com.safetycar.models.users.User;
import com.safetycar.services.contracts.AccountManager;
import com.safetycar.web.controllers.mvc.MvcRegisterController;
import com.safetycar.web.dto.user.CreateUserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.safetycar.SafetyCarTestObjectsFactory.*;
import static com.safetycar.util.Constants.OfferConstants.TRANSFER_OFFER;
import static com.safetycar.util.Constants.UserConstants.CREATE_USER_DTO;
import static com.safetycar.util.Constants.ValidationConstants.BINDING_RESULT_ERRORS;
import static com.safetycar.util.Constants.ValidationConstants.ERROR_;
import static com.safetycar.util.Constants.Views.*;
import static com.safetycar.util.Constants.Views.QueryConstants.EMAIL;
import static com.safetycar.web.controllers.mvc.MvcOfferController.REGISTER_FIRST;

@ExtendWith(MockitoExtension.class)
public class RegisterControllerTest {

    @InjectMocks
    private MvcRegisterController registerController;

    @Mock
    private AccountManager accountManager;
    @Mock
    private Model model;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private BindingResult bindingResult;

    private VerificationToken token;
    private User user;
    private Offer offer;
    private CreateUserDto dto;

    @BeforeEach
    void init() {
        user = getUser();
        token = getToken(user);
        offer = getOffer();
    }

    @Test
    public void getRegisterPage_ShouldPerformTasks() {
        //arrange
        Mockito.when(request.getSession()).thenReturn(session);
        String message = Mockito.anyString();
        Mockito.when(session.getAttribute(REGISTER_FIRST)).thenReturn(message);
        //act
        String actual = registerController.getRegisterPage(model, dto, request);
        //assert
        Assertions.assertEquals(REGISTER_VIEW, actual);
        Mockito.verify(model, Mockito.times(1))
                .addAttribute(REGISTER_FIRST, message);
        Mockito.verify(model, Mockito.times(1))
                .addAttribute(CREATE_USER_DTO, dto);
    }

    @Test
    public void handleRegisterUser_ShouldRegisterUser() {
        //arrange
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute(TRANSFER_OFFER)).thenReturn(offer);
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);

        //act
        String actual = registerController.handleRegisterUser(dto, bindingResult, request, model);
        //assert
        Assertions.assertEquals(EMAIL_CONFIRMATION_VIEW, actual);
        Mockito.verify(accountManager, Mockito.times(1))
                .registerUser(dto, offer, request);
        Mockito.verify(session, Mockito.times(1))
                .removeAttribute(REGISTER_FIRST);
    }

    @Test
    public void handleRegisterUser_ShouldReturnView_WhenBindingResultHasErrors() {
        //arrange
        Mockito.when(request.getSession()).thenReturn(session);
        String message = Mockito.anyString();
        Mockito.when(session.getAttribute(REGISTER_FIRST)).thenReturn(message);

        Mockito.when(bindingResult.hasErrors()).thenReturn(true);
        //act
        String actual = registerController.handleRegisterUser(dto, bindingResult, request, model);
        //assert
        Assertions.assertEquals(REGISTER_VIEW, actual);
        Mockito.verify(model, Mockito.times(1))
                .addAttribute(REGISTER_FIRST, message);
        Mockito.verify(model, Mockito.times(1))
                .addAttribute(CREATE_USER_DTO, dto);
    }

    @Test
    public void handleRegisterUser_ShouldReturnView_WhenDuplicateUser() {
        //arrange
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute(TRANSFER_OFFER)).thenReturn(offer);
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);
        DuplicateEntityException e = new DuplicateEntityException("duplicate");
        Mockito.doThrow(e).when(accountManager)
                .registerUser(dto, offer, request);
        //act
        String actual = registerController
                .handleRegisterUser(dto, bindingResult, request, model);
        //assert
        Assertions.assertEquals(REGISTER_VIEW, actual);
        Mockito.verify(bindingResult, Mockito.times(1))
                .rejectValue(EMAIL, ERROR_ +
                        CREATE_USER_DTO, e.getMessage());
        Mockito.verify(model, Mockito.times(1))
                .addAttribute(CREATE_USER_DTO, dto);
        Mockito.verify(model, Mockito.times(1))
                .addAttribute(BINDING_RESULT_ERRORS, e.getMessage());
    }

    @Test
    public void confirmRegistration_ShouldPerformTasks() {
        //arrange
        String tokenString = token.getToken();
        Mockito.when(accountManager.getVerificationToken(tokenString))
                .thenReturn(token);
        //act
        String actual = registerController.confirmRegistration(tokenString);
        //assert
        Assertions.assertEquals(REGISTER_CONFIRMATION_VIEW, actual);
        Mockito.verify(accountManager,Mockito.times(1))
                .activateAcc(token);

    }


}
