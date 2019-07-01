package ru.kappers.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.kappers.model.catalog.League;
import ru.kappers.model.leonmodels.MarketLeon;
import ru.kappers.model.leonmodels.OddsLeon;
import ru.kappers.model.leonmodels.RunnerLeon;
import ru.kappers.repository.RunnerLeonRepository;
import ru.kappers.service.MarketLeonService;
import ru.kappers.service.OddsLeonService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RunnerLeonServiceImplTest {
    @InjectMocks
    private RunnerLeonServiceImpl runnerLeonService;
    @Mock
    private RunnerLeonRepository repository;
    @Mock
    private MarketLeonService marketService;
    @Mock
    private OddsLeonService oddService;

    @Test
    public void getByMarketAndOdd() {
        final long marketId = 1L;
        final long oddId = 2L;
        final MarketLeon market = mock(MarketLeon.class);
        final OddsLeon odd = mock(OddsLeon.class);
        final List<RunnerLeon> runnerLeonList = Arrays.asList(mock(RunnerLeon.class), mock(RunnerLeon.class));
        when(marketService.getById(marketId)).thenReturn(market);
        when(oddService.getById(oddId)).thenReturn(odd);
        when(repository.getByMarketAndOdd(market, odd)).thenReturn(runnerLeonList);

        final List<RunnerLeon> result = runnerLeonService.getByMarketAndOdd(marketId, oddId);

        assertThat(result, is(runnerLeonList));
        verify(marketService).getById(marketId);
        verify(oddService).getById(oddId);
        verify(repository).getByMarketAndOdd(market, odd);
    }

    @Test
    public void getByOdd() {
        final long oddId = 1L;
        final OddsLeon odd = mock(OddsLeon.class);
        final List<RunnerLeon> runnerLeonList = Arrays.asList(mock(RunnerLeon.class), mock(RunnerLeon.class));
        when(oddService.getById(oddId)).thenReturn(odd);
        when(repository.getByOdd(odd)).thenReturn(runnerLeonList);

        final List<RunnerLeon> result = runnerLeonService.getByOdd(oddId);

        assertThat(result, is(runnerLeonList));
        verify(oddService).getById(oddId);
        verify(repository).getByOdd(odd);
    }

    @Test
    public void getAll() {
        final List<RunnerLeon> runnerLeonList = Arrays.asList(mock(RunnerLeon.class), mock(RunnerLeon.class));
        when(repository.findAll()).thenReturn(runnerLeonList);

        final List<RunnerLeon> result = runnerLeonService.getAll();

        assertThat(result, is(runnerLeonList));
        verify(repository).findAll();
    }

    @Test
    public void save() {
        final RunnerLeon runner = mock(RunnerLeon.class);
        when(repository.save(runner)).thenReturn(runner);

        final RunnerLeon result = runnerLeonService.save(runner);

        assertThat(result, is(runner));
        verify(repository).save(runner);
    }

    @Test
    public void delete() {
        final RunnerLeon runner = mock(RunnerLeon.class);

        runnerLeonService.delete(runner);

        verify(repository).delete(runner);
    }

    @Test
    public void getById() {
        final long id = 1L;
        final RunnerLeon runner = mock(RunnerLeon.class);
        final Optional<RunnerLeon> opRunner = Optional.of(runner);

        when(repository.findById(id)).thenReturn(opRunner);

        final RunnerLeon result = runnerLeonService.getById(id);

        assertThat(result, is(runner));
        verify(repository).findById(id);
    }

    @Test
    public void deleteAllByOddId() {
        final long oddId = 1L;
        final OddsLeon odd = mock(OddsLeon.class);
        when(oddService.getById(oddId)).thenReturn(odd);

        runnerLeonService.deleteAllByOddId(oddId);

        verify(oddService).getById(oddId);
        verify(repository).deleteAllByOdd(odd);
    }

    @Test
    public void saveAll() {
        final List<RunnerLeon> runnerLeonList = Arrays.asList(mock(RunnerLeon.class), mock(RunnerLeon.class));
        when(repository.save(any())).thenAnswer(it -> it.getArgument(0));

        final List<RunnerLeon> result = runnerLeonService.saveAll(runnerLeonList);

        assertThat(result, is(runnerLeonList));
        verify(repository, times(runnerLeonList.size())).save(any());
    }
}