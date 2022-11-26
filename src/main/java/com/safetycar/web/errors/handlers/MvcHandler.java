package com.safetycar.web.errors.handlers;

import com.safetycar.services.contracts.BrandService;
import com.safetycar.services.contracts.ModelService;
import com.safetycar.web.dto.offer.OfferDto;
import com.safetycar.exceptions.*;
import com.safetycar.web.errors.util.HandlerHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.naming.SizeLimitExceededException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.safetycar.util.Constants.ConfigConstants.CONTROLLERS_MVC;
import static com.safetycar.util.Constants.ErrorsConstants.UNEXPECTED_MESSAGE;
import static com.safetycar.util.Constants.OfferConstants.*;
import static com.safetycar.util.Constants.UserConstants.DETAILS;
import static com.safetycar.util.Constants.Views.CALCULATE_FIRST;
import static com.safetycar.util.Constants.Views.INDEX_VIEW;
import static org.springframework.http.HttpStatus.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice(basePackages = {CONTROLLERS_MVC})
public class MvcHandler implements HandlerHelper {

    protected final Log logger = LogFactory.getLog(getClass());

    private final ModelService modelService;
    private final BrandService brandService;


    @Autowired
    public MvcHandler(ModelService modelService, BrandService brandService) {
        this.modelService = modelService;
        this.brandService = brandService;
    }

    @ResponseStatus(value = CONFLICT)
    @ExceptionHandler(value = {DuplicateEntityException.class})
    public ModelAndView handleDuplicateEntity(
            DuplicateEntityException e) {
        return buildView(e, CONFLICT);
    }

    @ResponseStatus(value = BAD_REQUEST)
    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ModelAndView handleDuplicateEntity(
            MethodArgumentTypeMismatchException e) {
        return buildView(e.getCause(), BAD_REQUEST);
    }

    @ResponseStatus(value = PAYLOAD_TOO_LARGE)
    @ExceptionHandler(value = {SizeLimitExceededException.class})
    public ModelAndView handleFileStorage(
            SizeLimitExceededException e) {
        return buildView(e, PAYLOAD_TOO_LARGE);
    }

    @ResponseStatus(value = CONFLICT)
    @ExceptionHandler(value = {TooManyOffersException.class})
    public ModelAndView handleTooManyOffers(
            TooManyOffersException e) {
        return buildView(e, CONFLICT);
    }

    @ResponseStatus(value = FORBIDDEN)
    @ExceptionHandler(value = {AccessDeniedException.class})
    public ModelAndView handleAccessDenied(
            AccessDeniedException e) {
        return buildView(e, FORBIDDEN);
    }

    @ResponseStatus(value = OK)
    @ExceptionHandler(value = {ServletRequestBindingException.class})
    public ModelAndView handleSessionAttributeMissing(
            ServletRequestBindingException e, HttpServletRequest request) {
        logger.warn(e);

        HttpSession session = request.getSession();
        session.removeAttribute(DETAILS);
        session.removeAttribute(TRANSFER_OFFER);

        ModelAndView mav = new ModelAndView();
        mav.setViewName(INDEX_VIEW);
        mav.addObject(MESSAGE, CALCULATE_FIRST);
        mav.addObject(MODELS, modelService.getAll());
        mav.addObject(BRANDS, brandService.getAll());
        mav.addObject(OFFER_DTO, new OfferDto());
        return mav;
    }

    @ResponseStatus(value = UNAUTHORIZED)
    @ExceptionHandler(value = {ExpiredException.class})
    public ModelAndView handleExpired(
            ExpiredException e) {
        return buildView(e, UNAUTHORIZED);
    }

    @ResponseStatus(value = NOT_FOUND)
    @ExceptionHandler(value = {NoHandlerFoundException.class})
    public ModelAndView handleEntityNotFound(
            NoHandlerFoundException e) {
        return buildView(e, NOT_FOUND);
    }

    @ResponseStatus(value = NOT_FOUND)
    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ModelAndView handleEntityNotFound(
            EntityNotFoundException e) {
        return buildView(e, NOT_FOUND);

    }

    @ResponseStatus(value = FORBIDDEN)
    @ExceptionHandler(value = {AccountNotActivatedException.class})
    public ModelAndView handleAccountNotActivated(
            AccountNotActivatedException e) {
        return buildView(e, FORBIDDEN);
    }

    @ResponseStatus(value = INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {Exception.class})
    public ModelAndView handleUnhandled(Exception e) {
        logger.warn(e.getMessage(), e);
        RuntimeException changeMsg = new RuntimeException(UNEXPECTED_MESSAGE);
        return buildView(changeMsg, INTERNAL_SERVER_ERROR);
    }
}
