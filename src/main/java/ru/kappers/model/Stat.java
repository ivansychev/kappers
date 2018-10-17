package ru.kappers.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kappers.model.utilmodel.IssueType;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stat")
public class Stat implements Serializable {
    @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    private int id;

    @Column(name = "issue_type")
    private IssueType issueType;

    @Column(name = "content")
    private String content; //JSON формата {что за событие: количественное выражение}

 @OneToOne(cascade = CascadeType.ALL)
 @JoinColumn(name = "u_id")
 @MapsId
 private User user;

}
