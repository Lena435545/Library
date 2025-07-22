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
    private final SessionFactory sessionFactory;

    public JournalDao(JdbcTemplate jdbcTemplate, SessionFactory sessionFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Journal> index(){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select j from Journal j", Journal.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Journal show(int id){
        Session session = sessionFactory.getCurrentSession();
        return session.find(Journal.class, id);
    }

    @Transactional
    public void save(Journal journal){
        Session session = sessionFactory.getCurrentSession();
        session.persist(journal);
    }

    @Transactional
    public void update(int id, Journal updatedJournal){
        Session session = sessionFactory.getCurrentSession();

        Journal journalToBeUpdated = session.find(Journal.class, id);

        journalToBeUpdated.setName(updatedJournal.getName());
        journalToBeUpdated.setThematic(updatedJournal.getThematic());
        journalToBeUpdated.setYear(updatedJournal.getYear());
        journalToBeUpdated.setMonth(updatedJournal.getMonth());
        journalToBeUpdated.setImagePath(updatedJournal.getImagePath());
        journalToBeUpdated.setMemberId(updatedJournal.getMemberId());
    }

    @Transactional
    public void delete(int id){
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.find(Journal.class, id));
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
