package com.mapa.model;

import java.time.LocalDateTime;

public class User {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private LocalDateTime birthDay;
    private int id;

    public User(int id, String firstName, String lastName, String emailAddress, LocalDateTime birthDay) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.birthDay = birthDay;
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public LocalDateTime getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDateTime birthDay) {
        this.birthDay = birthDay;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return id + ", " + firstName + ", " + lastName + ", " + emailAddress + ", " + birthDay;
    }
}
