package com.library.services;

import com.library.models.Book;
import com.library.models.Member;
import com.library.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    private BookRepository bookRepository;
    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        bookService = new BookService(bookRepository);
    }

    @Test
    void findAllReturnsList() {
        Book book1 = new Book();
        book1.setName("Book1");
        Book book2 = new Book();
        book2.setName("Book2");
        when(bookRepository.findAll()).thenReturn(List.of(book1, book2));

        List<Book> result = bookService.findAll();

        assertEquals(2, result.size());
        assertEquals("Book1", result.get(0).getName());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void findByIdReturnsBookWhenExists() {
        Book book = new Book();
        book.setBookId(1);
        book.setName("TestBook");

        when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        Book result = bookService.findById(1);

        assertNotNull(result);
        assertEquals(1, result.getBookId());
        assertEquals("TestBook", result.getName());
        verify(bookRepository, times(1)).findById(1);
    }

    @Test
    void findByIdReturnsNullWhenBookNotFound() {
        int bookId = 999;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        Book result = bookService.findById(bookId);

        assertNull(result);
        verify(bookRepository, times(1)).findById(bookId);
    }

    @Test
    void saveBookWhenImageFileIsEmpty() {
        Book book = new Book();
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(true);

        bookService.save(book, file);

        verify(bookRepository, times(1)).save(book);
        verify(file, times(1)).isEmpty();
    }

    //TODO testSaveBookWhenImageFileIsNotEmpty - Integration-test(?)

    @Test
    void updateCopiesImagePathAndSavesBookWhenImageFileIsEmpty() {
        int bookId = 1;
        Book existingBook = new Book();
        existingBook.setImagePath("images/existing.jpg");
        Book updatedBook = new Book();

        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(true);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));

        bookService.update(bookId, updatedBook, file);

        assertEquals("images/existing.jpg", updatedBook.getImagePath());
        assertEquals(bookId, updatedBook.getBookId());
        verify(bookRepository).save(updatedBook);
    }

    @Test
    void deleteBookDeletesBookById() {
        bookService.delete(5);

        verify(bookRepository).deleteById(5);
    }

    @Test
    void getBookOwnerReturnsMemberWhenBookHasOwner() {
        Member member = new Member();
        Book book = new Book();
        book.setOwner(member);
        int bookId = 1;
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        Optional<Member> result = bookService.getBookOwner(bookId);

        assertTrue(result.isPresent());
        assertEquals(member, result.get());
    }

    @Test
    void getBookOwnerReturnsEmptyWhenBookHasNoOwner(){
        Book book = new Book();
        int bookId = 1;
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        Optional<Member> result = bookService.getBookOwner(bookId);

        assertTrue(result.isEmpty());
    }

    @Test
    void getBookOwnerReturnsEmptyWhenBookNotFound() {
        int bookId = 999;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        Optional<Member> result = bookService.getBookOwner(bookId);

        assertTrue(result.isEmpty());
    }

    @Test
    void assignSetsOwnerAndSavesBookWhenBookExists() {
        Book book = new Book();
        Member member = new Member();
        int bookId = 1;

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        bookService.assign(bookId, member);

        assertEquals(member, book.getOwner());
        verify(bookRepository).save(book);
    }

    @Test
    void testReleaseBook() {
        Member member = new Member();
        Book book = new Book();
        book.setOwner(member);
        int bookId = 1;

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        bookService.release(bookId);

        assertNull(book.getOwner());
        verify(bookRepository).save(book);
    }
}
