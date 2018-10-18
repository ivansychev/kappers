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
    @Column(name = "role_name", nullable = false, unique=true)
    private RoleType roleName;

    public enum RoleType {
        ROLE_ADMIN(1), ROLE_USER(2), ROLE_KAPPER(3);
        int id;
        RoleType(int id){
            this.id = id;
        }
        public int getId(){
            return id;
        }
    }
}
