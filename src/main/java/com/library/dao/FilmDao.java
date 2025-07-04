package com.library.dao;

import com.library.models.Film;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FilmDao {
    private final JdbcTemplate jdbcTemplate;

    public FilmDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Film> index() {
        return jdbcTemplate.query("SELECT * FROM Film", new BeanPropertyRowMapper<>(Film.class));
    }

    public Film show(int id) {
        return jdbcTemplate.query("SELECT * FROM Film WHERE film_id=?", new Object []{id},
                new BeanPropertyRowMapper<>(Film.class))
                .stream().findAny().orElse(null);
    }

    public void save(Film film){
        jdbcTemplate.update("INSERT INTO Film (name, director, year) VALUES (?, ?, ?)", film.getName(),
                film.getDirector(), film.getYear());
    }

    public void update(Film film, int id) {
        jdbcTemplate.update("UPDATE Film SET name=?, director=?, year=? WHERE film_id=?", film.getName(),
                film.getDirector(), film.getYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Film WHERE film_id=?", id);
    }
}
