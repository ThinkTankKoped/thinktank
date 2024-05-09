package id.ac.ui.cs.sofeng.thinktank.restcontroller;

import id.ac.ui.cs.sofeng.thinktank.model.Task;
import id.ac.ui.cs.sofeng.thinktank.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskRestController {

    private final TaskService taskService;

    @GetMapping("/list")
    public List<Task> findAllTasks() {
        return taskService.findAllTasks();
    }

    @GetMapping("/search/{taskId}")
    public ResponseEntity<Task> findByTaskId(@PathVariable String taskId) {
        return taskService.findByTaskId(taskId);
    }

    @PostMapping("/create")
    public ResponseEntity<Task> createNewTask(@RequestBody Task data) {
        return taskService.createNewTask(data);
    }

    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<String> deleteTaskByTaskId(@PathVariable String taskId) {
        return taskService.deleteByTaskId(taskId);
    }

    @PutMapping("/update/{taskId}")
    public ResponseEntity<Task> updateTaskByTaskId(@PathVariable String taskId, @RequestBody Task data) {
        return taskService.updateByTaskId(taskId, data);
    }

    @GetMapping("/search/assignment/{assignmentId}")
    public List<Task> findAllByAssignmentId(@PathVariable String assignmentId) {
        return taskService.findAllByAssignmentId(assignmentId);
    }
}
