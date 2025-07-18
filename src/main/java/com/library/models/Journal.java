package com.library.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Journal {

    private int journalId;
    private Integer memberId;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters")
    private String name;

    @Size(max = 50, message = "Thematic should be less than 50 characters")
    private String thematic;

    @Min(value = 1, message = "Month should be greater than 1")
    @Max(value = 12, message = "Month should be less than 12")
    private int month;

    private Integer year;
    private String imagePath;

    public Journal() {
    }

    public Journal(int journalId, Integer memberId, String name, String thematic, int month, int year, String imagePath) {
        this.journalId = journalId;
        this.memberId = memberId;
        this.name = name;
        this.thematic = thematic;
        this.month = month;
        this.year = year;
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
