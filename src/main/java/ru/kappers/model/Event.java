package ru.kappers.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import ru.kappers.model.utilmodel.Outcomes;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * JPA-сущность Заведенное событие
 */
@Slf4j
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events")
public class Event implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    private int id;
    @ManyToOne
    @JoinColumn(name = "u_id")
    private User kapper;
    @ManyToOne
    @JoinColumn(name = "f_id")
    private Fixture fixture;
    @Column(name = "outcome")
    private Outcomes outcome;
    @Column(name = "coefficient")
    private BigDecimal coefficient;
    @Column(name = "tokens")
    private int tokens;
    @Column(name = "price")
    private BigDecimal price;
    @Column (name="isclosed")
    private boolean isClosed;

}
