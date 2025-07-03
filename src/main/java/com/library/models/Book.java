package com.library.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Book {
    private int bookId;
    private Integer memberId;

    @NotEmpty(message = "Name should not be empty")
    @Size(min=2, max=50, message = "Name should be between 2 and 50 characters")
    private String name;

    @Size(min=2, max=30, message = "Author should be between 2 and 30 characters")
    private String author;

    @Min(value=1900, message="Year should be greater als 1900")
    private int year;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Book(int bookId, Integer memberId, String name, String author, int year) {
        this.bookId = bookId;
        this.memberId = memberId;
        this.name = name;
        this.author = author;
        this.year = year;
    }
    public Book() {
    }
}
