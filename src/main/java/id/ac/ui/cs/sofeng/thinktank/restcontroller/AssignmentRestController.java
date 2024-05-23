package id.ac.ui.cs.sofeng.thinktank.restcontroller;

import id.ac.ui.cs.sofeng.thinktank.model.Assignment;
import id.ac.ui.cs.sofeng.thinktank.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/assignments")
@RequiredArgsConstructor
public class AssignmentRestController {

    private final AssignmentService assignmentService;

    @GetMapping("/list")
    public List<Assignment> findAllAssignments() {
        return assignmentService.findAllAssignments();
    }

    @GetMapping("/search/{assignmentId}")
    public ResponseEntity<Assignment> findByAssignmentId(@PathVariable String assignmentId) {
        return assignmentService.findByAssignmentId(assignmentId);
    }

    @PostMapping("/create")
    public ResponseEntity<Assignment> createNewAssignment(@RequestBody Assignment data) {
        return assignmentService.createNewAssignment(data);
    }

    @DeleteMapping("/delete/{assignmentId}")
    public ResponseEntity<String> deleteAssignmentByAssignmentId(@PathVariable String assignmentId) {
        return assignmentService.deleteByAssignmentId(assignmentId);
    }

    @PutMapping("/update/{assignmentId}")
    public ResponseEntity<Assignment> updateAssignmentByAssignmentId(@PathVariable String assignmentId, @RequestBody Assignment data) {
        return assignmentService.updateByAssignmentId(assignmentId, data);
    }

    @GetMapping("/search/npm/{npm}")
    public List<Assignment> findAllByNpm(@PathVariable String npm) {
        return assignmentService.findAllByNpm(npm);
    }
}
