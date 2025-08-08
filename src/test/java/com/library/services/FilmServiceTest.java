package com.library.services;

import com.library.models.Film;
import com.library.models.Member;
import com.library.repositories.FilmRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FilmServiceTest {
    private FilmRepository filmRepository;
    private FilmService filmService;
    private int filmId;
    private int nfFilmId;

    @BeforeEach
    void setUp() {
        filmRepository = mock(FilmRepository.class);
        filmService = new FilmService(filmRepository);
        filmId = 1;
        nfFilmId = 999;
    }

    @Test
    void findAllReturnsListOfFilms() {
        Film film1 = new Film();
        film1.setName("Film1");
        Film film2 = new Film();
        film2.setName("Film2");
        when(filmRepository.findAll()).thenReturn(List.of(film1, film2));

        List<Film> result = filmService.findAll();

        assertEquals(2, result.size());
        assertEquals("Film1", result.get(0).getName());
        verify(filmRepository, times(1)).findAll();
    }

    @Test
    void findByIdReturnsFilmWhenExists() {
        Film film = new Film();
        film.setFilmId(filmId);
        film.setName("TestFilm");
        when(filmRepository.findById(filmId)).thenReturn(Optional.of(film));

        Film result = filmService.findById(filmId);

        assertNotNull(result);
        assertEquals(1, result.getFilmId());
        assertEquals("TestFilm", result.getName());
        verify(filmRepository, times(1)).findById(filmId);
    }

    @Test
    void findById_WhenFilmNotFound_ReturnsNull() {
        when(filmRepository.findById(nfFilmId))
                .thenReturn(Optional.empty());

        Film result = filmService.findById(nfFilmId);

        assertNull(result);
        verify(filmRepository, times(1)).findById(nfFilmId);
    }

    @Test
    void save_WhenImageFileIsEmpty_SavesFilm() {
        Film film = new Film();
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(true);

        filmService.save(film, file);

        verify(filmRepository, times(1)).save(film);
    }

    @Test
    void update_WhenImageFileIsEmpty_CopiesImagePathAndSavesFilm() {
        Film existingFilm = new Film();
        existingFilm.setImagePath("images/existing.jpg");
        Film updatedFilm = new Film();

        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(true);
        when(filmRepository.findById(filmId)).thenReturn(Optional.of(existingFilm));

        filmService.update(filmId, updatedFilm, file);

        assertEquals("images/existing.jpg", updatedFilm.getImagePath());
        assertEquals(filmId, updatedFilm.getFilmId());
        verify(filmRepository).save(updatedFilm);
    }

    @Test
    void delete_WhenFilmExists_RemovesFilmById(){
        filmService.delete(filmId);

        verify(filmRepository, times(1)).deleteById(filmId);
    }

    @Test
    void getFilmOwner_WhenFilmHasOwner_ReturnsMember(){
        Member member = new Member();
        Film film = new Film();
        film.setOwner(member);
        when(filmRepository.findById(filmId)).thenReturn(Optional.of(film));

        Optional<Member> result = filmService.getFilmOwner(filmId);

        assertTrue(result.isPresent());
        assertEquals(member, result.get());
    }

    @Test
    void getFilmOwner_WhenFilmHasNoOwner_ReturnsEmpty(){
        Film film = new Film();
        when(filmRepository.findById(filmId)).thenReturn(Optional.of(film));

        Optional<Member> result = filmService.getFilmOwner(filmId);

        assertTrue(result.isEmpty());
    }

    @Test
    void getFilmOwner_WhenFilmNotFound_ReturnsEmpty(){
        when(filmRepository.findById(nfFilmId)).thenReturn(Optional.empty());

        Optional<Member> result = filmService.getFilmOwner(nfFilmId);

        assertTrue(result.isEmpty());
    }

    @Test
    void assign_WhenFilmExists_SetsOwnerAndSavesFilm(){
        Film film = new Film();
        Member member = new Member();
        when(filmRepository.findById(filmId)).thenReturn(Optional.of(film));

        filmService.assign(filmId, member);

        assertEquals(member, film.getOwner());
        verify(filmRepository, times(1)).save(film);
    }

    @Test
    void release_WhenFilmHasOwner_removesOwner(){
        Member member = new Member();
        Film film = new Film();
        film.setOwner(member);
        when(filmRepository.findById(filmId)).thenReturn(Optional.of(film));

        filmService.release(filmId);

        assertNull(film.getOwner());
        verify(filmRepository, times(1)).save(film);
    }

























}