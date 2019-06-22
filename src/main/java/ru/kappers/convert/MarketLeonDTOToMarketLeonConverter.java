package ru.kappers.convert;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.kappers.model.dto.leon.MarketLeonDTO;
import ru.kappers.model.leonmodels.MarketLeon;

import javax.annotation.Nullable;

@Slf4j
@Service
public class MarketLeonDTOToMarketLeonConverter implements Converter<MarketLeonDTO, MarketLeon> {
    @Nullable
    @Override
    public MarketLeon convert(@Nullable MarketLeonDTO source) {
        if (source == null) {
            return null;
        }
        return MarketLeon.builder()
                .id(source.getId())
                .name(source.getName())
                .open(source.isOpen())
                .build();
    }
}