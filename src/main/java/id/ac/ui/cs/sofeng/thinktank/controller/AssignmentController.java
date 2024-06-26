package id.ac.ui.cs.sofeng.thinktank.controller;

import id.ac.ui.cs.sofeng.thinktank.model.Assignment;
import id.ac.ui.cs.sofeng.thinktank.model.Student;
import id.ac.ui.cs.sofeng.thinktank.repository.StudentRepository;
import id.ac.ui.cs.sofeng.thinktank.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import id.ac.ui.cs.sofeng.thinktank.model.User;
import id.ac.ui.cs.sofeng.thinktank.repository.UserRepository;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    @GetMapping("/main")
    public String mainPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username);
        String role = user.getRole();

        if ("Educator".equals(role)) {
            return "redirect:/assignments/access-denied-assignment"; //
        } else if ("Student".equals(role)) {
            Student studentData = studentRepository.findByUsername(username);
            String npm = studentData.getNpm();

            List<Assignment> assignments = assignmentService.getAllAssignmentsByNpm(npm);
            model.addAttribute("assignments", assignments);
            return "Assignment/main";
        }


        return "access-denied-assignment";
    }

    @GetMapping("/access-denied-assignment")
    public String accessDeniedAssignment() {
        return "access-denied-assignment"; // Remove the leading slash
    }


    @GetMapping("/create")
    public String createPage(Model model) {
        model.addAttribute("assignment", new Assignment());
        return "Assignment/create";
    }

    @PostMapping("/create")
    public String createAssignment(@ModelAttribute Assignment assignment) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student studentData = studentRepository.findByUsername(auth.getName());
        String npm = studentData.getNpm();
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
        model.addAttribute("studyResources", assignment.getStudyResources());
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

    @GetMapping("/complete/{assignmentId}")
    public String completeAssignment(@PathVariable String assignmentId) {
        assignmentService.markAssignmentAsComplete(assignmentId);
        return "redirect:/assignments/main";
    }
}
