package ru.kappers.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.kappers.model.dto.leon.RunnerLeonDTO;
import ru.kappers.model.leonmodels.RunnerLeon;

@Service
public class RunnerLeonDTOToRunnerLeonConverter implements Converter<RunnerLeonDTO, RunnerLeon> {
    @Nullable
    @Override
    public RunnerLeon convert(@Nullable RunnerLeonDTO source) {
        if (source==null)
        return null;
        else{
            return RunnerLeon.builder()
                    .id(source.getId())
                    .name(source.getName())
                    .open(source.isOpen())
                    .price(source.getPrice())
                    .tags(source.getTags())
                    .build();
        }
    }
}
