package ru.kappers.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.kappers.model.leonmodels.MarketLeon;
import ru.kappers.repository.MarketLeonRepository;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MarketLeonServiceImplTest {
    @InjectMocks
    private MarketLeonServiceImpl marketLeonService;
    @Mock
    private MarketLeonRepository repository;

    @Test
    public void getByName() {
        final String name = "test name";
        final MarketLeon marketLeon = mock(MarketLeon.class);
        when(repository.getByName(name)).thenReturn(marketLeon);

        final MarketLeon result = marketLeonService.getByName(name);

        assertThat(result, is(marketLeon));
        verify(repository).getByName(name);
    }

    @Test
    public void getById() {
        final long id = 1L;
        final MarketLeon marketLeon = mock(MarketLeon.class);
        when(repository.getOne(id)).thenReturn(marketLeon);

        final MarketLeon result = marketLeonService.getById(id);

        assertThat(result, is(marketLeon));
        verify(repository).getOne(id);
    }

    @Test
    public void getAll() {
        List<MarketLeon> marketLeonList = Arrays.asList(mock(MarketLeon.class), mock(MarketLeon.class));
        when(repository.findAll()).thenReturn(marketLeonList);

        final List<MarketLeon> result = marketLeonService.getAll();

        assertThat(result, is(marketLeonList));
        verify(repository).findAll();
    }

    @Test
    public void save() {
        final MarketLeon marketLeon = mock(MarketLeon.class);
        when(repository.save(marketLeon)).thenReturn(marketLeon);

        final MarketLeon result = marketLeonService.save(marketLeon);

        assertThat(result, is(marketLeon));
        verify(repository).save(marketLeon);
    }
}