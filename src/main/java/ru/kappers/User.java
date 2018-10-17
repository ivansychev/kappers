package ru.kappers;

import lombok.*;
import lombok.extern.log4j.Log4j;

import javax.persistence.*;

@Log4j
@Entity
@Data
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private int id;

    private String name;

    private String password;

    @Override
    public String toString() {
        return "Пользователь " + name + "с id = "+ id;
    }
}
