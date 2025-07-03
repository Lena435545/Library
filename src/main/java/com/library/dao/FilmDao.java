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
}
