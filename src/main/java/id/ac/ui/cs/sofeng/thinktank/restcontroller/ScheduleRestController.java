package id.ac.ui.cs.sofeng.thinktank.restcontroller;

import java.time.LocalDateTime;
import java.util.List;

import id.ac.ui.cs.sofeng.thinktank.model.Schedule;
import id.ac.ui.cs.sofeng.thinktank.service.ScheduleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleRestController {

    private final ScheduleService scheduleService;

//    @GetMapping
//    public List<Schedule> getAllSchedules() {
//        return scheduleService.findAll();
//    }

    @PostMapping
    public ResponseEntity<?> createSchedule(@RequestBody Schedule schedule) {
        Schedule createdSchedule = scheduleService.createSchedule(schedule);
        if (createdSchedule == null) {
            return ResponseEntity.badRequest().body("Invalid schedule data or conflicts detected.");
        }
        return ResponseEntity.ok(createdSchedule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSchedule(@PathVariable Long id) {
        try {
            scheduleService.deleteSchedule(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete schedule: " + e.getMessage());
        }
    }

    @GetMapping("/by-time-range")
    public List<Schedule> getSchedulesByTimeRange(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                  @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return scheduleService.findSchedulesByTimeRange(start, end);
    }

    @GetMapping("/overlapping")
    public ResponseEntity<List<Schedule>> getOverlappingSchedules(@RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
                                                                  @RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        List<Schedule> overlappingSchedules = scheduleService.findOverlappingSchedules(startTime, endTime);
        if (overlappingSchedules.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(overlappingSchedules);
    }
}
