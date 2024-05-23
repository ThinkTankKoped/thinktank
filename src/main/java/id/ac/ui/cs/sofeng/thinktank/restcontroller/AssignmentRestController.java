package id.ac.ui.cs.sofeng.thinktank.restcontroller;

import id.ac.ui.cs.sofeng.thinktank.model.Assignment;
import id.ac.ui.cs.sofeng.thinktank.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/assignments")
@RequiredArgsConstructor
public class AssignmentRestController {

    private final AssignmentService assignmentService;

    @PostMapping("/add")
    public Assignment addAssignment(@RequestBody Assignment assignment, @RequestParam String npm) {
        return assignmentService.addAssignment(assignment, npm);
    }

    @PutMapping("/update")
    public Assignment updateAssignment(@RequestBody Assignment assignment) {
        return assignmentService.updateAssignment(assignment);
    }

    @DeleteMapping("/delete/{assignmentId}")
    public void deleteAssignment(@PathVariable String assignmentId) {
        assignmentService.deleteAssignment(assignmentId);
    }

    @GetMapping("/get/{assignmentId}")
    public Assignment getAssignmentById(@PathVariable String assignmentId) {
        return assignmentService.getAssignmentById(assignmentId);
    }

    @GetMapping("/all")
    public List<Assignment> getAllAssignments(@RequestParam String npm) {
        return assignmentService.getAllAssignmentsByNpm(npm);
    }

    @PutMapping("/complete-task/{assignmentId}")
    public void markTaskAsComplete(@PathVariable String assignmentId, @RequestParam String task) {
        assignmentService.markTaskAsComplete(assignmentId, task);
    }

    @GetMapping("/all/{npm}")
    public List<Assignment> getAllAssignmentsByNpm(@PathVariable String npm) {
        return assignmentService.getAllAssignmentsByNpm(npm);
    }
}
