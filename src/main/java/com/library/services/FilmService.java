package com.library.services;

import com.library.models.Film;
import com.library.models.Member;
import com.library.repositories.FilmRepository;
import com.library.utils.ImageUploadUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class FilmService {

    private final FilmRepository filmRepository;

    @Value("${upload.dir}")
    private String uploadDir;


    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public List<Film> findAll(){
        return filmRepository.findAll();
    }

    public Film findById(int id) {
        Optional<Film> foundFilm = filmRepository.findById(id);

        return foundFilm.orElse(null);
    }

    @Transactional
    public void save(Film film, MultipartFile file) {
        if (!file.isEmpty()) {
            saveImageWithUniqueName(film, file);
        }
        filmRepository.save(film);
    }

    @Transactional
    public void update(int id, Film updatedFilm, MultipartFile file) {
        if (!file.isEmpty()) {
            saveImageWithUniqueName(updatedFilm, file);
        } else {
            Optional<Film> existingFilm = filmRepository.findById(id);
            existingFilm.ifPresent(film -> updatedFilm.setImagePath(film.getImagePath()));
        }
        updatedFilm.setFilmId(id);
        filmRepository.save(updatedFilm);
    }

    @Transactional
    public void delete(int id){
        filmRepository.deleteById(id);
    }
    @Transactional
    public Optional<Member> getFilmOwner(int id) {
        return filmRepository.findById(id).map(Film::getOwner);
    }

    @Transactional
    public void release(int id)  {
        filmRepository.findById(id).ifPresent(film -> {
            film.setOwner(null);
            filmRepository.save(film);
        });
    }

    @Transactional
    public void assign(int id, Member selectedMember) {
        filmRepository.findById(id).ifPresent(film -> {
            film.setOwner(selectedMember);
            filmRepository.save(film);
        });
    }


    private void saveImageWithUniqueName(Film film, MultipartFile file) {
        try {
            String fileName = ImageUploadUtil.saveImageWithUniqueName(file, uploadDir);
            film.setImagePath("images/" + fileName);
        } catch (IOException e) {
            System.out.println("Error during image upload: " + e.getMessage());
        }
    }
}
