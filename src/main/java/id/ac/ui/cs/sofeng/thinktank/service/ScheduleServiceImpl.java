package id.ac.ui.cs.sofeng.thinktank.service;
import id.ac.ui.cs.sofeng.thinktank.model.Schedule;
import id.ac.ui.cs.sofeng.thinktank.model.ScheduleCell;
import id.ac.ui.cs.sofeng.thinktank.model.ScheduleDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import id.ac.ui.cs.sofeng.thinktank.repository.ScheduleRepository;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Override
    public ScheduleCell[][] findAll(String[] daysOfWeek, LocalTime[] hoursOfDay, String studentId) {

        List<Schedule> schedules = scheduleRepository.findByStudentIdOrderById(studentId);
        List<ScheduleDto> scheduleDtos = new ArrayList<>();
        for (Schedule schedule : schedules) {
            ScheduleDto scheduleDto = ScheduleDto.builder()
                    .day(schedule.getDayOfWeek())
                    .courseName(schedule.getCourseName())
                    .build();

            LocalTime startTime = schedule.getStartTime();
            LocalTime endTime = schedule.getEndTime();
            List<LocalTime> localTimes = new ArrayList<>();
            while (startTime.isBefore(endTime)) {
                localTimes.add(startTime);
                startTime = startTime.plusHours(1);
            }
            localTimes.add(endTime);
            scheduleDto.setHours(localTimes);
            scheduleDtos.add(scheduleDto);
        }


        ScheduleCell[][] studySchedule = new ScheduleCell[hoursOfDay.length][daysOfWeek.length];
        for (int i = 0; i < hoursOfDay.length; i++) {
            for (int j = 0; j < daysOfWeek.length; j++) {
                ScheduleCell schedule = new ScheduleCell();
                schedule.setDay(daysOfWeek[j]);
                schedule.setHour(hoursOfDay[i].toString());
                schedule.setDescription("-");

                scheduleDtos.forEach(c-> {
                    if (c.getDay().equals(DayOfWeek.valueOf(schedule.getDay()))) {
                        c.getHours().forEach(d-> {
                            if (d.toString().equals(schedule.getHour())) {
                                schedule.setDescription(c.getCourseName());
                            }
                        });

                    }
                });
                studySchedule[i][j] = schedule;
            }
        }

        return studySchedule;
    }

    @Override
    public Schedule createSchedule(Schedule schedule) {
        if (schedule.getStudentId() == null) {
            return null;
        }
        List<Schedule> overlaps = scheduleRepository.findByStudentIdAndDayOfWeekAndStartTimeBetween(schedule.getStudentId(), schedule.getDayOfWeek(), schedule.getStartTime(), schedule.getEndTime());
        if (!overlaps.isEmpty() && Objects.isNull(schedule.getId())) {
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

    @Override
    public List<Schedule> getScheduleByStudentId(String studentId) {
        return scheduleRepository.findByStudentIdOrderById(studentId);
    }

    @Override
    public Schedule showUpdateForm(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid schedule Id:" + id));
        return schedule;
    }

    @Override
    public List<LocalTime> getTimeSlot() {
        List<LocalTime> timeSlots = new ArrayList<>();
        LocalTime start = LocalTime.of(0, 0);
        LocalTime end = LocalTime.of(23, 0);
        while (start.isBefore(end)) {
            timeSlots.add(start);
            start = start.plusHours(1);
        }
        timeSlots.add(end);
        return timeSlots;
    }
}