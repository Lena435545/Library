package com.library.dao;

import com.library.models.Journal;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JournalDao {
    private final JdbcTemplate jdbcTemplate;

    public JournalDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Journal> index(){
        return jdbcTemplate.query("SELECT * FROM Journal", new BeanPropertyRowMapper<>(Journal.class));
    }

    public Journal show(int id){
        return jdbcTemplate.query("SELECT * FROM Journal WHERE journal_id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Journal.class))
                .stream().findAny().orElse(null);
    }

    public void save(Journal journal){
        jdbcTemplate.update("INSERT INTO Journal (name, thematic, month, year) VALUES (?, ?, ?, ?)",
                journal.getName(), journal.getThematic(), journal.getMonth(), journal.getYear());
    }

    public void update(Journal journal, int id){
        jdbcTemplate.update("UPDATE Journal SET name=?, thematic=?, month=?, year=? WHERE journal_id=?",
                journal.getName(), journal.getThematic(), journal.getMonth(), journal.getYear(), id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Journal WHERE journal_id=?", id);
    }

}
