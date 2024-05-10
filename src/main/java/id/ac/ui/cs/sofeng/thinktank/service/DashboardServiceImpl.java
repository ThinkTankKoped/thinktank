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
        else if (dashboardRepository.findByEducatorsname(dashboard.getEducatorsname()) != null) {
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
        if (dashboardRepository.findByEducatorsname(educatorname) == null) {
            return;
        }
        else {
            dashboardRepository.deleteByEducatorsname(educatorname);
        }
    }

    @Override
    public List<Dashboard> findDashboardByEducator(String educatorname) {
        return dashboardRepository.findAllByEducatorsname(educatorname);
    }
}
