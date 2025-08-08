package com.library.services;

import com.library.models.Journal;
import com.library.models.Member;
import com.library.repositories.JournalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JournalServiceTest {
    private JournalRepository journalRepository;
    private JournalService journalService;
    private int journalId;
    private int nfJournalId;

    @BeforeEach
    void setUp(){
        journalRepository = mock(JournalRepository.class);
        journalService = new JournalService(journalRepository);
        journalId = 1;
        nfJournalId = 999;
    }

    @Test
    void findAll_ReturnsListOfJournals(){
        Journal journal1 = new Journal();
        journal1.setName("Journal1");
        Journal journal2 = new Journal();
        journal2.setName("Journal2");
        when(journalRepository.findAll()).thenReturn(List.of(journal1, journal2));

        List<Journal> result = journalService.findAll();

        assertEquals(2, result.size());
        assertEquals("Journal1", result.get(0).getName());
        verify(journalRepository).findAll();
    }

    @Test
    void findById_WhenJournalExists_ReturnsJournal(){
        Journal journal = new Journal();
        journal.setJournalId(journalId);
        journal.setName("TestJournal");
        when(journalRepository.findById(journalId)).thenReturn(Optional.of(journal));

        Journal result = journalService.findById(journalId);

        assertNotNull(result);
        assertEquals(1, result.getJournalId());
        assertEquals("TestJournal", result.getName());
        verify(journalRepository, times(1)).findById(journalId);
    }

    @Test
    void findById_WhenJournalNotFound_ReturnsNull(){
        when(journalRepository.findById(nfJournalId)).thenReturn(Optional.empty());

        Journal result = journalService.findById(nfJournalId);

        assertNull(result);
        verify(journalRepository, times(1)).findById(nfJournalId);
    }

    @Test
    void save_WhenImageFileIsEmpty_SavesJournal(){
        Journal journal = new Journal();
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(true);

        journalService.save(journal, file);

        verify(journalRepository, times(1)).save(journal);
    }

    @Test
    void update_WhenImageFileIsEmpty_CopiesImagePathAndSavesJournal(){
        Journal existingJournal = new Journal();
        existingJournal.setImagePath("images/existing.jpg");
        Journal updatedJournal = new Journal();

        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(true);
        when(journalRepository.findById(journalId)).thenReturn(Optional.of(existingJournal));

        journalService.update(journalId, updatedJournal, file);

        assertEquals("images/existing.jpg", updatedJournal.getImagePath());
        assertEquals(journalId, updatedJournal.getJournalId());
        verify(journalRepository).save(updatedJournal);
    }

    @Test
    void delete_WhenJournalExists_RemovesItById(){
        journalService.delete(journalId);

        verify(journalRepository, times(1)).deleteById(journalId);
    }

    @Test
    void getJournalOwner_WhenJournalHasOwner_ReturnsMember(){
        Member member = new Member();
        Journal journal = new Journal();
        journal.setOwner(member);
        when(journalRepository.findById(journalId)).thenReturn(Optional.of(journal));

        Optional<Member> result = journalService.getJournalOwner(journalId);

        assertTrue((result.isPresent()));
        assertEquals(member, result.get());
    }

    @Test
    void getJournalOwner_WhenJournalHasNoOwner_ReturnsEmpty(){
        Journal journal = new Journal();
        when(journalRepository.findById(journalId)).thenReturn(Optional.of(journal));

        Optional<Member> result = journalService.getJournalOwner(journalId);

        assertTrue(result.isEmpty());
    }

    @Test
    void getJournalOwner_WhenBookNotFound_ReturnsEmpty(){
        when(journalRepository.findById(nfJournalId)).thenReturn(Optional.empty());

        Optional<Member> result = journalService.getJournalOwner(nfJournalId);

        assertTrue(result.isEmpty());
    }

    @Test
    void assign_WhenJournalExists_SetsOwnerAndSavesJournal(){
        Journal journal = new Journal();
        Member member = new Member();
        when(journalRepository.findById(journalId)).thenReturn(Optional.of(journal));

        journalService.assign(journalId, member);

        assertEquals(member, journal.getOwner());
        verify(journalRepository, times(1)).save(journal);
    }

    @Test
    void release_WhenJournalHasOwner_RemovesOwner(){
        Member member = new Member();
        Journal journal = new Journal();
        journal.setOwner(member);
        when(journalRepository.findById(journalId)).thenReturn(Optional.of(journal));

        journalService.release(journalId);

        assertNull(journal.getOwner());
        verify(journalRepository, times(1)).save(journal);
    }






}
