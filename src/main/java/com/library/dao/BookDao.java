package com.library.dao;

import com.library.models.Book;
import com.library.models.Member;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDao {

    private final JdbcTemplate jdbcTemplate;

    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book (name, author, year, image_path) VALUES (?, ?, ?, ?)", book.getName(),
                book.getAuthor(), book.getYear(), book.getImagePath());
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("UPDATE Book SET name=?, author=?, year=?, image_path=? WHERE book_id=?", book.getName(),
                book.getAuthor(), book.getYear(), book.getImagePath(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE book_id=?", id);
    }

    public Optional<Member> getBookOwner(int id) {
        return jdbcTemplate.query("SELECT Member.* FROM Book JOIN Member ON Book.member_id = Member.member_id" +
                " WHERE Book.book_id = ?", new Object[] {id}, new BeanPropertyRowMapper<>(Member.class))
                .stream().findAny();
    }

    public void release(int id) {
        jdbcTemplate.update("UPDATE Book SET member_id=null WHERE book_id=?", id);
    }

    public void assign(int id, Member selectedMember) {
        jdbcTemplate.update("UPDATE Book SET member_id=? WHERE book_id=?", selectedMember.getMemberId(), id);
    }
}
