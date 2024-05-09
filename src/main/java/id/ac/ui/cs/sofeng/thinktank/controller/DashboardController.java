package id.ac.ui.cs.sofeng.thinktank.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import id.ac.ui.cs.sofeng.thinktank.service.DashboardService;
import id.ac.ui.cs.sofeng.thinktank.model.Dashboard;
import java.util.List;
@Controller
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;
    @GetMapping("/dashboard")
    public String dashboardPage(Model model) {
        List <Dashboard> dashboard = dashboardService.findDashboardByEducator("john_doe");
        model.addAttribute("dashboard", dashboard);
        return "dashboard";
    }
}
