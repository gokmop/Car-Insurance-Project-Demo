package com.safetycar.web.errors.handlers;

import com.safetycar.exceptions.*;
import com.safetycar.web.errors.util.ApiPayload;
import com.safetycar.web.errors.util.HandlerHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.safetycar.util.Constants.ConfigConstants.CONTROLLERS_REST;
import static com.safetycar.util.Constants.ErrorsConstants.UNEXPECTED_MESSAGE;
import static org.springframework.http.HttpStatus.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(basePackages = {CONTROLLERS_REST})
public class RestHandler extends ResponseEntityExceptionHandler implements HandlerHelper {

    public static final String MALFORMED_JSON_REQUEST = "Malformed JSON request";

    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid
            (
                    MethodArgumentNotValidException e,
                    HttpHeaders headers,
                    HttpStatus status,
                    WebRequest request
            ) {
        if (logger.isWarnEnabled()) logger.warn(e.getMessage(), e);
        ApiPayload apiPayload = new ApiPayload(BAD_REQUEST, getSubErrors(e));
        return buildResponseEntity(apiPayload);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable
            (
                    HttpMessageNotReadableException e,
                    HttpHeaders headers,
                    HttpStatus status,
                    WebRequest request
            ) {
        if (logger.isWarnEnabled()) logger.warn(e.getMessage(), e);
        ApiPayload apiPayload = new ApiPayload(status, MALFORMED_JSON_REQUEST, e);
        return buildResponseEntity(apiPayload);
    }

    @ExceptionHandler(value = {DuplicateEntityException.class})
    public ResponseEntity<Object> handleDuplicateEntity(
            DuplicateEntityException e) {
        ApiPayload apiPayload = new ApiPayload(CONFLICT, e.getMessage());
        return buildResponseEntity(apiPayload);
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDenied(
            AccessDeniedException e) {
        ApiPayload apiPayload = new ApiPayload(FORBIDDEN, e.getMessage());
        return buildResponseEntity(apiPayload);
    }

    @ExceptionHandler(value = {FileStorageException.class})
    public ResponseEntity<Object> handleFileStorage(
            FileStorageException e) {
        ApiPayload apiPayload = new ApiPayload(UNSUPPORTED_MEDIA_TYPE, e.getMessage());
        return buildResponseEntity(apiPayload);
    }

    @ExceptionHandler(value = {TooManyOffersException.class})
    public ResponseEntity<Object> handleTooManyOffers(
            TooManyOffersException e) {
        ApiPayload apiPayload = new ApiPayload(CONFLICT, e.getMessage());
        return buildResponseEntity(apiPayload);
    }

    @ExceptionHandler(value = {ExpiredException.class})
    public ResponseEntity<Object> handleExpired(
            ExpiredException e) {
        ApiPayload apiPayload = new ApiPayload(CONFLICT, e.getMessage());
        return buildResponseEntity(apiPayload);
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFound(
            EntityNotFoundException e) {
        ApiPayload apiPayload = new ApiPayload(NOT_FOUND, e.getMessage());
        return buildResponseEntity(apiPayload);
    }

    @ExceptionHandler(value = {AccountNotActivatedException.class})
    public ResponseEntity<Object> handleAccountNotActivated(
            AccountNotActivatedException e) {
        ApiPayload apiPayload = new ApiPayload(FORBIDDEN, e.getMessage());
        return buildResponseEntity(apiPayload);
    }
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleException(Exception e) {

        if (logger.isWarnEnabled()) logger.warn(e.getMessage(), e);

        ApiPayload apiPayload = new ApiPayload(INTERNAL_SERVER_ERROR, UNEXPECTED_MESSAGE);
        return buildResponseEntity(apiPayload);
    }
}
