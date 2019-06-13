package ru.kappers.convert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.kappers.model.dto.leon.CompetitorLeonDTO;
import ru.kappers.model.dto.leon.LeagueLeonDTO;
import ru.kappers.model.dto.leon.MarketLeonDTO;
import ru.kappers.model.dto.leon.OddsLeonDTO;
import ru.kappers.model.leonmodels.CompetitorLeon;
import ru.kappers.model.leonmodels.LeagueLeon;
import ru.kappers.model.leonmodels.MarketLeon;
import ru.kappers.model.leonmodels.OddsLeon;

import java.sql.Timestamp;
import java.util.stream.Collectors;

@Service
public class OddsLeonDTOToOddsLeonConverter implements Converter<OddsLeonDTO, OddsLeon> {
    private Converter<LeagueLeonDTO, LeagueLeon> leagueConverter = new LeagueLeonDTOToLeagueLeonConverter();
    private Converter<CompetitorLeonDTO, CompetitorLeon> competitorConverter = new CompetitorLeonDTOToCompetitorLeonConverter();
    private Converter<MarketLeonDTO, MarketLeon> marketConverter = new MarketLeonDTOToMarketLeonConverter();

    @Nullable
    @Override
    public OddsLeon convert(@Nullable OddsLeonDTO source) {
        if (source == null) {
            return null;
        }
        return OddsLeon.builder()
                .id(source.getId())
                .name(source.getName())
                .kickoff(new Timestamp(source.getKickoff()))
                .open(source.isOpen())
                .url(source.getUrl())
                .lastUpdated(new Timestamp(source.getLastUpdated()))
                .league(leagueConverter.convert(source.getLeague()))
                .competitors(source.getCompetitors().stream().map(s -> competitorConverter.convert(s)).collect(Collectors.toList()))
                .markets(source.getMarkets() != null ? source.getMarkets().stream().map(m -> marketConverter.convert(m)).collect(Collectors.toList()) : null)
                .build();

    }
}

