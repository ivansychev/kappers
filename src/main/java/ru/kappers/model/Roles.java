package ru.kappers.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.hibernate.Session;
//import service.db.HibernateSessionFactory;

import javax.persistence.*;
import java.io.Serializable;

@Log4j
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Roles implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id", nullable = false, insertable = true, updatable = false)
    private int roleId;
    @Column(name = "role_name")
    private String roleName;

    public enum RoleTypes {
        ADMIN, KAPPER, USER
    }

    public static Roles of(RoleTypes type) {
//        Session session = HibernateSessionFactory.getSession();
//        switch (type) {
//            case ADMIN:
//                return session.load(Roles.class, 1);
//            case USER:
//                return session.load(Roles.class, 2);
//            case KAPPER:
//                return session.load(Roles.class, 3);
//            default:
//                return null;
       return null;
    }

}
