package id.ac.ui.cs.sofeng.thinktank.service;
import id.ac.ui.cs.sofeng.thinktank.model.Student;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StudentService {
    void deleteStudent(String name);
    Student findStudent(String name);
    Student createNewStudent(Student data);
    List<Student> getAllStudent();
}
