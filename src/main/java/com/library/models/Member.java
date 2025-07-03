package com.library.models;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Member {
    private int memberId;

    @NotEmpty(message = "Name should not be empty")
    @Size(min=2, max=30, message = "Name should be between 2 and 30 characters")
    private String name;

    @NotEmpty(message = "Surname should not be empty")
    @Size(min=2, max=30, message = "Name should be between 2 and 30 characters")
    private String surname;

    @Email(message = "E-mail should be valid")
    private String email;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public Member(int clientId, String email, String surname, String name) {
        this.memberId = clientId;
        this.email = email;
        this.surname = surname;
        this.name = name;
    }

    public Member() {

    }
}
