package ru.kappers.convert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import ru.kappers.model.catalog.Team;
import ru.kappers.model.dto.rapidapi.TeamRapidDTO;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TeamRapidDTOTeamConverterTest {
    @InjectMocks
    private TeamRapidDTOTeamConverter converter;

    @Test
    public void convertMustReturnNullIfParameterIsNull() {
        assertNull(converter.convert(null));
    }

    @Test
    public void convert() {
        final List<TeamRapidDTO> dtoList = Arrays.asList(
                TeamRapidDTO.builder()
                        .team_id(1)
                        .name("Уиган Атлетик")
                        .code("Latics")
                        .logo("https://upload.wikimedia.org/wikipedia/ru/thumb/0/05/FC_Wigan_Athletic_Logo.svg/440px-FC_Wigan_Athletic_Logo.svg.png")
                        .build(),
                TeamRapidDTO.builder()
                        .team_id(2)
                        .name("«Локомотив» (Москва)")
                        .code("Железнодорожники")
                        .logo("https://upload.wikimedia.org/wikipedia/ru/c/c5/FC_Lokomotiv.png")
                        .build()
        );

        for (TeamRapidDTO dto : dtoList) {
            final Team team = converter.convert(dto);

            assertThat(team, is(notNullValue()));
            assertThat(team.getId(), is(dto.getTeam_id()));
            assertThat(team.getName(), is(dto.getName()));
            assertThat(team.getCode(), is(dto.getCode()));
            assertThat(team.getLogo(), is(dto.getLogo()));
        }
    }
}