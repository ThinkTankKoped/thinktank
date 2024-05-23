package id.ac.ui.cs.sofeng.thinktank.service;
import id.ac.ui.cs.sofeng.thinktank.model.Dashboard;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import id.ac.ui.cs.sofeng.thinktank.repository.DashboardRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DashboardServiceImpl implements DashboardService{
    private final DashboardRepository dashboardRepository;
//    private final DashboardService dashboardService;
    @Override
    public Dashboard addDashboard(Dashboard dashboard) {
        if (dashboard.getEducatorsname() == null || dashboard.getStudentname() == null) {
            return null;
        }
        Dashboard existingDashboard = dashboardRepository.findByStudentname(dashboard.getStudentname());
        if (existingDashboard != null) {
            // If it does, return null or throw an exception
            return null;
        }
        return dashboardRepository.save(dashboard);
    }

    @Override
    public void removeStudent(long id) {
        if (dashboardRepository.findById(id) == null) {
            return;
        }
        else {
            dashboardRepository.deleteById(id);
        }
    }

    @Override
    public void deleteDashboard(String educatorname) {
        if (dashboardRepository.findAllByEducatorsname(educatorname) == null) {
            return;
        }
        else {
            dashboardRepository.deleteAllByEducatorsname(educatorname);
        }
    }

    @Override
    public List<Dashboard> findDashboardByEducator(String educatorname) {
        return dashboardRepository.findAllByEducatorsname(educatorname);
    }

    @Override
    public Dashboard findDashboardByStudentName(String studentname) {
        return dashboardRepository.findByStudentname(studentname);
    }
}
