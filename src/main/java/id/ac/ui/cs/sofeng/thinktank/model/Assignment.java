package id.ac.ui.cs.sofeng.thinktank.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "tbl_assignment")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "npm")
    private String npm;

    @Column(name = "description")
    private String description;

    @Column(name = "deadline")
    private String deadline;

    @Column(name = "progress")
    private int progress;

    @Column(name = "assignment_id", unique = true)
    private String assignmentId;

    @ElementCollection
    @Column(name = "tasks")
    private List<String> tasks;

    @Column(name = "is_completed", nullable = false)
    private boolean isCompleted = false; // Default value
}
