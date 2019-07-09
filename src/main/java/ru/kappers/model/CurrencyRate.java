package ru.kappers.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Slf4j
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "currency_rate")
public class CurrencyRate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    private int id;
    @Column(name="date")
    private Date date;
    @Column(name="charcode")
    private String charCode;
    @Column(name="numcode")
    private String numCode;
    @Column(name="name")
    private String name;
    @Column(name="value")
    private BigDecimal value;
    @Column(name="nominal")
    private int nominal;


}
