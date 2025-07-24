package com.library.services;

import com.library.models.Book;
import com.library.models.Member;
import com.library.repositories.BookRepository;
import com.library.utils.ImageUploadUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    @Value("${upload.dir}")
    private String uploadDir;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(int id) {
        Optional<Book> foundBook = bookRepository.findById(id);
        return foundBook.orElse(null);
    }

    @Transactional
    public void save(Book book, MultipartFile file) {
        if (!file.isEmpty()) {
            saveImageWithUniqueName(book, file);
        }
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook, MultipartFile file) {
        if (!file.isEmpty()) {
            saveImageWithUniqueName(updatedBook, file);
        } else {
            Optional<Book> existingBook = bookRepository.findById(id);
            existingBook.ifPresent(book -> updatedBook.setImagePath(book.getImagePath()));
        }
        updatedBook.setBookId(id);
        bookRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public Optional<Member> getBookOwner(int id) {
        return bookRepository.findById(id).map(Book::getOwner);
    }

    @Transactional
    public void release(int id) {
        bookRepository.findById(id).ifPresent(book -> {
            book.setOwner(null);
            bookRepository.save(book);
        });
    }

    @Transactional
    public void assign(int id, Member selectedMember) {
        bookRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(selectedMember);
                    bookRepository.save(book);
                }
        );
    }

    private void saveImageWithUniqueName(Book book, MultipartFile file) {
        try {
            String fileName = ImageUploadUtil.saveImageWithUniqueName(file, uploadDir);
            book.setImagePath("images/" + fileName);
        } catch (IOException e) {
            System.err.println("Error during image upload: " + e.getMessage());
        }
    }
}
