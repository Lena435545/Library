package com.library.services;

import com.library.models.Book;
import com.library.models.Member;
import com.library.repositories.BookRepository;
import com.library.utils.ImageUploadUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
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
    void testFindAllReturnsList() {
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
    void testFindByIdReturnsBookWhenExists() {
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
    void findByIdReturnsNullWhenBookNotFound(){
        int bookId= 999;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        Book result = bookService.findById(bookId);

        assertNull(result);
        verify(bookRepository, times(1)).findById(bookId);
    }

    @Test
    void testSaveBookWhenImageFileIsEmpty() {
        Book book = new Book();
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(true);

        bookService.save(book, file);

        verify(bookRepository, times(1)).save(book);
        verify(file, times(1)).isEmpty();
    }


    @Test
    void testDeleteBook() {
        bookService.delete(5);
        verify(bookRepository, times(1)).deleteById(5);
    }

    @Test
    void testAssignBookToMember() {
        Book book = new Book();
        Member member = new Member();

        when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        bookService.assign(1, member);

        assertEquals(member, book.getOwner());
        verify(bookRepository).save(book);
    }
//
    @Test
    void testReleaseBook() {
        Member member = new Member();
        Book book = new Book();
        book.setOwner(member);

        when(bookRepository.findById(2)).thenReturn(Optional.of(book));

        bookService.release(2);

        assertNull(book.getOwner());
        verify(bookRepository).save(book);
    }
}
