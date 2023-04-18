package com.arman_jaurigue.models;

import com.arman_jaurigue.data_objects.data_annotations.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterModel {
    @Required
    @MaxLength(32)
    @MinLength(3)
    private String firstName;
    private String firstNameError;
    @MinLength(3)
    @MaxLength(32)
    @Required
    private String lastName;
    @ErrorFor(field = "lastName")
    private String lastNameValidMessage;
    @Required
    @MinLength(3)
    @MaxLength(32)
    private String email;
    private String emailError;
    @Required
    @MinLength(5)
    @MaxLength(128)
    @Validate(errorMessage = "Password must be strong", method = "")
    private char[] password;
    private String passwordError;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstNameError() {
        return firstNameError;
    }

    public void setFirstNameError(String firstNameError) {
        this.firstNameError = firstNameError;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastNameValidMessage() {
        return lastNameValidMessage;
    }

    public void setLastNameValidMessage(String lastNameValidMessage) {
        this.lastNameValidMessage = lastNameValidMessage;
    }

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


    private static boolean passwordValidation(char[] password)
    {
        String passwordStr = String.valueOf(password);
        Pattern p = Pattern.compile("^" +
                "(?=.*[0-9])" + // a digit must occur at least once
                "(?=.*[a-z])" + // a lower case letter must occur at least once
                "(?=.*[A-Z])" + // an upper case letter must occur at least once
                // "(?=.*[@#$%^&+=])" + // a special character must occur at least once
                "(?=\\S+$)" + // no whitespace allowed in the entire string
                ".{8,}" + // anything, at least eight characters
                "$");
        Matcher m = p.matcher(passwordStr);
        System.out.println("Regex: " + m.matches());
        return m.matches();
    }
}
