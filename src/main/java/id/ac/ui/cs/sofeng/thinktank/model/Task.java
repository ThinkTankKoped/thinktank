package id.ac.ui.cs.sofeng.thinktank.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private boolean status; // true for complete, false for incomplete

    @Column(name = "task_id", unique = true)
    private String taskId; // Unique identifier for the task

    @ManyToOne
    @JoinColumn(name = "assignment_id")
    private Assignment assignment; // Reference to the assignment
}
