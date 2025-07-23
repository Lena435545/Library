package com.library.dao;

import com.library.models.Film;
import com.library.models.Member;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class FilmDao {
    private final JdbcTemplate jdbcTemplate;

    public FilmDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
