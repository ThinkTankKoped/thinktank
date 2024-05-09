package id.ac.ui.cs.sofeng.thinktank.service;

import id.ac.ui.cs.sofeng.thinktank.model.Task;
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
        if (taskRepository.findByTaskId(taskId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }
        taskRepository.deleteByTaskId(taskId);
        return ResponseEntity.status(HttpStatus.OK).body("Task deleted successfully");
    }

    @Override
    public ResponseEntity<Task> updateByTaskId(String taskId, Task data) {
        Task existingTask = taskRepository.findByTaskId(taskId);
        if (existingTask == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Update task data
        existingTask.setTitle(data.getTitle());
        existingTask.setDescription(data.getDescription());
        existingTask.setStatus(data.isStatus());

        Task updatedTask = taskRepository.save(existingTask);
        return ResponseEntity.status(HttpStatus.OK).body(updatedTask);
    }

    @Override
    public ResponseEntity<Task> createNewTask(Task data) {
        // Generate unique task ID based on assignment ID
        String assignmentId = data.getAssignmentId();
        String taskId = generateUniqueTaskId(assignmentId);
        data.setTaskId(taskId);

        Task savedTask = taskRepository.save(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    @Override
    public List<Task> findAllByAssignmentId(String assignmentId) {
        return taskRepository.findAllByAssignmentId(assignmentId);
    }

    // Method to generate unique task ID based on assignment ID
    private String generateUniqueTaskId(String assignmentId) {
        // Extract the first three characters of the assignment ID to use as prefix
        String assignmentPrefix = assignmentId.substring(0, 3);

        // Find the maximum task number for the given assignment ID prefix
        Integer maxTaskNumber = taskRepository.findMaxTaskNumberByAssignmentIdPrefix(assignmentPrefix);
        if (maxTaskNumber == null) {
            maxTaskNumber = 0;
        }

        // Increment the task number for the new task
        int newTaskNumber = maxTaskNumber + 1;

        // Combine assignment prefix and task number to form the task ID
        String taskId = assignmentPrefix + "task" + newTaskNumber;

        return taskId;
    }
}
