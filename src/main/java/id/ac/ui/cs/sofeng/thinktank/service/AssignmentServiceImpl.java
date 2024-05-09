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
        // Generate unique assignmentId
        String assignmentId = generateUniqueAssignmentId();
        data.setAssignmentId(assignmentId);

        Assignment savedAssignment = assignmentRepository.save(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAssignment);
    }

    @Override
    public List<Assignment> findAllByNpm(String npm) {
        return assignmentRepository.findAllByNpm(npm);
    }

    // Method to generate unique assignmentId
    private String generateUniqueAssignmentId() {
        Random random = new Random();
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";

        StringBuilder assignmentId = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            assignmentId.append(letters.charAt(random.nextInt(letters.length())));
        }
        for (int i = 0; i < 3; i++) {
            assignmentId.append(numbers.charAt(random.nextInt(numbers.length())));
        }

        // Check if assignmentId already exists, generate a new one if needed
        while (assignmentRepository.findByAssignmentId(assignmentId.toString()) != null) {
            for (int i = 0; i < 3; i++) {
                assignmentId.setCharAt(i, letters.charAt(random.nextInt(letters.length())));
            }
            for (int i = 3; i < 6; i++) {
                assignmentId.setCharAt(i, numbers.charAt(random.nextInt(numbers.length())));
            }
        }

        return assignmentId.toString();
    }
}