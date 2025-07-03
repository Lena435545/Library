package com.library.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Film {
    private int filmId;
    private Integer memberId;

    @NotEmpty(message = "Name should not be empty")
    @Size(min=2, max=50, message = "Name should be between 2 and 50 characters")
    private String name;

    @Size(min=2, max=30, message = "Director should be between 2 and 50 characters")
    private String director;

    @Min(value=1900, message = "Year should be greater als 1900")
    private int year;

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
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

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Film(int filmId, Integer memberId, String name, String director, int year) {
        this.filmId = filmId;
        this.memberId = memberId;
        this.name = name;
        this.director = director;
        this.year = year;
    }

    public Film() {
    }
}
