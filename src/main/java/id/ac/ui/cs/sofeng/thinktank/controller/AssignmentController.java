package id.ac.ui.cs.sofeng.thinktank.controller;

import id.ac.ui.cs.sofeng.thinktank.model.Assignment;
import id.ac.ui.cs.sofeng.thinktank.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;

    @GetMapping("/main")
    public String mainPage(Model model) {
        String npm = "1234567890"; // Hardcoded for testing
        List<Assignment> assignments = assignmentService.getAllAssignmentsByNpm(npm);
        model.addAttribute("assignments", assignments);
        return "Assignment/main";
    }

    @GetMapping("/create")
    public String createPage(Model model) {
        model.addAttribute("assignment", new Assignment());
        return "Assignment/create";
    }

    @PostMapping("/create")
    public String createAssignment(@ModelAttribute Assignment assignment) {
        String npm = "1234567890"; // Hardcoded for testing
        assignmentService.addAssignment(assignment, npm);
        return "redirect:/assignments/main";
    }

    @GetMapping("/edit/{assignmentId}")
    public String editPage(@PathVariable String assignmentId, Model model) {
        Assignment assignment = assignmentService.getAssignmentById(assignmentId);
        model.addAttribute("assignment", assignment);
        return "Assignment/edit";
    }

    @PostMapping("/edit")
    public String updateAssignment(@ModelAttribute Assignment assignment) {
        assignmentService.updateAssignment(assignment);
        return "redirect:/assignments/main";
    }

    @GetMapping("/view/{assignmentId}")
    public String viewPage(@PathVariable String assignmentId, Model model) {
        Assignment assignment = assignmentService.getAssignmentById(assignmentId);
        model.addAttribute("assignment", assignment);
        model.addAttribute("tasks", assignment.getTasks());
        return "Assignment/view";
    }

    @GetMapping("/delete/{assignmentId}")
    public String deleteAssignment(@PathVariable String assignmentId) {
        assignmentService.deleteAssignment(assignmentId);
        return "redirect:/assignments/main";
    }

    @GetMapping("/complete-task/{assignmentId}")
    public String completeTask(@PathVariable String assignmentId, @RequestParam String task) {
        assignmentService.markTaskAsComplete(assignmentId, task);
        return "redirect:/assignments/view/" + assignmentId;
    }
}
