package com.safetycar.web.controllers.mvc;

import org.springframework.ui.Model;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.safetycar.util.Constants.ValidationConstants.ERROR;
import static com.safetycar.util.Constants.Views.*;

@Controller
public class MvcLoginController {

    public static final String LOGIN_ENDPOINT = "/login";
    public static final String LOGIN_ERROR_ENDPOINT = "/login/error";

    @GetMapping(LOGIN_ENDPOINT)
    public String showLoginPage() {
        return LOGIN_VIEW;
    }

    @GetMapping(LOGIN_ERROR_ENDPOINT)
    public String showError(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            AuthenticationException e = (AuthenticationException) session
                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            String message = null;
            if (e != null) {
                message = e.getMessage();
            }
            model.addAttribute(ERROR, message);
        }
        return LOGIN_VIEW;
    }
}
