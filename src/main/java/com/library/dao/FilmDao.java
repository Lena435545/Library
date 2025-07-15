package com.library.dao;

import com.library.models.Film;
import com.library.models.Member;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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
        jdbcTemplate.update("INSERT INTO Film (name, director, year, image_path ) VALUES (?, ?, ?, ?)", film.getName(),
                film.getDirector(), film.getYear(), film.getImagePath());
    }

    public void update(int id, Film film) {
        jdbcTemplate.update("UPDATE Film SET name=?, director=?, year=?, image_path = ? WHERE film_id=?", film.getName(),
                film.getDirector(), film.getYear(), film.getImagePath(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Film WHERE film_id=?", id);
    }

    public Optional<Member> getFilmOwner(int id) {
        return jdbcTemplate.query("SELECT Member.* FROM Film JOIN Member ON Film.member_id = Member.member_id" +
                " WHERE Film.film_id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Member.class))
                .stream().findAny();
    }

    public void release(int id) {
        jdbcTemplate.update("UPDATE Film SET member_id=null WHERE film_id=?", id);
    }

    public void assign(int id, Member selectedMember) {
        jdbcTemplate.update("UPDATE Film SET member_id=? WHERE film_id=?", selectedMember.getMemberId(), id);
    }
}
