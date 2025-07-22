package com.library.dao;

import com.library.models.Book;
import com.library.models.Film;
import com.library.models.Journal;
import com.library.models.Member;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Component
public class MemberDao {

    private final JdbcTemplate jdbcTemplate;
    private final SessionFactory sessionFactory;

    @Autowired
    public MemberDao(JdbcTemplate jdbcTemplate, SessionFactory sessionFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Member> index() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select m from Member m", Member.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Member show(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Member.class, id);
    }

    @Transactional
    public void save(Member member) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(member);
    }

    @Transactional
    public void update(int id, Member updatedMember) {
        Session session = sessionFactory.getCurrentSession();

        Member memberToBeUpdated = session.find(Member.class, id);

        memberToBeUpdated.setName(updatedMember.getName());
        memberToBeUpdated.setSurname(updatedMember.getSurname());
        memberToBeUpdated.setEmail(updatedMember.getEmail());
    }

    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.find(Member.class, id));
    }

    public List<Book> getBooksByMemberId(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE member_id = ?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class));
    }

    public List<Film> getFilmsByMemberId(int id) {
        return jdbcTemplate.query("SELECT * FROM Film WHERE member_id = ?", new Object[]{id},
                new BeanPropertyRowMapper<>(Film.class));
    }

    public List<Journal> getJournalsByMemberId(int id) {
        return jdbcTemplate.query("SELECT * FROM Journal WHERE journal_id = ?", new Object[]{id},
                new BeanPropertyRowMapper<>(Journal.class));
    }
}
