package ru.kappers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kappers.model.CurrencyRate;
import ru.kappers.model.Event;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public interface CurrRateRepository extends JpaRepository<CurrencyRate, Integer> {
   boolean existsCurrencyRateByDateAndCharCode(Date date, String currLiteral);
   CurrencyRate getCurrencyRateByDateAndCharCode(Date date, String currLiteral);
   void deleteAll();
   List<CurrencyRate> getAllByDate(Date timestamp);


}
