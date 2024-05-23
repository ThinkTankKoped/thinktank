package id.ac.ui.cs.sofeng.thinktank.service;
import id.ac.ui.cs.sofeng.thinktank.model.Dashboard;
import java.util.List;
public interface DashboardService {
    Dashboard addDashboard(Dashboard dashboard);
    void removeStudent(long id);
    void deleteDashboard(String educatorname);
    List<Dashboard> findDashboardByEducator(String educatorname);
    Dashboard findDashboardByStudentName(String studentname);
}
