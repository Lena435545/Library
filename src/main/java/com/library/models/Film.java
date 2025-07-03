package com.library.models;

public class Film {
    private int film_id;
    private int member_id;
    private String name;
    private String director;
    private int year;

    public int getFilm_id() {
        return film_id;
    }

    public void setFilm_id(int film_id) {
        this.film_id = film_id;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
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

    public Film(int film_id, int member_id, String name, String director, int year) {
        this.film_id = film_id;
        this.member_id = member_id;
        this.name = name;
        this.director = director;
        this.year = year;
    }

    public Film() {
    }
}
