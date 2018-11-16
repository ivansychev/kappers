package ru.kappers.model;

import lombok.extern.log4j.Log4j;
import org.hibernate.annotations.NaturalId;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Log4j
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    private int id;

    @NaturalId
    @Column(name = "user_name")
  //  @NotEmpty(message = "*Please provide user name")
    private String userName;

    @Column(name = "password")
 //   @Length(min = 5, message = "*Your password must have at least 5 characters")
  //  @NotEmpty(message = "*Please provide your password")
    private String password;

    @Column(name = "name")
    private String name;
    @Column(name = "email")
//    @Email(message = "*Please provide a valid Email")
 //   @NotEmpty(message = "*Please provide an email")
    private String email;

    @Column(name = "date_of_birth")
    private Timestamp dateOfBirth;

    @Column(name = "date_of_registration")
    private Timestamp dateOfRegistration;

    @Column(name = "isblocked", nullable = false)
    private boolean isblocked;

    @Column(name = "currency")
    private String currency;

    @Column(name = "lang")
    private String lang;

    /** Роль */
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

//
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinTable(name = "stat", joinColumns = @JoinColumn(name = "user_id"))
//    private List<Stat> stat;


//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinTable(name = "personal_info", joinColumns = @JoinColumn(name = "user_id"))
//    private PersonalInfo personalInfo;

//    @OneToMany(cascade = CascadeType.ALL,  fetch = FetchType.LAZY)
//    @JoinTable(name = "history", joinColumns = @JoinColumn(name = "player_id"))
//    private Set<History> entity;

    public boolean hasRole(int role_id) {
        return role.getId() == role_id;
    }

    public boolean hasRole(String roleName){
        return role.getName().equals(roleName);
    }


}