package ru.kappers.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kappers.config.KappersProperties;
import ru.kappers.model.CurrencyRate;
import ru.kappers.repository.CurrRateRepository;
import ru.kappers.service.CurrRateService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * Реализация сервиса курсов валют
 */
@Slf4j
@Service
@Transactional
public class CurrRateServiceImpl implements CurrRateService {

    private final CurrRateRepository repository;
    private final KappersProperties kappersProperties;

    @Autowired
    public CurrRateServiceImpl(CurrRateRepository repository, KappersProperties kappersProperties) {
        this.repository = repository;
        this.kappersProperties = kappersProperties;
    }

    @Override
    public CurrencyRate save(CurrencyRate rate) {
        log.debug("save(rate: {})...", rate);
        return update(rate);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExist(Date date, String currLiteral) {
        log.debug("isExist(date: {}, currLiteral: {})...", date, currLiteral);
        if (currLiteral.equals(kappersProperties.getRubCurrencyCode())) return true;
        return getCurrByDate(date, currLiteral) != null;
    }

    @Override
    @Transactional(readOnly = true)
    public CurrencyRate getCurrByDate(Date date, String currLiteral) {
        log.debug("getCurrByDate(date: {}, currLiteral: {})...", date, currLiteral);
        return repository.getCurrencyRateByDateAndCharCode(date, currLiteral);
    }

    @Override
    public void clear() {
        log.debug("clear()...");
        repository.deleteAll();
    }

    @Override
    public CurrencyRate update(CurrencyRate rate) {
        log.debug("update(rate: {})...", rate);
        CurrencyRate cRate = getCurrByDate(rate.getDate(), rate.getCharCode());
        if (cRate != null) {
            cRate.setValue(rate.getValue());
            return repository.save(cRate);
        }
        return repository.save(rate);
    }

    @Override
    @Transactional(readOnly = true)
    public CurrencyRate getToday(String literal) {
        log.debug("getToday(literal: {})...", literal);
        return getCurrByDate(Date.valueOf(LocalDate.now()), literal);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CurrencyRate> getAllToday() {
        log.debug("getAllToday()...");
        return getAllByDate(Date.valueOf(LocalDate.now()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CurrencyRate> getAllByDate(Date date) {
        log.debug("getAllByDate(date: {})...", date);
        return repository.getAllByDate(date);
    }
}
