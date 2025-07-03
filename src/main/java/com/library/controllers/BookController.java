package com.library.controllers;

import com.library.dao.BookDao;
import com.library.models.Book;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookDao bookDao;

    public BookController(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("books", bookDao.index());
        return ("books/index");
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("book", bookDao.show(id));
        return ("books/show");
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return ("books/new");
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("book") Book book){
        bookDao.save(book);
        return ("redirect:/books");
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("book", bookDao.show(id));
        return ("books/edit");
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("book") Book book){
        bookDao.update(id, book);
        return ("redirect:/books");
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookDao.delete(id);
        return ("redirect:/books");
    }
}
