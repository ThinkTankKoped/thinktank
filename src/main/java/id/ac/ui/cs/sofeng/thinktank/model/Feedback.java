package id.ac.ui.cs.sofeng.thinktank.model;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Entity
@Table(name = "tbl_feedback")
public class Feedback {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "message")
    private String message;

    @Column(name = "document")
    private String documentPath;

    @Column(name = "studentid", unique = true)
    private String studentId;
}
