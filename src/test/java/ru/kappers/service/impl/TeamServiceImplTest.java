package ru.kappers.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.kappers.model.catalog.Team;
import ru.kappers.repository.TeamRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TeamServiceImplTest {

    @InjectMocks
    private TeamServiceImpl teamService;
    @Mock
    private TeamRepository teamRepository;

    @Test
    public void save() {
        final Team team = mock(Team.class);
        when(teamRepository.save(team)).thenReturn(team);

        final Team result = teamService.save(team);

        assertThat(result, is(team));
        verify(teamRepository).save(team);
    }

    @Test(expected = NullPointerException.class)
    public void saveMustThrowExceptionIfParameterIsNull() {
        teamService.save(null);
    }

    @Test
    public void delete() {
        final Team team = mock(Team.class);

        teamService.delete(team);

        verify(teamRepository).delete(team);
    }

    @Test(expected = NullPointerException.class)
    public void deleteMustThrowExceptionIfParameterIsNull() {
        teamService.delete(null);
    }

    @Test
    public void getById() {
        final int id = 1;
        final Team team = mock(Team.class);
        final Optional<Team> opTeam = Optional.of(team);
        when(teamRepository.findById(id)).thenReturn(opTeam);

        final Team result = teamService.getById(id);

        assertThat(result, is(team));
        verify(teamRepository).findById(id);
    }

    @Test
    public void getByIdMustReturnNullIfTeamNotFound() {
        final int id = 1;
        when(teamRepository.findById(id)).thenReturn(Optional.empty());

        final Team result = teamService.getById(id);

        assertThat(result, is(nullValue()));
        verify(teamRepository).findById(id);
    }

    @Test
    public void getByName() {
        final String name = "testName";
        final Team team = mock(Team.class);
        final Optional<Team> opTeam = Optional.of(team);
        when(teamRepository.findFirstByName(name)).thenReturn(opTeam);

        final Team result = teamService.getByName(name);

        assertThat(result, is(team));
        verify(teamRepository).findFirstByName(name);
    }

    @Test
    public void getByNameMustReturnNullIfTeamNotFound() {
        final String name = "testName";
        when(teamRepository.findFirstByName(name)).thenReturn(Optional.empty());

        final Team result = teamService.getByName(name);

        assertThat(result, is(nullValue()));
        verify(teamRepository).findFirstByName(name);
    }

    @Test
    public void getAllWithNameContains() {
        final String name = "testName";
        final List<Team> teamList = Arrays.asList(mock(Team.class), mock(Team.class));
        when(teamRepository.findAllByNameContains(name)).thenReturn(teamList);

        final List<Team> resultList = teamService.getAllWithNameContains(name);

        assertThat(resultList, is(teamList));
        verify(teamRepository).findAllByNameContains(name);
    }

    @Test
    public void getAll() {
        final List<Team> teamList = Arrays.asList(mock(Team.class), mock(Team.class));
        when(teamRepository.findAll()).thenReturn(teamList);

        final List<Team> resultList = teamService.getAll();

        assertThat(resultList, is(teamList));
        verify(teamRepository).findAll();
    }

    @Test
    public void getAllById() {
        final List<Team> teamList = Arrays.asList(mock(Team.class), mock(Team.class));
        when(teamRepository.findAllById(anyIterable())).thenReturn(teamList);

        final List<Team> resultList = teamService.getAllById(Arrays.asList(1, 2));

        assertThat(resultList, is(teamList));
        verify(teamRepository).findAllById(anyIterable());
    }

    @Test
    public void getAllByIdIsNotIn() {
        final List<Team> teamList = Arrays.asList(mock(Team.class), mock(Team.class));
        when(teamRepository.findAllByIdIsNotIn(anyIterable())).thenReturn(teamList);

        final List<Team> resultList = teamService.getAllByIdIsNotIn(Arrays.asList(1, 2));

        assertThat(resultList, is(teamList));
        verify(teamRepository).findAllByIdIsNotIn(anyIterable());
    }
}