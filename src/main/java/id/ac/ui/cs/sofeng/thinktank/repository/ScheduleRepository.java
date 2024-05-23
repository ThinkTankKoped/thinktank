package id.ac.ui.cs.sofeng.thinktank.repository;

import id.ac.ui.cs.sofeng.thinktank.model.Schedule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    //List<Schedule> findAllByCourse(String course);
    List<Schedule> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT s FROM Schedule s WHERE NOT (s.endTime <= :startTime OR s.startTime >= :endTime)")
    List<Schedule> findOverlappingSchedules(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    List<Schedule> findByStudentIdOrderById(String studentId);

    List<Schedule> findByStudentIdAndDayOfWeekAndStartTimeBetween(
            String studentId, DayOfWeek dayOfWeek, LocalTime start, LocalTime end);
}
