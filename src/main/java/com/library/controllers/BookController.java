package com.library.controllers;

import com.library.dao.BookDao;
import com.library.dao.MemberDao;
import com.library.models.Book;
import com.library.models.Member;
import jakarta.servlet.ServletContext;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookDao bookDao;
    private final MemberDao memberDao;
    private final ServletContext servletContext;

    @Value("${upload.path}")
    private String uploadDir;

    public BookController(BookDao bookDao, MemberDao memberDao, ServletContext servletContext) {
        this.bookDao = bookDao;
        this.memberDao = memberDao;
        this.servletContext = servletContext;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("books", bookDao.index());
        return ("books/index");
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("member") Member member) {
        model.addAttribute("book", bookDao.show(id));

        Optional<Member> bookOwner = bookDao.getBookOwner(id);

        if (bookOwner.isPresent())
            model.addAttribute("owner", bookOwner.get());
        else
            model.addAttribute("members", memberDao.index());

        return ("books/show");
    }

    @GetMapping("/new")
    public String create(@ModelAttribute("book") Book book) {
        return ("books/new");
    }

    @PostMapping("/new")
    public String save(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                       @RequestParam("image") MultipartFile file) {
        if (bindingResult.hasErrors())
            return ("books/new");

        if (!file.isEmpty()) {
            try {
                String originalName = Paths.get(file.getOriginalFilename()).getFileName().toString();

                String filename = UUID.randomUUID() + "_" + originalName;

                Path uploadPath = Paths.get(uploadDir);
                Files.createDirectories(uploadPath);

                Path filePath = uploadPath.resolve(filename);
                file.transferTo(filePath);

                book.setImagePath("/images/books/" + filename);

                System.out.println("Image saved at: " + filePath.toAbsolutePath());

            } catch (IOException e) {
                System.err.println("Error while saving the image:");
                e.printStackTrace();
            }
        }

        bookDao.save(book);
        return ("redirect:/books");
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDao.show(id));
        return ("books/edit");
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return ("books/edit");

        bookDao.update(id, book);
        return ("redirect:/books");
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDao.delete(id);
        return ("redirect:/books");
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookDao.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("member") Member selectedMember) {
        bookDao.assign(id, selectedMember);
        return "redirect:/books/" + id;
    }
}
