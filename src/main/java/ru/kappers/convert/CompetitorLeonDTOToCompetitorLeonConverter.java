package ru.kappers.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.kappers.model.dto.leon.CompetitorLeonDTO;
import ru.kappers.model.leonmodels.CompetitorLeon;

import javax.annotation.Nullable;

@Service
public class CompetitorLeonDTOToCompetitorLeonConverter implements Converter<CompetitorLeonDTO, CompetitorLeon> {
    @Nullable
    @Override
    public CompetitorLeon convert(@Nullable CompetitorLeonDTO source) {
        if (source == null) {
            return null;
        }
        return CompetitorLeon.builder()
                .id(source.getId())
                .logo(source.getLogo())
                .name(source.getName())
                .build();
    }
}
