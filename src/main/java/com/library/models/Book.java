package com.library.models;

public class Book {
    private int bookId;
    private int clientId;
    private String name;
    private String author;
    private int year;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
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

    public Book(int bookId, int clientId, String name, String author, int year) {
        this.bookId = bookId;
        this.clientId = clientId;
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public Book() {
    }
}
