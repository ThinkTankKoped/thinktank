package id.ac.ui.cs.sofeng.thinktank.repository;
import org.springframework.stereotype.Repository;
import id.ac.ui.cs.sofeng.thinktank.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{
    Student findByName(String name);
    void deleteByName(String name);
    List<Student> findAll();
}
