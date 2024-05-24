package id.ac.ui.cs.sofeng.thinktank.controller;
import id.ac.ui.cs.sofeng.thinktank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import id.ac.ui.cs.sofeng.thinktank.service.DashboardService;
import id.ac.ui.cs.sofeng.thinktank.model.Dashboard;

import java.util.ArrayList;
import java.util.List;
import id.ac.ui.cs.sofeng.thinktank.service.StudentService;
import id.ac.ui.cs.sofeng.thinktank.model.Student;

@Controller
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/dashboardmain")
    public String dashboardPage(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String role = userRepository.findByUsername(username).getRole();
        if (role.equals("Educator")) {


            List<Dashboard> dashboard = dashboardService.findDashboardByEducator(username);
            List<Student> students = new ArrayList<>();
            for (Dashboard dash : dashboard) {
                String val1 = dash.getStudentname();
                students.add(studentService.findStudent(val1));
            }
            model.addAttribute("dashboard", dashboard);
            model.addAttribute("students", students);
            return "dashboardmain";
        } else {
            return "access-denied-assignment";
        }
    }

    @GetMapping("dashboardmain/delete")
    public String deleteDashboard(Model model) {
        String educatorname = SecurityContextHolder.getContext().getAuthentication().getName();
        dashboardService.deleteDashboard(educatorname);
        return "redirect:/dashboardmain";
    }

    @PostMapping("dashboardmain/add")
    public String addDashboard(@RequestParam String studentname, @RequestParam String educatorname){
        Dashboard dashboard = new Dashboard();
        dashboard.setStudentname(studentname);
        dashboard.setEducatorsname(educatorname);
        dashboardService.addDashboard(dashboard);
        return "redirect:/dashboardmain";
    }
    @GetMapping("dashboardmain/remove/{id}")
    public String removeStudent(@PathVariable long id){
        dashboardService.removeStudent(id);
        return "redirect:/dashboardmain";
    }
}