package ru.kappers.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kappers.model.CurrencyRate;
import ru.kappers.repository.CurrRateRepository;
import ru.kappers.service.CurrRateService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class CurrRateServiceImpl implements CurrRateService {
    @Autowired
    private CurrRateRepository repository;

    @Override
    public CurrencyRate save(CurrencyRate rate) {
        CurrencyRate cRate = repository.getCurrencyRateByDateAndCharCode(rate.getDate(), rate.getCharCode());
        if (cRate == null) {
            return repository.save(rate);
        } else
            return update(rate);
    }

    @Override
    public boolean isExist(Date date, String currLiteral) {
        CurrencyRate rate = repository.getCurrencyRateByDateAndCharCode(date, currLiteral);
        return rate != null;
    }

    @Override
    public CurrencyRate getCurrByDate(Date date, String currLiteral) {
        return repository.getCurrencyRateByDateAndCharCode(date, currLiteral);
    }

    @Override
    public void clear() {
        repository.deleteAll();
    }

    @Override
    public CurrencyRate update(CurrencyRate rate) {
        CurrencyRate cRate = repository.getCurrencyRateByDateAndCharCode(rate.getDate(), rate.getCharCode());
        if (cRate != null) {
            cRate.setValue(rate.getValue());
            return repository.save(cRate);
        }
        return repository.save(rate);
    }

    @Override
    public CurrencyRate getToday(String literal) {
        return repository.getCurrencyRateByDateAndCharCode(Date.valueOf(LocalDate.now()), literal);
    }

    @Override
    public List<CurrencyRate> getAllToday() {
        return repository.getAllByDate(Date.valueOf(LocalDate.now()));
    }

    @Override
    public List<CurrencyRate> getAllByDate(Date date) {
        return repository.getAllByDate(date);
    }
}
