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
    private final SessionFactory sessionFactory;

    public FilmDao(JdbcTemplate jdbcTemplate, SessionFactory sessionFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Film> index() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select f from Film f", Film.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Film show(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Film.class, id);
    }

    @Transactional
    public void save(Film film){
       Session session = sessionFactory.getCurrentSession();
       session.persist(film);
    }

    @Transactional
    public void update(int id, Film updatedFilm) {
        Session session = sessionFactory.getCurrentSession();

        Film filmToBeUpdated = session.find(Film.class, id);

        filmToBeUpdated.setName(updatedFilm.getName());
        filmToBeUpdated.setDirector(updatedFilm.getDirector());
        filmToBeUpdated.setYear(updatedFilm.getYear());
        filmToBeUpdated.setImagePath(updatedFilm.getImagePath());
        filmToBeUpdated.setMemberId(updatedFilm.getMemberId());
    }

    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.find(Film.class, id));
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
