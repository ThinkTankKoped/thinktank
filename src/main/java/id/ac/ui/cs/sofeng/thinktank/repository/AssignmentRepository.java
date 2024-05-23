package id.ac.ui.cs.sofeng.thinktank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import id.ac.ui.cs.sofeng.thinktank.model.Assignment;
import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {
    List<Assignment> findAllByNpm(String npm);
    Assignment findByAssignmentId(String assignmentId);
    void deleteByAssignmentId(String assignmentId);
}
