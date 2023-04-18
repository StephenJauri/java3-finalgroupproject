package com.armanJaurigue.dataObjects;

import com.arman_jaurigue.data_objects.enumerations.Privileges;
import com.arman_jaurigue.data_objects.enumerations.Status;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private Status status;
    private Privileges privileges;

    public User() {
        this(0,"John","Doe","john@example.com","Passw0rd".toCharArray(), Status.Inactive, Privileges.None);
    }

    public User(int id, String firstName, String lastName, String email,char[] password, Status status, Privileges privileges) {
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setStatus(status);
        setPrivileges(privileges);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        //Set the email pattern string
        Pattern p = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
        //Match the given string with the pattern
        Matcher m = p.matcher(email);
        //Check whether match is found
        if(!m.matches()) {
            throw new IllegalArgumentException("Invalid email address");
        }
        if(email.length() > 100) {
            throw new IllegalArgumentException("Email cannot have more than 100 characters");
        }
        this.email = email;
    }


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Privileges getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Privileges privileges) {
        this.privileges = privileges;
    }

}
