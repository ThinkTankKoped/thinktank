package id.ac.ui.cs.sofeng.thinktank.controller;

import id.ac.ui.cs.sofeng.thinktank.model.Assignment;
import id.ac.ui.cs.sofeng.thinktank.model.Task;
import id.ac.ui.cs.sofeng.thinktank.service.AssignmentService;
import id.ac.ui.cs.sofeng.thinktank.service.TaskService;
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
    private final TaskService taskService;

    @GetMapping("/list")
    public String listAssignments(Model model) {
        List<Assignment> assignments = assignmentService.findAllByNpm("01");
        model.addAttribute("assignments", assignments);
        return "Assignment/assignment_list";
    }

    @GetMapping("/create")
    public String createAssignmentForm(Model model) {
        model.addAttribute("assignment", new Assignment());
        return "Assignment/assignment_create";
    }

    @PostMapping("/create")
    public String createAssignment(@ModelAttribute Assignment assignment) {
        assignment.setNpm("01"); // hardcoded NPM
        assignmentService.createNewAssignment(assignment);
        return "redirect:/assignments/list";
    }

    @GetMapping("/edit/{assignmentId}")
    public String editAssignment(@PathVariable String assignmentId, Model model) {
        Assignment assignment = assignmentService.findByAssignmentId(assignmentId).getBody();
        List<Task> tasks = taskService.findAllByAssignmentId(assignmentId);
        model.addAttribute("assignment", assignment);
        model.addAttribute("tasks", tasks);
        return "Assignment/assignment_edit";
    }

    @PostMapping("/update/{assignmentId}")
    public String updateAssignment(@PathVariable String assignmentId, @ModelAttribute Assignment assignment) {
        assignmentService.updateByAssignmentId(assignmentId, assignment);
        return "redirect:/assignments/list";
    }

    @GetMapping("/delete/{assignmentId}")
    public String deleteAssignment(@PathVariable String assignmentId) {
        assignmentService.deleteByAssignmentId(assignmentId);
        return "redirect:/assignments/list";
    }

    @PostMapping("/task/create/{assignmentId}")
    public String createTask(@PathVariable String assignmentId, @ModelAttribute Task task) {
        Assignment assignment = assignmentService.findByAssignmentId(assignmentId).getBody();
        if (assignment != null) {
            task.setAssignment(assignment);
            taskService.createNewTask(task);
        }
        return "redirect:/assignments/edit/" + assignmentId;
    }

    @GetMapping("/task/delete/{taskId}")
    public String deleteTask(@PathVariable String taskId) {
        Task task = taskService.findByTaskId(taskId).getBody();
        if (task != null) {
            String assignmentId = task.getAssignment().getAssignmentId();
            taskService.deleteByTaskId(taskId);
            return "redirect:/assignments/edit/" + assignmentId;
        }
        return "redirect:/assignments/list";
    }

    @PostMapping("/task/update/{taskId}")
    public String updateTask(@PathVariable String taskId, @ModelAttribute Task task) {
        taskService.updateByTaskId(taskId, task);
        return "redirect:/assignments/list";
    }

    @PostMapping("/task/complete/{taskId}")
    public String completeTask(@PathVariable String taskId) {
        Task task = taskService.findByTaskId(taskId).getBody();
        if (task != null) {
            task.setStatus(true);
            taskService.updateByTaskId(taskId, task);
            return "redirect:/assignments/list";
        }
        return "redirect:/assignments/list";
    }

    @PostMapping("/complete/{assignmentId}")
    public String completeAssignment(@PathVariable String assignmentId) {
        Assignment assignment = assignmentService.findByAssignmentId(assignmentId).getBody();
        if (assignment != null) {
            assignment.setProgress(100);
            assignmentService.updateByAssignmentId(assignmentId, assignment);
            return "redirect:/assignments/list";
        }
        return "redirect:/assignments/list";
    }
}
