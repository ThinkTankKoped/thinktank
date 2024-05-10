package id.ac.ui.cs.sofeng.thinktank.service;
import id.ac.ui.cs.sofeng.thinktank.model.Schedule;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleService {
    List<Schedule> findAll();
    Schedule createSchedule(Schedule schedule);
    void deleteSchedule(Long id);
    List<Schedule> findSchedulesByTimeRange(LocalDateTime start, LocalDateTime end);
    List<Schedule> findOverlappingSchedules(LocalDateTime startTime, LocalDateTime endTime);
}
