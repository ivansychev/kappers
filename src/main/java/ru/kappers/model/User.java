package ru.kappers.model;

import lombok.extern.log4j.Log4j;
import org.hibernate.annotations.NaturalId;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import ru.kappers.service.RolesService;
import ru.kappers.util.RoleUtil;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
    @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    @EqualsAndHashCode.Exclude
    private int userId;

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

    @Column(name = "isblocked")
    private Boolean isblocked;

    @Column(name = "currency")
    private String currency;

    @Column(name = "lang")
    private String lang;

    @Column(name = "role_id")
    private Integer roleId;

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
        return roleId == role_id;
    }

    public boolean hasRole(String roleName){
        return roleName.equals(RoleUtil.getRoleNameById(roleId).getRoleName());
    }


}