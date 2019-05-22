package ru.kappers.model.leonmodels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Slf4j
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sport_leon")
public class SportLeon {
    @Id
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    private long id;
    @Column(name = "name")
    @Size(max = 255)
    @NotBlank
    private String name;
    @Column(name = "betline_name")
    @Size(max = 255)
    private String betlineName;
    @Column(name = "betline_combination")
    @Size(max = 255)
    private String betlineCombination;
    @OneToMany(mappedBy = "sport")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private LeagueLeon leagues;
}
