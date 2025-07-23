package com.library.services;

import com.library.dao.FilmDao;
import com.library.models.Film;
import com.library.repositories.FilmRepository;
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

    private final FilmDao filmDao;
    private final FilmRepository filmRepository;

    @Value("${upload.dir}")
    private String uploadDir;


    public FilmService(FilmDao filmDao, FilmRepository filmRepository) {
        this.filmDao = filmDao;
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


    private void saveImageWithUniqueName(Film film, MultipartFile file) {
        try {
            String originalName = Paths.get(Objects.requireNonNull(file.getOriginalFilename()))
                    .getFileName().toString();
            String fileName = UUID.randomUUID() + "_" + originalName;

            Path uploadPath = Paths.get(uploadDir);
            Files.createDirectories(uploadPath);

            Path filePath = uploadPath.resolve(fileName);
            file.transferTo(filePath);

            film.setImagePath("images/" + fileName);

        } catch (IOException e) {
            System.out.println("Error during image upload: " + e.getMessage());
        }
    }
}
