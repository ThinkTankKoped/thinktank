package id.ac.ui.cs.sofeng.thinktank.service;
import id.ac.ui.cs.sofeng.thinktank.model.Student;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StudentService {
    void deleteStudent(String username);
    Student findStudent(String username);
    Student createNewStudent(Student data);
    List<Student> getAllStudent();
}
