package com.library.models;


public class Member {
    private int memberId;
    private String name;
    private String surname;
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
