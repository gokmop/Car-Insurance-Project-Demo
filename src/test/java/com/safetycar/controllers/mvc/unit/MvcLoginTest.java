package com.safetycar.controllers.mvc.unit;

import com.safetycar.web.controllers.mvc.MvcLoginController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.safetycar.security.CustomAuthenticationProvider.INVALID_USERNAME_OR_PASSWORD;
import static com.safetycar.util.Constants.ValidationConstants.ERROR;
import static com.safetycar.util.Constants.Views.LOGIN_VIEW;

@ExtendWith(MockitoExtension.class)
public class MvcLoginTest {

    @InjectMocks
    private MvcLoginController controller;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @Test
    public void showLoginPage_ShouldLoadPage() throws Exception {
        //arrange
        //act
        String actual = controller.showLoginPage();
        //assert
        Assertions.assertEquals(LOGIN_VIEW, actual);
    }

    @Test
    public void showErrorPage_ShouldLoadPageWithError() {
        //arrange
        AuthenticationException e = new BadCredentialsException(INVALID_USERNAME_OR_PASSWORD);
        Mockito.when(request.getSession(false)).thenReturn(session);
        Mockito.when(session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION)).thenReturn(e);
        //act
        String actual = controller.showError(model, request);
        //assert
        Assertions.assertEquals(LOGIN_VIEW, actual);
        Mockito.verify(model,Mockito.times(1)).addAttribute(ERROR, e.getMessage());
    }

}
