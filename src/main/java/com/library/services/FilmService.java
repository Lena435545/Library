package com.library.services;

import com.library.dao.FilmDao;
import com.library.models.Film;
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
public class FilmService {

    private final FilmDao filmDao;

    public FilmService(FilmDao filmDao) {
        this.filmDao = filmDao;
    }

    @Value("${upload.dir}")
    private String uploadDir;

    public void save(Film film, MultipartFile file) {
        if (!file.isEmpty()) {
            saveImageWithUniqueName(film, file);
        }
        filmDao.save(film);
    }

    public void update(int id, Film film, MultipartFile file) {
        if (!file.isEmpty()) {
            saveImageWithUniqueName(film, file);
        } else {
            Film existingFilm = filmDao.show(id);
            film.setImagePath(existingFilm.getImagePath());
        }
        filmDao.update(id, film);
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
