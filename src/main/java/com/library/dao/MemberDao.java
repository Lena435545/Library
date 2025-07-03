package com.library.dao;

import com.library.models.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Component
public class MemberDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Member> index() {
        return jdbcTemplate.query("SELECT * FROM Member", new BeanPropertyRowMapper<>(Member.class));
    }


    public Member show(int id) {
        return jdbcTemplate.query("SELECT * FROM Member WHERE member_id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Member.class))
                .stream().findAny().orElse(null);
    }

    public void save(Member member) {
        jdbcTemplate.update("INSERT INTO Member (name, surname, email) VALUES (?, ?, ?)", member.getName(),
                member.getSurname(), member.getEmail());
    }

    public void update(int id, Member member) {
        jdbcTemplate.update("UPDATE Member SET name=?, surname=?, email=? WHERE member_id=?", member.getName(),
                member.getSurname(), member.getEmail(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Member WHERE member_id=?", id);
    }
}
