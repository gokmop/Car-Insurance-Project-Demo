package com.safetycar.services;

import com.safetycar.exceptions.DuplicateEntityException;
import com.safetycar.exceptions.TooManyOffersException;
import com.safetycar.models.Offer;
import com.safetycar.models.users.User;
import com.safetycar.security.MyCustomLoginAuthenticationSuccessHandler;
import com.safetycar.services.contracts.OfferAppendingService;
import com.safetycar.services.contracts.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static com.safetycar.SafetyCarTestObjectsFactory.getOffer;
import static com.safetycar.SafetyCarTestObjectsFactory.getUser;
import static com.safetycar.security.MySpringUserDetailsService.getAuthorities;
import static com.safetycar.util.Constants.OfferConstants.TRANSFER_OFFER;
import static com.safetycar.util.Helper.getAdminRoles;
import static com.safetycar.web.controllers.mvc.MvcOfferController.OFFER_ERROR;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class MyCustomAuthenticationSuccessHandlerTest {

    @InjectMocks
    private MyCustomLoginAuthenticationSuccessHandler successHandler;

    @Mock
    private UserService mockUserService;

    @Mock
    private OfferAppendingService mockAppendingService;
    @Mock
    private HttpServletRequest mockRequest;
    @Mock
    private HttpServletResponse mockResponse;
    @Mock
    private HttpSession mockSession;

    private Authentication testAuth;
    private User testUser;
    private Offer testOffer;

    @BeforeEach
    void init() {
        testUser = getUser();
        List<GrantedAuthority> authorities = new LinkedList<>(getAuthorities(testUser.getRoles()));
        testAuth = new UsernamePasswordAuthenticationToken(testUser, testUser.getPassword(), authorities);
        testOffer = getOffer();
    }

    @Test
    public void onAuthenticationSuccess_ShouldAppendOffer() throws IOException, ServletException {
        //arrange
        Mockito.when(mockRequest.getSession(false)).thenReturn(mockSession);
        Mockito.when(mockSession.getAttribute(TRANSFER_OFFER)).thenReturn(testOffer);
        Mockito.when(mockUserService.userByEmail(Mockito.anyString())).thenReturn(testUser);
        //act
        successHandler.onAuthenticationSuccess(mockRequest, mockResponse, testAuth);
        //assert
        Mockito.verify(mockAppendingService, Mockito.times(1)).appendOffer(testOffer, testUser);
    }

    @Test
    public void onAuthenticationSuccess_ShouldNotAppendOffer_WhenUserIsAdmin() throws IOException, ServletException {
        //arrange
        testUser.setRoles(getAdminRoles(testUser));
        Mockito.when(mockRequest.getSession(false)).thenReturn(mockSession);
        Mockito.when(mockSession.getAttribute(TRANSFER_OFFER)).thenReturn(testOffer);
        Mockito.when(mockUserService.userByEmail(Mockito.anyString())).thenReturn(testUser);
        //act
        successHandler.onAuthenticationSuccess(mockRequest, mockResponse, testAuth);
        //assert
        Mockito.verify(mockAppendingService, Mockito.times(0)).appendOffer(testOffer, testUser);
    }

    @Test
    public void onAuthenticationSuccess_ShouldNotAppendOffer_WhenSessionNull() throws IOException, ServletException {
        //arrange
        Mockito.when(mockRequest.getSession(false)).thenReturn(null);
        //act
        successHandler.onAuthenticationSuccess(mockRequest, mockResponse, testAuth);
        //assert
        Mockito.verify(mockAppendingService, Mockito.times(0)).appendOffer(testOffer, testUser);
    }

    @Test
    public void onAuthenticationSuccess_ShouldNotAppendOffer_AndShouldRemoveSessionAttribute_WhenTooManyOffers() throws IOException, ServletException {
        //arrange
        Mockito.when(mockRequest.getSession(false)).thenReturn(mockSession);
        Mockito.when(mockSession.getAttribute(TRANSFER_OFFER)).thenReturn(testOffer);
        Mockito.when(mockUserService.userByEmail(Mockito.anyString())).thenReturn(testUser);
        Mockito.doThrow(TooManyOffersException.class).when(mockAppendingService).appendOffer(testOffer, testUser);
        //act
        successHandler.onAuthenticationSuccess(mockRequest, mockResponse, testAuth);
        //assert
        Mockito.verify(mockSession, Mockito.times(1)).removeAttribute(TRANSFER_OFFER);
        String errorMessage = null;
        Mockito.verify(mockSession, Mockito.times(1)).setAttribute(OFFER_ERROR, errorMessage);
    }

    @Test
    public void onAuthenticationSuccess_ShouldNotAppendOffer_AndShouldRemoveSessionAttribute_WhenDuplicate() throws IOException, ServletException {
        //arrange
        Mockito.when(mockRequest.getSession(false)).thenReturn(mockSession);
        Mockito.when(mockSession.getAttribute(TRANSFER_OFFER)).thenReturn(testOffer);
        Mockito.when(mockUserService.userByEmail(Mockito.anyString())).thenReturn(testUser);
        Mockito.doThrow(DuplicateEntityException.class).when(mockAppendingService).appendOffer(testOffer, testUser);
        //act
        successHandler.onAuthenticationSuccess(mockRequest, mockResponse, testAuth);
        //assert
        Mockito.verify(mockSession, Mockito.times(1)).removeAttribute(TRANSFER_OFFER);
        String errorMessage = null;
        Mockito.verify(mockSession, Mockito.times(1)).setAttribute(OFFER_ERROR, errorMessage);
    }


}
