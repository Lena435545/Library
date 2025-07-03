package com.library.models;

public class Film {
    private int filmId;
   private Integer memberId;
    private String name;
    private String director;
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
