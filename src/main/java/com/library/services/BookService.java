package com.library.services;

import com.library.dao.BookDao;
import com.library.models.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
public class BookService {
    private final BookDao bookDao;

    public BookService(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Value("${upload.dir}")
    private String uploadDir;

    public void save(Book book, MultipartFile file) {
        handleImageUpload(book, file);
        bookDao.save(book);
    }


    public void update(int id, Book book, MultipartFile file) {
        handleImageUpload(book, file);
        bookDao.update(id, book);
    }

    private void handleImageUpload(Book book, MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                String originalName = Paths.get(Objects.requireNonNull(file.getOriginalFilename()))
                        .getFileName().toString();
                String fileName = UUID.randomUUID() + " " + originalName;

                Path uploadPath = Paths.get(uploadDir);
                Files.createDirectories(uploadPath);

                Path filePath = uploadPath.resolve(fileName);
                file.transferTo(filePath);

                book.setImagePath("images/books/" + fileName);
            }
            catch (IOException e) {
                System.err.println("Error during image upload: " + e.getMessage());
            }
        }
    }
}
