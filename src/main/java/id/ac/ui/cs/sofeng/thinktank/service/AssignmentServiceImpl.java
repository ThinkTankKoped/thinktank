package id.ac.ui.cs.sofeng.thinktank.service;

import id.ac.ui.cs.sofeng.thinktank.model.Assignment;
import id.ac.ui.cs.sofeng.thinktank.repository.AssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentRepository assignmentRepository;

    @Override
    public ResponseEntity<Assignment> findByAssignmentId(String assignmentId) {
        Assignment assignment = assignmentRepository.findByAssignmentId(assignmentId);
        if (assignment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(assignment);
    }

    @Override
    public List<Assignment> findAllAssignments() {
        return assignmentRepository.findAll();
    }

    @Override
    public ResponseEntity<String> deleteByAssignmentId(String assignmentId) {
        if (assignmentRepository.findByAssignmentId(assignmentId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Assignment not found");
        }
        assignmentRepository.deleteByAssignmentId(assignmentId);
        return ResponseEntity.status(HttpStatus.OK).body("Assignment deleted successfully");
    }

    @Override
    public ResponseEntity<Assignment> updateByAssignmentId(String assignmentId, Assignment data) {
        Assignment existingAssignment = assignmentRepository.findByAssignmentId(assignmentId);
        if (existingAssignment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Update assignment data
        existingAssignment.setTitle(data.getTitle());
        existingAssignment.setDescription(data.getDescription());
        existingAssignment.setDeadline(data.getDeadline());
        existingAssignment.setProgress(data.getProgress());
        existingAssignment.setNpm(data.getNpm());

        Assignment updatedAssignment = assignmentRepository.save(existingAssignment);
        return ResponseEntity.status(HttpStatus.OK).body(updatedAssignment);
    }

    @Override
    public ResponseEntity<Assignment> createNewAssignment(Assignment data) {
        String uniqueAssignmentId = generateUniqueAssignmentId();
        data.setAssignmentId(uniqueAssignmentId);
        data.setProgress(0);

        Assignment newAssignment = assignmentRepository.save(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAssignment);
    }

    @Override
    public List<Assignment> findAllByNpm(String npm) {
        return assignmentRepository.findAllByNpm(npm);
    }

    private String generateUniqueAssignmentId() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        while (result.length() < 10) {
            int index = random.nextInt(characters.length());
            result.append(characters.charAt(index));
        }
        return result.toString();
    }
}
