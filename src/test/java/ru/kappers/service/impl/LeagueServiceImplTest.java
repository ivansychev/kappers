package ru.kappers.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.kappers.model.catalog.League;
import ru.kappers.repository.LeagueRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LeagueServiceImplTest {

    @InjectMocks
    private LeagueServiceImpl leagueService;
    @Mock
    private LeagueRepository leagueRepository;

    @Test
    public void save() {
        final League league = mock(League.class);
        when(leagueRepository.save(league)).thenReturn(league);

        final League result = leagueService.save(league);

        assertThat(result, is(league));
        verify(leagueRepository).save(league);
    }

    @Test(expected = NullPointerException.class)
    public void saveMustThrowExceptionIfParameterIsNull() {
        leagueService.save(null);
    }

    @Test
    public void delete() {
        final League league = mock(League.class);

        leagueService.delete(league);

        verify(leagueRepository).delete(league);
    }

    @Test(expected = NullPointerException.class)
    public void deleteMustThrowExceptionIfParameterIsNull() {
        leagueService.delete(null);
    }

    @Test
    public void getById() {
        final int id = 1;
        final League league = mock(League.class);
        final Optional<League> opLeague = Optional.of(league);
        when(leagueRepository.findById(id)).thenReturn(opLeague);

        final League result = leagueService.getById(id);

        assertThat(result, is(league));
        verify(leagueRepository).findById(id);
    }

    @Test
    public void getByIdMustReturnNullIfLeagueNotFound() {
        final int id = 1;
        when(leagueRepository.findById(id)).thenReturn(Optional.empty());

        final League result = leagueService.getById(id);

        assertThat(result, is(nullValue()));
        verify(leagueRepository).findById(id);
    }

    @Test
    public void getByName() {
        final String name = "testName";
        final League league = mock(League.class);
        final Optional<League> opLeague = Optional.of(league);
        when(leagueRepository.findFirstByName(name)).thenReturn(opLeague);

        final League result = leagueService.getByName(name);

        assertThat(result, is(league));
        verify(leagueRepository).findFirstByName(name);
    }

    @Test
    public void getByNameMustReturnNullIfLeagueNotFound() {
        final String name = "testName";
        when(leagueRepository.findFirstByName(name)).thenReturn(Optional.empty());

        final League result = leagueService.getByName(name);

        assertThat(result, is(nullValue()));
        verify(leagueRepository).findFirstByName(name);
    }

    @Test
    public void getAllWithNameContains() {
        final String name = "testName2";
        final List<League> leagueList = Arrays.asList(mock(League.class), mock(League.class));
        when(leagueRepository.findAllByNameContains(name)).thenReturn(leagueList);

        final List<League> resultList = leagueService.getAllWithNameContains(name);

        assertThat(resultList, is(leagueList));
        verify(leagueRepository).findAllByNameContains(name);
    }

    @Test
    public void getAll() {
        final List<League> leagueList = Arrays.asList(mock(League.class), mock(League.class), mock(League.class));
        when(leagueRepository.findAll()).thenReturn(leagueList);

        final List<League> resultList = leagueService.getAll();

        assertThat(resultList, is(leagueList));
        verify(leagueRepository).findAll();
    }
}