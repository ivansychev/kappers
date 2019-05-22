package ru.kappers.convert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.kappers.model.dto.leon.MarketLeonDTO;
import ru.kappers.model.leonmodels.MarketLeon;
import ru.kappers.model.leonmodels.RunnerLeon;

import javax.annotation.Nullable;
import java.util.stream.Collectors;

@Service
public class MarketLeonDTOToMarketLeonConverter implements Converter<MarketLeonDTO, MarketLeon> {
    private ConversionService conversionService;

    @Autowired
    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Nullable
    @Override
    public MarketLeon convert(@Nullable MarketLeonDTO source) {
        if (source == null)
            return null;
        else {
            return MarketLeon.builder()
                    .id(source.getId())
                    .name(source.getName())
                    .family(source.getFamily())
                    .open(source.isOpen())
                    .runners(source.getRunners().stream().map(s -> conversionService.convert(s, RunnerLeon.class)).collect(Collectors.toList()))
                    .build();
        }

    }
}
