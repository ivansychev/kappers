package ru.kappers.model;

import lombok.*;
import lombok.extern.log4j.Log4j;
import org.hibernate.annotations.NaturalId;
import ru.kappers.model.utilmodel.Outcomes;

import javax.persistence.*;

@Log4j
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    int id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "u_id")
    @MapsId
    User kapper;
    @Column(name = "outcome")
    Outcomes outcome;
    @Column(name = "coefficient")
    double coefficient;
    @Column(name = "tokens")
    int tokens;
    @Column(name = "price")
    double price;

}
