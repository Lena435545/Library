package com.library.dao;

import com.library.models.Book;
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
public class BookDao {

    private final JdbcTemplate jdbcTemplate;

    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Member> getBookOwner(int id) {
        return jdbcTemplate.query("SELECT Member.* FROM Book JOIN Member ON Book.member_id = Member.member_id" +
                        " WHERE Book.book_id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Member.class))
                .stream().findAny();
    }

    public void release(int id) {
        jdbcTemplate.update("UPDATE Book SET member_id=null WHERE book_id=?", id);
    }

    public void assign(int id, Member selectedMember) {
        jdbcTemplate.update("UPDATE Book SET member_id=? WHERE book_id=?", selectedMember.getMemberId(), id);
    }
}
