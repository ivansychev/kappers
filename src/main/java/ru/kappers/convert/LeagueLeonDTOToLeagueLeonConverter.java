package ru.kappers.convert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.kappers.model.dto.leon.LeagueLeonDTO;
import ru.kappers.model.leonmodels.LeagueLeon;
import ru.kappers.model.leonmodels.SportLeon;

import javax.annotation.Nullable;

@Service
public class LeagueLeonDTOToLeagueLeonConverter implements Converter<LeagueLeonDTO, LeagueLeon> {
    private ConversionService conversionService;

    @Autowired
    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }
    @Nullable
    @Override
    public LeagueLeon convert(@Nullable LeagueLeonDTO source) {
        if (source == null)
            return null;
        else {
            return LeagueLeon.builder()
                    .id(source.getId())
                    .name(source.getName())
                    .url(source.getUrl())
                    .sport(conversionService.convert(source.getSport(), SportLeon.class))
                    .build();
        }
    }
}
