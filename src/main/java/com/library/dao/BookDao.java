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
    private final SessionFactory sessionFactory;

    public BookDao(JdbcTemplate jdbcTemplate, SessionFactory sessionFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Book> index() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select b from Book b", Book.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Book show(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Book.class, id);
    }

    @Transactional
    public void save(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        Session session = sessionFactory.getCurrentSession();

        Book bookToBeUpdated = session.find(Book.class, id);

        bookToBeUpdated.setName(updatedBook.getName());
        bookToBeUpdated.setAuthor(updatedBook.getAuthor());
        bookToBeUpdated.setYear(updatedBook.getYear());
        bookToBeUpdated.setImagePath(updatedBook.getImagePath());
        bookToBeUpdated.setMemberId(updatedBook.getMemberId());
    }

    @Transactional
    public void delete(int id) {
       Session session = sessionFactory.getCurrentSession();
       session.remove(session.find(Book.class, id));
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
