package id.ac.ui.cs.sofeng.thinktank.model;

import jakarta.persistence.*;
import lombok.Setter;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "studentId")
    private String studentId;

    @Column(name = "startTime")
    private LocalTime startTime;

    @Column(name = "endTime")
    private LocalTime endTime;

    @Column(name = "courseId")
    private String courseId;

    @Column(name = "courseName")
    private String courseName;

    @Column(name = "dayOfWeek")
    private DayOfWeek dayOfWeek;
}

