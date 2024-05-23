package id.ac.ui.cs.sofeng.thinktank.model;
import jakarta.persistence.*;
import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
@Entity
@Table(name = "tbl_student")
public class Student {

        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @Column(name = "name")
        private String name;

        @Column(name = "NPM")
        private String npm;

        @Column(name = "grades")
        private float grades;

        @Column(name = "attendance")
        private int attendance;

        @Column(name = "progress")
        private int progress;
}
