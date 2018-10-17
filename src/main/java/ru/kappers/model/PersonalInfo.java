package ru.kappers.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.persistence.*;
import java.io.Serializable;

@Log4j
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "personal_info")
public class PersonalInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, insertable = true, updatable = false)
    private int id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "u_id")
    @MapsId
    private User user;
    /**
     * текстовое представление аватарки в Base64
     * */
    @Column(name = "photo")
    private String photo;

    @Column(name = "about")

    private String about;

}
