package com.library.models;

public class Journal {

    private int journalId;
    private Integer memberId;
    private String name;
    private String thematic;
    private int month;
    private int year;

    public Journal() {
    }

    public Journal(int journalId, Integer memberId, String name, String thematic, int month, int year) {
        this.journalId = journalId;
        this.memberId = memberId;
        this.name = name;
        this.thematic = thematic;
        this.month = month;
        this.year = year;
    }

    public int getJournalId() {
        return journalId;
    }

    public void setJournalId(int journalId) {
        this.journalId = journalId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThematic() {
        return thematic;
    }

    public void setThematic(String thematic) {
        this.thematic = thematic;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
