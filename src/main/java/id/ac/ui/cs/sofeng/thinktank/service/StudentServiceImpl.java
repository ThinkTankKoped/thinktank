package id.ac.ui.cs.sofeng.thinktank.service;
import id.ac.ui.cs.sofeng.thinktank.model.Student;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import id.ac.ui.cs.sofeng.thinktank.repository.StudentRepository;
import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService{
    private final StudentRepository studentRepository;

    @Override
    public void deleteStudent(String name) {
        if (studentRepository.findByName(name) == null) {
            return;
        }
        else {
            studentRepository.deleteByName(name);
        }
    }

    @Override
    public Student findStudent(String name) {
        return studentRepository.findByName(name);
    }

    @Override
    public Student createNewStudent(Student data) {
        if (data.getName() == null || data.getNpm() == null || data.getGrades() == 0) {
            return null;
        }
        else if (studentRepository.findByName(data.getName()) != null) {
            return null;
        }
        return studentRepository.save(data);
    }

    @Override
    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }
}
