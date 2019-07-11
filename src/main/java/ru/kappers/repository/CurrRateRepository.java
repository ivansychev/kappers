package ru.kappers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kappers.model.CurrencyRate;

import java.time.LocalDate;
import java.util.List;

public interface CurrRateRepository extends JpaRepository<CurrencyRate, Integer> {
   boolean existsCurrencyRateByDateAndCharCode(LocalDate date, String currLiteral);
   CurrencyRate getCurrencyRateByDateAndCharCode(LocalDate date, String currLiteral);
   void deleteAll();
   List<CurrencyRate> getAllByDate(LocalDate date);


}
