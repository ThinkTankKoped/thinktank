package id.ac.ui.cs.sofeng.thinktank.controller;

import id.ac.ui.cs.sofeng.thinktank.model.Schedule;
import id.ac.ui.cs.sofeng.thinktank.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // Get all schedules
    @GetMapping
    public ResponseEntity<List<Schedule>> getAllSchedules() {
        List<Schedule> schedules = scheduleService.findAll();
        if (schedules.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(schedules);
    }

    // Create a new schedule
    @PostMapping
    public ResponseEntity<Schedule> createSchedule(@RequestBody Schedule schedule) {
        try {
            Schedule createdSchedule = scheduleService.createSchedule(schedule);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSchedule);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Update an existing schedule
    @PutMapping("/{id}")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable Long id, @RequestBody Schedule schedule) {
        try {
            schedule.setId(id); // Ensure the correct schedule is updated
            Schedule updatedSchedule = scheduleService.createSchedule(schedule);
            if (updatedSchedule == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updatedSchedule);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Delete a schedule
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        try {
            scheduleService.deleteSchedule(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
