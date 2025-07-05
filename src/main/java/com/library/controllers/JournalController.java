package com.library.controllers;

import com.library.dao.JournalDao;
import com.library.models.Journal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/journals")
public class JournalController {
    private final JournalDao journalDao;

    public JournalController(JournalDao journalDao) {
        this.journalDao = journalDao;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("journals", journalDao.index());
        return ("journals/index");
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") int id){
        model.addAttribute("journal", journalDao.show(id));
        return ("journals/show");
    }

    @GetMapping("/new")
    public String create(@ModelAttribute("journal") Journal journal){
        return ("journals/new");
    }

    @PostMapping("/new")
    public String save(@ModelAttribute("journal") Journal journal) {
        journalDao.save(journal);
        return ("redirect:/journals");
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("journal", journalDao.show(id));
        return ("journals/edit");
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("journal") Journal journal, @PathVariable("id") int id) {
        journalDao.update(journal, id);
        return ("redirect:/journals");
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        journalDao.delete(id);
        return ("redirect:/journals");
    }

}
