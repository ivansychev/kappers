package ru.kappers.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import lombok.extern.log4j.Log4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kappers.KappersApplication;
import ru.kappers.model.Fixture;
import ru.kappers.service.FixtureService;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

@Log4j
@ActiveProfiles("test")
@ContextConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {KappersApplication.class})
@TestExecutionListeners({DbUnitTestExecutionListener.class})
public class FixtureServiceImplTest {
    @Autowired
    private FixtureService service;

    @Test
    public void getById() {
        Fixture fixture = Fixture.builder().fixture_id(54552).event_date(Timestamp.valueOf(LocalDateTime.now())).build();
        System.out.println(service);
        service.addRecord(fixture);
        Fixture byId = service.getById(54552);
        Assert.assertNotNull(byId);
    }
}