package com.safetycar.web.wrapper;

import com.safetycar.models.VerificationToken;

import java.util.Locale;

public class RegistrationMailWrapper {

    private String appUrl;
    private Locale locale;
    private VerificationToken token;

    public RegistrationMailWrapper(String appUrl, Locale locale, VerificationToken token) {
        this.appUrl = appUrl;
        this.locale = locale;
        this.token = token;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public VerificationToken getToken() {
        return token;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void setToken(VerificationToken token) {
        this.token = token;
    }
}