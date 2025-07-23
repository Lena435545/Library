package com.library.dao;

import com.library.models.Journal;
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
public class JournalDao {
    private final JdbcTemplate jdbcTemplate;

    public JournalDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Member> getJournalOwner(int id) {
        return jdbcTemplate.query("SELECT Member.* FROM Journal JOIN Member ON Journal.member_id = Member.member_id WHERE Journal.journal_id = ?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Member.class))
                .stream().findAny();
    }

    public void assign(int id, Member selectedMember) {
        jdbcTemplate.update("UPDATE Journal SET member_id = ? WHERE journal_id = ?", selectedMember.getMemberId(), id);
    }


    public void release(int id) {
        jdbcTemplate.update("UPDATE Journal SET member_id=null WHERE journal_id = ?", id);
    }
}
