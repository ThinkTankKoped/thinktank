package id.ac.ui.cs.sofeng.thinktank.service;
import id.ac.ui.cs.sofeng.thinktank.model.Schedule;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import id.ac.ui.cs.sofeng.thinktank.repository.ScheduleRepository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Override
    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    @Override
    public Schedule createSchedule(Schedule schedule) {
        if (schedule.getStudentId() == null) {
            return null;
        }
        List<Schedule> overlaps = scheduleRepository.findByStartTimeBetween(schedule.getStartTime(), schedule.getEndTime());
        if (!overlaps.isEmpty()) {
            return null;
        }
        return scheduleRepository.save(schedule);
    }

    @Override
    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }

    @Override
    public List<Schedule> findSchedulesByTimeRange(LocalDateTime start, LocalDateTime end) {
        return scheduleRepository.findByStartTimeBetween(start, end);
    }

    @Override
    public List<Schedule> findOverlappingSchedules(LocalDateTime startTime, LocalDateTime endTime) {
        return scheduleRepository.findOverlappingSchedules(startTime, endTime);
    }
}