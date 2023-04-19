package com.arman_jaurigue.models;

import com.arman_jaurigue.data_objects.data_annotations.*;

public class LoginModel {
    @Required
    @MinLength(5)
    @MaxLength(100)
    private String email;
    private String emailError;

    @Required
    @MinLength(8)
    @MaxLength(128)
    private char[] password;
    private String passwordError;

    @Ignore
    private String otherError;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailError() {
        return emailError;
    }

    public void setEmailError(String emailError) {
        this.emailError = emailError;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public String getOtherError() {
        return otherError;
    }

    public void setOtherError(String otherError) {
        this.otherError = otherError;
    }
}
