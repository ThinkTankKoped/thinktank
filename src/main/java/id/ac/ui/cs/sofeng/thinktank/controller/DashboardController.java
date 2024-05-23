package id.ac.ui.cs.sofeng.thinktank.controller;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping("/dashboardmain")
    public String dashboardPage(Model model) {
        List <Dashboard> dashboard = dashboardService.findDashboardByEducator("john_doe");
        List <Student> students = new ArrayList<>();
        for (Dashboard dash : dashboard) {
            String val1 = dash.getStudentname();
            students.add(studentService.findStudent(val1));
        }
        model.addAttribute("dashboard", dashboard);
        model.addAttribute("students", students);
        return "dashboardmain";
    }

    @GetMapping("dashboardmain/delete/{educatorname}")
    public String deleteDashboard(@PathVariable String educatorname) {
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