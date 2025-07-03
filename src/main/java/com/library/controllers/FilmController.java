package com.library.controllers;

import com.library.dao.FilmDao;
import com.library.models.Film;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

}
