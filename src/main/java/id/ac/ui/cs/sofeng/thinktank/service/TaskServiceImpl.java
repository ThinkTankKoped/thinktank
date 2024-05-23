package id.ac.ui.cs.sofeng.thinktank.service;

import id.ac.ui.cs.sofeng.thinktank.model.Assignment;
import id.ac.ui.cs.sofeng.thinktank.model.Task;
import id.ac.ui.cs.sofeng.thinktank.repository.AssignmentRepository;
import id.ac.ui.cs.sofeng.thinktank.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final AssignmentRepository assignmentRepository;

    @Override
    public ResponseEntity<Task> findByTaskId(String taskId) {
        Task task = taskRepository.findByTaskId(taskId);
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(task);
    }

    @Override
    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public ResponseEntity<String> deleteByTaskId(String taskId) {
        Task task = taskRepository.findByTaskId(taskId);
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }
        Assignment assignment = task.getAssignment();
        taskRepository.deleteByTaskId(taskId);

        // Update assignment progress
        updateAssignmentProgress(assignment);

        return ResponseEntity.status(HttpStatus.OK).body("Task deleted successfully");
    }

    @Override
    public ResponseEntity<Task> updateByTaskId(String taskId, Task data) {
        Task existingTask = taskRepository.findByTaskId(taskId);
        if (existingTask == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        existingTask.setTitle(data.getTitle());
        existingTask.setDescription(data.getDescription());
        existingTask.setStatus(data.isStatus());

        Task updatedTask = taskRepository.save(existingTask);

        // Update assignment progress
        Assignment assignment = existingTask.getAssignment();
        updateAssignmentProgress(assignment);

        return ResponseEntity.status(HttpStatus.OK).body(updatedTask);
    }

    @Override
    public ResponseEntity<Task> createNewTask(Task data) {
        Assignment assignment = assignmentRepository.findByAssignmentId(data.getAssignment().getAssignmentId());
        if (assignment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        data.setAssignment(assignment);
        String uniqueTaskId = generateUniqueTaskId(assignment.getAssignmentId());
        data.setTaskId(uniqueTaskId);

        Task newTask = taskRepository.save(data);

        // Update assignment progress
        updateAssignmentProgress(assignment);

        return ResponseEntity.status(HttpStatus.CREATED).body(newTask);
    }

    @Override
    public List<Task> findAllByAssignmentId(String assignmentId) {
        return taskRepository.findAllByAssignmentAssignmentId(assignmentId);
    }

    private void updateAssignmentProgress(Assignment assignment) {
        List<Task> tasks = taskRepository.findAllByAssignmentAssignmentId(assignment.getAssignmentId());
        long completedTasks = tasks.stream().filter(Task::isStatus).count();
        int progress = (int) ((double) completedTasks / tasks.size() * 100);
        assignment.setProgress(progress);
        assignmentRepository.save(assignment);
    }

    private String generateUniqueTaskId(String assignmentIdPrefix) {
        Integer maxTaskNumber = taskRepository.findMaxTaskNumberByAssignmentIdPrefix(assignmentIdPrefix);
        int taskNumber = (maxTaskNumber == null) ? 1 : maxTaskNumber + 1;
        return assignmentIdPrefix + "task" + taskNumber;
    }
}
