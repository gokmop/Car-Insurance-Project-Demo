package com.safetycar.web.errors.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.safetycar.util.Constants.OfferConstants.MESSAGE;
import static com.safetycar.util.Constants.ValidationConstants.ERROR;

public interface HandlerHelper {

    String STATUS_CODE = "statusCode";
    String CODE_TITLE = "codeTitle";
    String TIME_STAMP = "timeStamp";
    String PATTERN = "dd-MM-yyyy hh:mm:ss";

    default ModelAndView buildView(Throwable e, HttpStatus status) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(ERROR);
        modelAndView.addObject(MESSAGE, e.getMessage());
        modelAndView.addObject(STATUS_CODE, status.value());
        modelAndView.addObject(CODE_TITLE, status.getReasonPhrase());
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(PATTERN);
        modelAndView.addObject(TIME_STAMP, dateTimeFormatter.format(now));
        return modelAndView;
    }

    default ResponseEntity<Object> buildResponseEntity(ApiPayload apiPayload) {
        return new ResponseEntity<>(apiPayload, apiPayload.getStatus());
    }

    default Collection<ApiSubError> getSubErrors(MethodArgumentNotValidException ex) {
        Set<ApiSubError> errors = new HashSet<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String objectName = error.getObjectName();
            String fieldName = ((FieldError) error).getField();

            String rejectedValue = null;
            Object value = ((FieldError) error).getRejectedValue();
            if (value != null) {
                rejectedValue = Objects.requireNonNull(value).toString();
            }
            String errorMessage = error.getDefaultMessage();
            errors.add(new ApiValidationError
                    (
                            objectName,
                            fieldName,
                            rejectedValue,
                            errorMessage
                    ));
        });
        return errors;
    }
}
