package id.ac.ui.cs.sofeng.thinktank.repository;

import id.ac.ui.cs.sofeng.thinktank.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {
    Assignment findByAssignmentId(String assignmentId);
    void deleteByAssignmentId(String assignmentId);
    List<Assignment> findAllByNpm(String npm);
}
