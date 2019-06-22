package ru.kappers.convert;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.kappers.model.dto.leon.LeagueLeonDTO;
import ru.kappers.model.leonmodels.LeagueLeon;

import javax.annotation.Nullable;
@Slf4j
@Service
public class LeagueLeonDTOToLeagueLeonConverter implements Converter<LeagueLeonDTO, LeagueLeon> {

    @Nullable
    @Override
    public LeagueLeon convert(@Nullable LeagueLeonDTO source) {
        if (source == null) {
            return null;
        }
        return LeagueLeon.builder()
                .id(source.getId())
                .name(source.getName())
                .url(source.getUrl())
                .sport(source.getSport().getName())
                .build();
    }
}
