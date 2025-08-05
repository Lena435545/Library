package com.library.repositories;

import com.library.models.Book;
import com.library.models.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByOwner(Member owner);
    List<Book> findByNameStartingWith(String title);
}
