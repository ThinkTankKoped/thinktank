package id.ac.ui.cs.sofeng.thinktank.service;

import id.ac.ui.cs.sofeng.thinktank.model.Assignment;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AssignmentService {

    ResponseEntity<Assignment> findByAssignmentId(String assignmentId);
    List<Assignment> findAllAssignments();
    ResponseEntity<String> deleteByAssignmentId(String assignmentId);
    ResponseEntity<Assignment> updateByAssignmentId(String assignmentId, Assignment data);
    ResponseEntity<Assignment> createNewAssignment(Assignment data);
    List<Assignment> findAllByNpm(String npm);
}
