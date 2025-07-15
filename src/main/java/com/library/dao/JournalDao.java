package com.library.dao;

import com.library.models.Journal;
import com.library.models.Member;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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
        jdbcTemplate.update("INSERT INTO Journal (name, thematic, month, year, image_path) VALUES (?, ?, ?, ?, ?)",
                journal.getName(), journal.getThematic(), journal.getMonth(), journal.getYear(), journal.getImagePath());
    }

    public void update(int id, Journal journal){
        jdbcTemplate.update("UPDATE Journal SET name=?, thematic=?, month=?, year=?, image_path=? WHERE journal_id=?",
                journal.getName(), journal.getThematic(), journal.getMonth(), journal.getYear(), journal.getImagePath(), id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Journal WHERE journal_id=?", id);
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
