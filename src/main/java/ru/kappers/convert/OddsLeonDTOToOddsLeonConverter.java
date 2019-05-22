package ru.kappers.convert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.kappers.model.dto.leon.OddsLeonDTO;
import ru.kappers.model.leonmodels.CompetitorLeon;
import ru.kappers.model.leonmodels.LeagueLeon;
import ru.kappers.model.leonmodels.MarketLeon;
import ru.kappers.model.leonmodels.OddsLeon;
import java.util.stream.Collectors;

@Service
public class OddsLeonDTOToOddsLeonConverter implements Converter<OddsLeonDTO, OddsLeon> {
    private ConversionService conversionService;

    @Autowired
    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }
    @Nullable
    @Override
    public OddsLeon convert(@Nullable OddsLeonDTO source) {
        if (source == null) {
            return null;
        }
       return OddsLeon.builder()
                .id(source.getId())
                .name(source.getName())
                .kickoff(source.getKickoff())
                .open(source.isOpen())
                .url(source.getUrl())
                .lastUpdated(source.getLastUpdated())
                .league(conversionService.convert(source.getLeague(), LeagueLeon.class))
                .competitors(source.getCompetitors().stream().map(s-> conversionService.convert(s, CompetitorLeon.class)).collect(Collectors.toList()))
                .markets(source.getCompetitors().stream().map(s-> conversionService.convert(s, MarketLeon.class)).collect(Collectors.toList()))
                .build();

    }
}

