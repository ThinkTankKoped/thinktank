package id.ac.ui.cs.sofeng.thinktank.repository;

import id.ac.ui.cs.sofeng.thinktank.model.Schedule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    //List<Schedule> findAllByCourse(String course);
    List<Schedule> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT s FROM Schedule s WHERE NOT (s.endTime <= :startTime OR s.startTime >= :endTime)")
    List<Schedule> findOverlappingSchedules(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}
