package id.ac.ui.cs.sofeng.thinktank.repository;
import org.springframework.stereotype.Repository;
import id.ac.ui.cs.sofeng.thinktank.model.Dashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
@Repository
public interface DashboardRepository extends JpaRepository<Dashboard, Long>{
    List<Dashboard> findAllByEducatorsname(String educatorsname);
    Dashboard findByStudentname(String studentname);
    Dashboard findByEducatorsname(String educatorsname);
    void deleteAllByEducatorsname(String educatorsname);
}
