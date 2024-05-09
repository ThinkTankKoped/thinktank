package id.ac.ui.cs.sofeng.thinktank.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "assignments")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "NPM")
    private String npm;

    @Column(name = "description")
    private String description;

    @Column(name = "deadline")
    private String deadline;

    @Column(name = "progress")
    private int progress;

    @Column(name = "assignment_id", unique = true)
    private String assignmentId; // Foreign key to reference the assignment
}

