package id.ac.ui.cs.sofeng.thinktank.service;

import id.ac.ui.cs.sofeng.thinktank.model.Assignment;
import id.ac.ui.cs.sofeng.thinktank.repository.AssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Transactional
public class AssignmentServiceImpl implements AssignmentService {
    private final AssignmentRepository assignmentRepository;

    @Override
    public Assignment addAssignment(Assignment assignment, String npm) {
        assignment.setAssignmentId(generateAssignmentId());
        assignment.setProgress(0);
        assignment.setNpm(npm);
        assignment.setCompleted(false); // Ensure the default value for isCompleted is set to false
        return assignmentRepository.save(assignment);
    }

    @Override
    public Assignment updateAssignment(Assignment assignment) {
        Assignment existingAssignment = assignmentRepository.findByAssignmentId(assignment.getAssignmentId());
        if (existingAssignment == null) {
            throw new IllegalArgumentException("Assignment not found");
        }
        existingAssignment.setTitle(assignment.getTitle());
        existingAssignment.setDescription(assignment.getDescription());
        existingAssignment.setDeadline(assignment.getDeadline());

        // Ensure tasks list is initialized and does not contain null elements
        List<String> tasks = assignment.getTasks() != null ? assignment.getTasks() : new ArrayList<>();
        tasks.removeIf(Objects::isNull); // Remove any null elements
        existingAssignment.setTasks(tasks);

        existingAssignment.setProgress(calculateProgress(existingAssignment));
        existingAssignment.setCompleted(assignment.isCompleted());

        // Ensure study resources list is initialized and does not contain null elements
        List<String> studyResources = assignment.getStudyResources() != null ? assignment.getStudyResources() : new ArrayList<>();
        studyResources.removeIf(Objects::isNull); // Remove any null elements
        existingAssignment.setStudyResources(studyResources);

        return assignmentRepository.save(existingAssignment);
    }


    @Override
    public void deleteAssignment(String assignmentId) {
        Assignment existingAssignment = assignmentRepository.findByAssignmentId(assignmentId);
        if (existingAssignment == null) {
            throw new IllegalArgumentException("Assignment not found");
        }
        assignmentRepository.deleteByAssignmentId(assignmentId);
    }

    @Override
    public Assignment getAssignmentById(String assignmentId) {
        return assignmentRepository.findByAssignmentId(assignmentId);
    }

    @Override
    public List<Assignment> getAllAssignmentsByNpm(String npm) {
        return assignmentRepository.findAllByNpm(npm);
    }

    @Override
    public void markTaskAsComplete(String assignmentId, String task) {
        Assignment assignment = assignmentRepository.findByAssignmentId(assignmentId);
        if (assignment == null) {
            throw new IllegalArgumentException("Assignment not found");
        }
        List<String> tasks = assignment.getTasks();
        int taskIndex = tasks.indexOf(task);
        if (taskIndex == -1) {
            throw new IllegalArgumentException("Task not found in the assignment");
        }
        tasks.set(taskIndex, "COMPLETE: " + task);
        assignment.setProgress(calculateProgress(assignment));
        assignmentRepository.save(assignment);
    }

    @Override
    public void markAssignmentAsComplete(String assignmentId) {
        Assignment assignment = assignmentRepository.findByAssignmentId(assignmentId);
        if (assignment == null) {
            throw new IllegalArgumentException("Assignment not found");
        }
        assignment.setCompleted(true);
        assignmentRepository.save(assignment);
    }

    private String generateAssignmentId() {
        String letters = UUID.randomUUID().toString().substring(0, 3).toUpperCase();
        int numbers = ThreadLocalRandom.current().nextInt(100, 1000);
        return letters + "-" + numbers;
    }

    private int calculateProgress(Assignment assignment) {
        List<String> tasks = assignment.getTasks();

        if (tasks == null || tasks.isEmpty()) {
            return 0;
        }

        int totalTasks = tasks.size();
        int completedTasks = (int) tasks.stream()
                .filter(Objects::nonNull) // Ensure tasks are not null
                .filter(task -> task.startsWith("COMPLETE: "))
                .count();

        return (completedTasks * 100) / totalTasks;
    }
}
