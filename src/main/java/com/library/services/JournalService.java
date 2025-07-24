package com.library.services;

import com.library.models.Journal;
import com.library.models.Member;
import com.library.repositories.JournalRepository;
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
public class JournalService {

    private final JournalRepository journalRepository;

    @Value("${upload.dir}")
    private String uploadDir;
    public JournalService(JournalRepository journalRepository) {
        this.journalRepository = journalRepository;
    }

    public List<Journal> findAll(){
        return journalRepository.findAll();
    }

    public Journal findById(int id)  {
        Optional<Journal> foundJournal = journalRepository.findById(id);
        return foundJournal.orElse(null);
    }

    @Transactional
    public void save(Journal journal, MultipartFile file) {
        if (!file.isEmpty()) {
            saveImageWithUniqueName(journal, file);
        }
        journalRepository.save(journal);
    }

    @Transactional
    public void update(int id, Journal updatedJournal, MultipartFile file) {
        if (!file.isEmpty()) {
            saveImageWithUniqueName(updatedJournal, file);
        } else {
            Optional<Journal> existingJournal = journalRepository.findById(id);
            existingJournal.ifPresent(journal -> updatedJournal.setImagePath(journal.getImagePath()));
        }
        updatedJournal.setJournalId(id);
        journalRepository.save(updatedJournal);
    }

    @Transactional
    public void delete(int id) {
        journalRepository.deleteById(id);
    }

    @Transactional
    public Optional<Member> getJournalOwner(int id) {
        return journalRepository.findById(id).map(Journal::getOwner);
    }

    @Transactional
    public void release (int id) {
        journalRepository.findById(id).ifPresent(journal -> {
            journal.setOwner(null);
            journalRepository.save(journal);
        });
    }

    @Transactional
    public void assign(int id, Member selectedMember) {
        journalRepository.findById(id).ifPresent(journal -> {
            journal.setOwner(selectedMember);
            journalRepository.save(journal);
        });
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
