package ru.kappers.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import lombok.extern.log4j.Log4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kappers.KappersApplication;
import ru.kappers.model.CurrencyRate;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.*;

@Log4j
@ActiveProfiles("test")
@ContextConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {KappersApplication.class})
@TestExecutionListeners({DbUnitTestExecutionListener.class})
@DatabaseSetup("/data/CurrRateServiceImplTest-currrates.xml")

public class CurrRateServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private CurrRateService service;

    @Test
    public void save() {
        CurrencyRate rate = CurrencyRate.builder()
                .nominal(1)
                .value(4650)
                .date(Date.valueOf("2018-11-23"))
                .charCode("BTC")
                .numCode("000")
                .name("Bitcoin")
                .build();
        CurrencyRate save = service.save(rate);
        assertNotNull(save);
        assertEquals(save.getValue(), 4650.00d, 0.001);
        assertEquals(save.getNominal(), 1);
        assertEquals(save.getCharCode(), "BTC");
        assertEquals(save.getName(), "Bitcoin");
        assertEquals(save.getNumCode(), "000");
        assertEquals(save.getDate(), Date.valueOf(LocalDate.of(2018, Month.NOVEMBER, 23)));
    }

    @Test
    public void isExist() {
//        CurrencyRate rate = CurrencyRate.builder()
//                .nominal(1)
//                .value(4650)
//                .date(Date.valueOf("2018-11-23"))
//                .charCode("BTC")
//                .numCode("000")
//                .name("Bitcoin")
//                .build();
//        service.save(rate);
        assertTrue(service.isExist(Date.valueOf("2018-11-21"), "GLD"));
    }

    @Test
    public void getCurrByDate() {
        CurrencyRate gld = service.getCurrByDate(Date.valueOf("2018-11-21"), "GLD");
        assertNotNull(gld);
        assertEquals(gld.getNumCode(), "999");
    }

    @Test
    public void update() {
        CurrencyRate gld = service.getCurrByDate(Date.valueOf("2018-11-21"), "GLD");
        assertNotNull(gld);
        assertEquals(gld.getValue(), 2000.00d, 0.001);
        gld.setValue(3000);
        CurrencyRate update = service.update(gld);
        assertEquals(update.getValue(), 3000, 0.001);
    }

    @Test
    public void getToday() {
    }

    @Test
    public void getAllToday() {
    }

    @Test
    public void getAllByDate() {
        List<CurrencyRate> allByDate = service.getAllByDate(Date.valueOf("2018-11-21"));
        CurrencyRate gld = service.getCurrByDate(Date.valueOf("2018-11-21"), "GLD");
        assertTrue(allByDate.contains(gld));
    }
}