package ru.kappers.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.kappers.model.catalog.Team;
import ru.kappers.model.dto.rapidapi.TeamRapidDTO;

@Service
public class TeamRapidDTOTeamConverter implements Converter<TeamRapidDTO, Team> {

    @Override
    public Team convert(TeamRapidDTO source) {
        if (source == null) return null;
        else {
            return Team.builder()
                    .id(source.getTeam_id())
                    .name(source.getName())
                    .code(source.getCode())
                    .logo(source.getLogo())
                    .build();
        }
    }
}