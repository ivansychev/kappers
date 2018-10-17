package ru.kappers.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "history")
public class History implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, insertable = true, updatable = false)
    private int id;

    @Column(name = "changes")
    private String changes;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "u_id")
    @MapsId
    private User user;
}
