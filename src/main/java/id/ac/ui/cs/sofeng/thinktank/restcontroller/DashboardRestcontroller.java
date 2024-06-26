package id.ac.ui.cs.sofeng.thinktank.restcontroller;
import java.util.List;

import id.ac.ui.cs.sofeng.thinktank.model.Student;
import id.ac.ui.cs.sofeng.thinktank.repository.StudentRepository;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import id.ac.ui.cs.sofeng.thinktank.model.Dashboard;
import id.ac.ui.cs.sofeng.thinktank.service.DashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequestMapping("/dashboardmain")
@RequiredArgsConstructor

public class DashboardRestcontroller {
    private final DashboardService dashboardService;

    @GetMapping("/list/{educatorname}")
    public List<Dashboard> findDashboardByEducator(@PathVariable String educatorname){
        return dashboardService.findDashboardByEducator(educatorname);
    }
}
