package id.ac.ui.cs.sofeng.thinktank.model;
import jakarta.persistence.*;
import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
@Entity
@Table(name = "tbl_dashboard")
public class Dashboard {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "studentname")
    private String studentname;

    @Column(name = "educatorsname")
    private String educatorsname;

}
