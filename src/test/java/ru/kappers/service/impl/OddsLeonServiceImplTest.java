package ru.kappers.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.kappers.model.leonmodels.OddsLeon;
import ru.kappers.repository.OddsLeonRepository;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OddsLeonServiceImplTest {
    @InjectMocks
    private OddsLeonServiceImpl oddsLeonService;
    @Mock
    private OddsLeonRepository repository;

    @Test
    public void save() {
        final OddsLeon odd = mock(OddsLeon.class);
        when(repository.save(odd)).thenReturn(odd);

        final OddsLeon result = oddsLeonService.save(odd);

        assertThat(result, is(odd));
        verify(repository).save(odd);
    }

    @Test
    public void delete() {
        final OddsLeon odd = mock(OddsLeon.class);

        oddsLeonService.delete(odd);

        verify(repository).delete(odd);
    }

    @Test
    public void update() {
        final OddsLeon odd = mock(OddsLeon.class);
        when(repository.save(odd)).thenReturn(odd);
        oddsLeonService = spy(oddsLeonService);

        final OddsLeon result = oddsLeonService.update(odd);

        assertThat(result, is(odd));
        verify(oddsLeonService).save(odd);
        verify(repository).save(odd);
    }

    @Test
    public void getById() {
        final long id = 1L;
        final OddsLeon odd = mock(OddsLeon.class);
        when(repository.getOne(id)).thenReturn(odd);

        final OddsLeon result = oddsLeonService.getById(id);

        assertThat(result, is(odd));
        verify(repository).getOne(id);
    }

    @Test
    public void getAll() {
        final List<OddsLeon> oddsLeonList = Arrays.asList(mock(OddsLeon.class), mock(OddsLeon.class));
        when(repository.findAll()).thenReturn(oddsLeonList);

        final List<OddsLeon> result = oddsLeonService.getAll();

        assertThat(result, is(oddsLeonList));
        verify(repository).findAll();
    }
}