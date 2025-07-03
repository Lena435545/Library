package com.library.controllers;

import com.library.dao.FilmDao;
import com.library.models.Film;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("film", filmDao.show(id));
        return "films/show";
    }

    @GetMapping("/new")
    public String newFilm(@ModelAttribute("film") Film film){
        return ("films/new");
    }

}
