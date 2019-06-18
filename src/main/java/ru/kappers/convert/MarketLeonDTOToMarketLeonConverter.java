package ru.kappers.convert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.kappers.model.dto.leon.MarketLeonDTO;
import ru.kappers.model.dto.leon.RunnerLeonDTO;
import ru.kappers.model.leonmodels.MarketLeon;
import ru.kappers.model.leonmodels.RunnerLeon;

import javax.annotation.Nullable;
import java.util.stream.Collectors;

@Service
public class MarketLeonDTOToMarketLeonConverter implements Converter<MarketLeonDTO, MarketLeon> {
private Converter <RunnerLeonDTO, RunnerLeon> converter = new RunnerLeonDTOToRunnerLeonConverter();

    @Nullable
    @Override
    public MarketLeon convert(@Nullable MarketLeonDTO source) {
        if (source == null)
            return null;
        else {
            return MarketLeon.builder()
                    .id(source.getId())
                    .name(source.getName())
                    .open(source.isOpen())
                    .runners(source.getRunners().stream().map(s -> converter.convert(s)).collect(Collectors.toList()))
                    .build();
        }

    }
}
