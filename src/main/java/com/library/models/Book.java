package com.library.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    private Member owner;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters")
    @Column(name = "name")
    private String name;

    @Size(max = 50, message = "Author should be less than 50 characters")
    @Column(name = "author")
    private String author;

    @Column(name = "year")
    private int year;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name="taken_at")
    private LocalDateTime takenAt;

    @Transient
    private boolean expired;

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public LocalDateTime getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(LocalDateTime takenAt) {
        this.takenAt = takenAt;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Member getOwner() {
        return owner;
    }

    public void setOwner(Member owner) {
        this.owner = owner;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Book(int bookId, Member owner, String name, String author, int year, String imagePath, LocalDateTime takenAt, boolean expired) {
        this.bookId = bookId;
        this.owner = owner;
        this.name = name;
        this.author = author;
        this.year = year;
        this.imagePath = imagePath;
        this.takenAt = takenAt;
        this.expired = expired;
    }

    public Book() {
    }
}
