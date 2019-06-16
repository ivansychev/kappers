package ru.kappers.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.kappers.model.History;
import ru.kappers.model.User;
import ru.kappers.repository.HistoryRepository;
import ru.kappers.service.impl.HistoryServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HistoryServiceImplTest {

    @InjectMocks
    private HistoryServiceImpl historyService;
    @Mock
    private HistoryRepository historyRepository;

    @Test
    public void addHistoryRecord() {
        final History history = mock(History.class);
        when(historyRepository.save(history)).thenReturn(history);

        final History result = historyService.addHistoryRecord(history);

        assertThat(result, is(history));
        verify(historyRepository).save(history);
    }

    @Test
    public void delete() {
        final History history = mock(History.class);

        historyService.delete(history);

        verify(historyRepository).delete(history);
    }

    @Test
    public void clearHistory() {
        final User user = mock(User.class);

        historyService.clearHistory(user);

        verify(historyRepository).deleteAllByUser(user);
    }

    @Test
    public void getById() {
        final int id = 1;
        final History history = mock(History.class);
        when(historyRepository.getById(id)).thenReturn(history);

        final History result = historyService.getById(id);

        assertThat(result, is(history));
        verify(historyRepository).getById(id);
    }

    @Test
    public void getUsersHistory() {
        final User user = mock(User.class);
        final List<History> historyList = Arrays.asList(mock(History.class), mock(History.class));
        when(historyRepository.getByUser(user)).thenReturn(historyList);

        final List<History> resultList = historyService.getUsersHistory(user);

        assertThat(resultList, is(historyList));
        verify(historyRepository).getByUser(user);
    }

    @Test
    public void editHistory() {
        final History history = mock(History.class);
        when(historyRepository.save(history)).thenReturn(history);

        final History result = historyService.editHistory(history);

        assertThat(result, is(history));
        verify(historyRepository).save(history);
    }

    @Test
    public void getAll() {
        final List<History> historyList = Arrays.asList(mock(History.class), mock(History.class));
        when(historyRepository.findAll()).thenReturn(historyList);

        final List<History> resultList = historyService.getAll();

        assertThat(resultList, is(historyList));
        verify(historyRepository).findAll();
    }
}