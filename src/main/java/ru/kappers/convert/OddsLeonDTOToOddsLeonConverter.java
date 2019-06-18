package ru.kappers.convert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.kappers.model.dto.leon.*;
import ru.kappers.model.leonmodels.*;
import ru.kappers.service.CompetitorLeonService;
import ru.kappers.service.LeagueLeonService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OddsLeonDTOToOddsLeonConverter implements Converter<OddsLeonDTO, OddsLeon> {
    private Converter<LeagueLeonDTO, LeagueLeon> leagueConverter = new LeagueLeonDTOToLeagueLeonConverter();
    private Converter<CompetitorLeonDTO, CompetitorLeon> competitorConverter = new CompetitorLeonDTOToCompetitorLeonConverter();
    private Converter<RunnerLeonDTO, RunnerLeon> runnerConverter = new RunnerLeonDTOToRunnerLeonConverter();

    private final CompetitorLeonService competitorService;
    private final LeagueLeonService leagueService;

    public OddsLeonDTOToOddsLeonConverter(CompetitorLeonService competitorService, LeagueLeonService leagueService) {
        this.competitorService = competitorService;
        this.leagueService = leagueService;
    }

    @Nullable
    @Override
    public OddsLeon convert(@Nullable OddsLeonDTO source) {
        if (source == null) {
            return null;
        }
        List<CompetitorLeonDTO> compDtos = source.getCompetitors();
        CompetitorLeon home = null;
        CompetitorLeon away = null;
        for (CompetitorLeonDTO dto:compDtos) {
            CompetitorLeon comp = competitorService.getByName(dto.getName());
            if (comp==null){
                comp = competitorConverter.convert(dto);
                competitorService.save(comp);
            }
            if (dto.getHomeAway().equals("HOME")){
                home = comp;
            } else{
                away = comp;
            }
        }
        LeagueLeon league = leagueService.getByName(source.getLeague().getName());
        if (league==null){
            league = leagueConverter.convert(source.getLeague());
            leagueService.save(league);
        }

        return OddsLeon.builder()
                .id(source.getId())
                .name(source.getName())
                .kickoff(new Timestamp(source.getKickoff()))
                .open(source.isOpen())
                .url(source.getUrl())
                .lastUpdated(new Timestamp(source.getLastUpdated()))
                .league(league)
                .home(home)
                .away(away)
                .build();

    }
}

