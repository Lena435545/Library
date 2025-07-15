package com.library.services;

import com.library.dao.JournalDao;
import com.library.models.Journal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
public class JournalService {

    private final JournalDao journalDao;
    @Value("${upload.dir}")
    private String uploadDir;

    public JournalService(JournalDao journalDao) {
        this.journalDao = journalDao;
    }

    public void save(Journal journal, MultipartFile file) {
        if (!file.isEmpty()) {
            saveImageWithUniqueName(journal, file);
        }
        journalDao.save(journal);
    }

    public void update(int id, Journal journal, MultipartFile file) {
        if (!file.isEmpty()) {
            saveImageWithUniqueName(journal, file);
        } else {
            Journal existingJournal = journalDao.show(id);
            journal.setImagePath(existingJournal.getImagePath());
        }
        journalDao.update(id, journal);
    }

    private void saveImageWithUniqueName(Journal journal, MultipartFile file) {
        try {
            String originalName = Paths.get(Objects.requireNonNull(file.getOriginalFilename()))
                    .getFileName().toString();

            String fileName = UUID.randomUUID() + "_" + originalName;

            Path uploadPath = Paths.get(uploadDir);
            Files.createDirectories(uploadPath);

            Path filePath = uploadPath.resolve(fileName);
            file.transferTo(filePath);

            journal.setImagePath("images/" + fileName);
        } catch (IOException e) {
            System.err.println("Error during image upload: " + e.getMessage());
        }
    }


}
