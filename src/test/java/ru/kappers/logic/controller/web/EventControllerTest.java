package ru.kappers.logic.controller.web;

import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.kappers.logic.controller.EventController;
import ru.kappers.service.EventService;
import ru.kappers.service.FixtureService;
import ru.kappers.service.KapperInfoService;
import ru.kappers.service.UserService;

@Slf4j
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = EventController.class)
@ActiveProfiles("test")
@MockBeans({
    @MockBean(FixtureService.class),
    @MockBean(EventService.class),
    @MockBean(UserService.class),
    @MockBean(KapperInfoService.class),
    //@MockBean(ConversionService.class)
})
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Ignore
    @Test
    public void getFixtureById() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/rest/events/101")
                .accept(MediaType.APPLICATION_JSON)
//                .header() //todo добавить авторизацию
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

}