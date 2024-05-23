package id.ac.ui.cs.sofeng.thinktank.service;
import id.ac.ui.cs.sofeng.thinktank.model.Schedule;
import id.ac.ui.cs.sofeng.thinktank.model.ScheduleCell;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleService {
    ScheduleCell[][] findAll(String[] daysOfWeek, LocalTime[] hoursOfDay, String studentId);
    Schedule createSchedule(Schedule schedule);
    void deleteSchedule(Long id);
    List<Schedule> findSchedulesByTimeRange(LocalDateTime start, LocalDateTime end);
    List<Schedule> findOverlappingSchedules(LocalDateTime startTime, LocalDateTime endTime);

    List<Schedule> getScheduleByStudentId(String studentId);

    Schedule showUpdateForm(Long id);

    List<LocalTime> getTimeSlot();
}
