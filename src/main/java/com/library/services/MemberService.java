package com.library.services;

import com.library.models.Book;
import com.library.models.Film;
import com.library.models.Journal;
import com.library.models.Member;
import com.library.repositories.BookRepository;
import com.library.repositories.FilmRepository;
import com.library.repositories.JournalRepository;
import com.library.repositories.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final FilmRepository filmRepository;
    private final JournalRepository journalRepository;

    public MemberService(MemberRepository memberRepository, BookRepository bookRepository, FilmRepository filmRepository, JournalRepository journalRepository) {
        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
        this.filmRepository = filmRepository;
        this.journalRepository = journalRepository;
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Member findById(int id) {
        Optional<Member> foundMember = memberRepository.findById(id);
        return foundMember.orElse(null);
    }

    @Transactional
    public void save(Member member) {
        memberRepository.save(member);
    }

    @Transactional
    public void update(int id, Member updatedMember) {
        updatedMember.setMemberId(id);
        memberRepository.save(updatedMember);
    }

    @Transactional
    public void delete(int id) {
        memberRepository.deleteById(id);
    }

    public List<Book> findBooksByOwner(Member owner) {
        return bookRepository.findByOwner(owner);
    }

    public List<Film> findFilmsByOwner(Member owner) {
        return filmRepository.findByOwner(owner);
    }

    public List<Journal> findJournalsByOwner(Member owner) {
        return journalRepository.findByOwner(owner);
    }
}
