package com.library.controllers;

import com.library.dao.BookDao;
import com.library.dao.MemberDao;
import com.library.models.Book;
import com.library.models.Member;
import com.library.services.BookService;
import com.library.services.MemberService;
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
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/books")
public class BookController {

    private final MemberService memberService;
    private final BookDao bookDao;
    private final BookService bookService;


    public BookController(MemberDao memberDao, MemberService memberService, BookDao bookDao, BookService bookService) {
        this.memberService = memberService;
        this.bookDao = bookDao;
        this.bookService = bookService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("books", bookService.findAll());
        return ("books/index");
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("member") Member member) {
        model.addAttribute("book", bookService.findById(id));

        Optional<Member> bookOwner = bookDao.getBookOwner(id);

        if (bookOwner.isPresent())
            model.addAttribute("owner", bookOwner.get());
        else
            model.addAttribute("members", memberService.findAll());

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

        bookService.save(book, file);

        return ("redirect:/books");
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.findById(id));
        return ("books/edit");
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult, @RequestParam("image") MultipartFile file) {
        if (bindingResult.hasErrors())
            return ("books/edit");
        bookService.update(id, book, file);
        return ("redirect:/books");
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
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
