package ru.kappers.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.kappers.model.dto.leon.SportLeonDTO;
import ru.kappers.model.leonmodels.SportLeon;

@Service
public class SportLeonDTOToSportLeonConverter implements Converter<SportLeonDTO, SportLeon> {
    @Nullable
    @Override
    public SportLeon convert(@Nullable SportLeonDTO source) {
        if (source==null)
        return null;

        else{
            return SportLeon.builder()
                    .id(source.getId())
                    .name(source.getName())
                    .betlineName(source.getBetline().getName())
                    .betlineCombination(source.getBetline().getCombination())
                    .build();
        }
    }
}
