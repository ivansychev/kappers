package ru.kappers.logic.controller.web;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kappers.model.Event;
import ru.kappers.model.Fixture;
import ru.kappers.model.User;
import ru.kappers.model.dto.EventDTO;
import ru.kappers.model.utilmodel.Odds;
import ru.kappers.model.utilmodel.Outcomes;
import ru.kappers.repository.FixtureRepository;
import ru.kappers.repository.KapperInfoRepository;
import ru.kappers.service.FixtureService;
import ru.kappers.service.KapperInfoService;
import ru.kappers.service.UserService;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Slf4j
@ContextConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
@TestExecutionListeners({DbUnitTestExecutionListener.class})
public class EventControllerTest extends AbstractTransactionalJUnit4SpringContextTests {

    public static final String KAPPER_USERNAME = "kapper1";
    public static final String KAPPER_PASSWORD = "kapper1";
    public Gson GSON = new Gson();
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private FixtureService fixtureService;
    @Autowired
    private FixtureRepository fixtureRepository;
    @Autowired
    private KapperInfoService kapperInfoService;
    @Autowired
    private KapperInfoRepository kapperInfoRepository;
    @Autowired
    private UserService userService;
//    @MockBean
//    private EventService eventService;
//    @SpyBean
//    private EventController eventController;
//    @Autowired
//    private MockMvc mockMvc;


    private Fixture findFixture() {
        return fixtureRepository
                .findAll(PageRequest.of(0, 1))
                .getContent()
                .get(0);
    }

    @Test
    public void getFixtureByIdWithoutAuthorization() {
        final int fixtureId = 101;

        final ResponseEntity<Odds> response = testRestTemplate
                .getForEntity("/rest/events/" + fixtureId, Odds.class);

        assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
    }

    @Test
    public void getFixtureById() {
        final Fixture dbFixture = findFixture();
        assertThat(dbFixture, is(notNullValue()));
        final int fixtureId = dbFixture.getId();

        final ResponseEntity<Odds> response = testRestTemplate
                .withBasicAuth(KAPPER_USERNAME, KAPPER_PASSWORD)
                .getForEntity("/rest/events/" + fixtureId, Odds.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().getFixture(), is(notNullValue()));
        assertThat(response.getBody().getFixture().getId(), is(fixtureId));
    }

    @Ignore
    @Test
//    @DatabaseSetup("/data/EventControllerTest.xml")
    public void createEvent() throws Exception {
        final User kapperUser = userService.getByUserName(KAPPER_USERNAME);
        assertThat(kapperUser, is(notNullValue()));
        kapperInfoService.initKapper(kapperUser);
        kapperInfoRepository.flush();

        final Fixture dbFixture = findFixture();
        assertThat(dbFixture, is(notNullValue()));
        final EventDTO eventDTO = EventDTO.builder()
                .outcome(Outcomes.HOMETEAMWIN)
                .coefficient(new BigDecimal("1.35"))
                .tokens(50)
                .price(new BigDecimal("500"))
                .f_id(dbFixture.getId())
                .build();
        final String data = GSON.toJson(eventDTO);
//        final Authentication authentication = mock(Authentication.class);
//        when(eventController.getCurrentAuthentication()).thenReturn(authentication);
//        when(eventService.createEventByUser())

//        mockMvc.perform(
//                MockMvcRequestBuilders.post("/rest/events/create")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON)
////                        .header("username", "kapper") //todo добавить авторизацию, пока временно отключил проверку
//                        .content(data)
//                ).andExpect(status().isOk())
//                .andExpect(jsonPath("$.outcome", is(eventDTO.getOutcome())))
//                .andExpect(jsonPath("$.coefficient", is(eventDTO.getCoefficient())))
//                .andExpect(jsonPath("$.tokens", is(eventDTO.getTokens())))
//                .andExpect(jsonPath("$.price", is(eventDTO.getPrice())))
//                .andExpect(jsonPath("$.fixture.id", is(eventDTO.getF_id())));
        final ResponseEntity<Event> response = testRestTemplate
                .withBasicAuth(KAPPER_USERNAME, KAPPER_PASSWORD)
                .postForEntity("/rest/events/create", data, Event.class);

        //todo разобраться почему внутри теста инициируется KapperInfo, а при вызове контроллера не находит KapperInfo
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().getOutcome(), is(eventDTO.getOutcome()));
        assertThat(response.getBody().getCoefficient(), is(eventDTO.getCoefficient()));
        assertThat(response.getBody().getTokens(), is(eventDTO.getTokens()));
        assertThat(response.getBody().getPrice(), is(eventDTO.getPrice()));
        assertThat(response.getBody().getFixture(), is(notNullValue()));
        assertThat(response.getBody().getFixture().getId(), is(eventDTO.getF_id()));
    }

    @TestConfiguration
    public static class TestConfig {
    }
}