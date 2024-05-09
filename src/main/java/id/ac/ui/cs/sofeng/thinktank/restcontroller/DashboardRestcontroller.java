package id.ac.ui.cs.sofeng.thinktank.restcontroller;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import id.ac.ui.cs.sofeng.thinktank.model.Dashboard;
import id.ac.ui.cs.sofeng.thinktank.service.DashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequestMapping("/dashboard")
@RequiredArgsConstructor

public class DashboardRestcontroller {
    private final DashboardService dashboardService;

    @GetMapping("/list/{educatorname}")
    public List<Dashboard> findDashboardByEducator(@PathVariable String educatorname){
        return dashboardService.findDashboardByEducator(educatorname);
    }
    @PostMapping("/add")
    public Dashboard addDashboard(@RequestBody Dashboard dashboard){
        return dashboardService.addDashboard(dashboard);
    }
    @DeleteMapping("/delete/{educatorname}")
    public void deleteDashboard(@PathVariable String educatorname){
        dashboardService.deleteDashboard(educatorname);
    }
    @DeleteMapping("/remove/{id}")
    public void removeStudent(@PathVariable long id){
        dashboardService.removeStudent(id);
    }
}
