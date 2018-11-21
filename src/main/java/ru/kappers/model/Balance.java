package ru.kappers.model;

import lombok.*;
import lombok.extern.log4j.Log4j;

import javax.persistence.*;

@Log4j
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "balance")
@ToString
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    private int id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "u_id")
    @MapsId
    private User user;
    @Column(name = "balance")
    private double balance;
    @Column(name = "currency")
    private String currency;
}
