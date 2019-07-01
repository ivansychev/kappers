package ru.kappers.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.kappers.model.catalog.League;
import ru.kappers.model.leonmodels.CompetitorLeon;
import ru.kappers.repository.CompetitorRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CompetitorLeonServiceImplTest {
    @InjectMocks
    private CompetitorLeonServiceImpl competitorLeonService;
    @Mock
    private CompetitorRepository repository;

    @Test
    public void save() {
        final CompetitorLeon competitor = mock(CompetitorLeon.class);
        when(repository.save(competitor)).thenReturn(competitor);

        final CompetitorLeon result = competitorLeonService.save(competitor);

        assertThat(result, is(competitor));
        verify(repository).save(competitor);
    }

    @Test
    public void delete() {
        final CompetitorLeon competitor = mock(CompetitorLeon.class);

        competitorLeonService.delete(competitor);

        verify(repository).delete(competitor);
    }

    @Test
    public void update() {
        final CompetitorLeon competitor = mock(CompetitorLeon.class);
        when(repository.save(competitor)).thenReturn(competitor);

        final CompetitorLeon result = competitorLeonService.update(competitor);

        assertThat(result, is(competitor));
        verify(repository).save(competitor);
    }

    @Test
    public void getById() {
        final long id = 1L;
        final CompetitorLeon competitor = mock(CompetitorLeon.class);
        final Optional<CompetitorLeon> opCompetitor = Optional.of(competitor);
        when(repository.findById(id)).thenReturn(opCompetitor);
        final CompetitorLeon result = competitorLeonService.getById(id);
        assertThat(result, is(competitor));
        verify(repository).findById(id);
    }

    @Test
    public void getAll() {
        final List<CompetitorLeon> competitorLeonList = Arrays.asList(mock(CompetitorLeon.class), mock(CompetitorLeon.class));
        when(repository.findAll()).thenReturn(competitorLeonList);

        final List<CompetitorLeon> result = competitorLeonService.getAll();

        assertThat(result, is(competitorLeonList));
        verify(repository).findAll();
    }

    @Test
    public void getByName() {
        final String name = "test name";
        final CompetitorLeon competitor = mock(CompetitorLeon.class);
        when(repository.getByName(name)).thenReturn(competitor);

        final CompetitorLeon result = competitorLeonService.getByName(name);

        assertThat(result, is(competitor));
        verify(repository).getByName(name);
    }
}