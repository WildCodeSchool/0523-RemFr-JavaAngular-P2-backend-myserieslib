package com.templateproject.api.entity;
public class UserMail {

    private String emailAddress;

    public UserMail(String mail) {
        this.emailAddress = mail;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
