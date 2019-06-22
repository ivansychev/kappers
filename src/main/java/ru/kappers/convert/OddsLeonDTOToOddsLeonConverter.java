package ru.kappers.convert;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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

@Service
@Slf4j
public class OddsLeonDTOToOddsLeonConverter implements Converter<OddsLeonDTO, OddsLeon> {
    private final CompetitorLeonService competitorService;
    private final LeagueLeonService leagueService;
    private final ConversionService conversionService;

    @Autowired
    public OddsLeonDTOToOddsLeonConverter(CompetitorLeonService competitorService, LeagueLeonService leagueService,
                                          @Lazy ConversionService conversionService) {
        this.competitorService = competitorService;
        this.leagueService = leagueService;
        this.conversionService = conversionService;
    }

    @Nullable
    @Override
    public OddsLeon convert(@Nullable OddsLeonDTO source) {
        if (source == null) {
            return null;
        }
        final List<CompetitorLeonDTO> compDtos = source.getCompetitors();
        CompetitorLeon home = null;
        CompetitorLeon away = null;
        for (CompetitorLeonDTO dto : compDtos) {
            CompetitorLeon comp = competitorService.getByName(dto.getName());
            if (comp == null) {
                comp = competitorService.save(
                        conversionService.convert(dto, CompetitorLeon.class));
            }
            if ("HOME".equalsIgnoreCase(dto.getHomeAway())) {
                home = comp;
            } else {
                away = comp;
            }
        }
        LeagueLeon league = leagueService.getByName(source.getLeague().getName());
        if (league == null) {
            league = leagueService.save(
                    conversionService.convert(source.getLeague(), LeagueLeon.class));
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
                .runners(new ArrayList<>()) // не стоит коллекции делать null, во избежание лишних NPE
                .build();
    }
}