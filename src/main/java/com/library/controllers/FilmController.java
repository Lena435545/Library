package com.library.controllers;

import com.library.dao.FilmDao;
import com.library.models.Film;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/films")
public class FilmController {

    private final FilmDao filmDao;

    public FilmController(FilmDao filmDao) {
        this.filmDao = filmDao;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("films", filmDao.index());
        return "films/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("film", filmDao.show(id));
        return "films/show";
    }

    @GetMapping("/new")
    public String create(@ModelAttribute("film") Film film) {
        return ("films/new");
    }

    @PostMapping("/new")
    public String save(@ModelAttribute("film") Film film) {
        filmDao.save(film);
        return ("redirect:/films");
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("film", filmDao.show(id));
        return ("films/edit");
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("film") Film film, @PathVariable("id") int id) {
        filmDao.update(film, id);
        return ("redirect:/films");
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        filmDao.delete(id);
        return ("redirect:/films");
    }
}
