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
        if (source==null)
        return null;
        else{
           return CompetitorLeon.builder()
                   .id(source.getId())
                   .homeAway(source.getHomeAway())
                   .logo(source.getLogo())
                   .name(source.getName())
                   .type(source.getType())
                   .build();
        }
    }
}
