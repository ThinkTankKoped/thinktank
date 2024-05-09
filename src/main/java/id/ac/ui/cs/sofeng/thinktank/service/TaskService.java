package id.ac.ui.cs.sofeng.thinktank.service;

import id.ac.ui.cs.sofeng.thinktank.model.Task;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TaskService {

    ResponseEntity<Task> findByTaskId(String taskId);
    List<Task> findAllTasks();
    ResponseEntity<String> deleteByTaskId(String taskId);
    ResponseEntity<Task> updateByTaskId(String taskId, Task data);
    ResponseEntity<Task> createNewTask(Task data);
    List<Task> findAllByAssignmentId(String assignmentId);
}
