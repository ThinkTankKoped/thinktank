package id.ac.ui.cs.sofeng.thinktank.controller;

import id.ac.ui.cs.sofeng.thinktank.model.Schedule;
import id.ac.ui.cs.sofeng.thinktank.model.ScheduleCell;
import id.ac.ui.cs.sofeng.thinktank.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;

@Controller
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/student/{id}/detail")
    public String showDetailSchedules(@PathVariable String id, Model model) {
        model.addAttribute("schedules", scheduleService.getScheduleByStudentId(id));
        model.addAttribute("id", id);
        return "study/listSchedule";
    }

    @GetMapping("/student/{id}")
    public String showSchedules(@PathVariable String id, Model model) {
        String[] daysOfWeek = {
                DayOfWeek.SUNDAY.name(),
                DayOfWeek.MONDAY.name(),
                DayOfWeek.TUESDAY.name(),
                DayOfWeek.WEDNESDAY.name(),
                DayOfWeek.THURSDAY.name(),
                DayOfWeek.FRIDAY.name(),
                DayOfWeek.SATURDAY.name()};

        LocalTime[] hoursOfDay = {
                LocalTime.of(00, 00),
                LocalTime.of(1, 00),
                LocalTime.of(2, 00),
                LocalTime.of(3, 00),
                LocalTime.of(4, 00),
                LocalTime.of(5, 00),
                LocalTime.of(6, 00),
                LocalTime.of(7, 00),
                LocalTime.of(8, 00),
                LocalTime.of(9, 00),
                LocalTime.of(10, 00),
                LocalTime.of(11, 00),
                LocalTime.of(12, 00),
                LocalTime.of(13, 00),
                LocalTime.of(14, 00),
                LocalTime.of(15, 00),
                LocalTime.of(16, 00),
                LocalTime.of(17, 00),
                LocalTime.of(18, 0),
                LocalTime.of(19, 00),
                LocalTime.of(20, 00),
                LocalTime.of(21, 00),
                LocalTime.of(22, 00),
                LocalTime.of(23, 00)
        };

        String[] hoursOfDayAsString = new String[hoursOfDay.length];
        for (int i = 0; i < hoursOfDay.length; i++) {
            hoursOfDayAsString[i] = hoursOfDay[i].toString();
        }

        ScheduleCell[][] studySchedule = scheduleService.findAll(daysOfWeek, hoursOfDay, id);

        model.addAttribute("daysOfWeek", Arrays.asList(daysOfWeek));
        model.addAttribute("hoursOfDay", Arrays.asList(hoursOfDayAsString));
        model.addAttribute("studySchedule", studySchedule);
        model.addAttribute("id", id);
        return "study_schedule";
    }

    @GetMapping("/new")
    public String showStudyScheduleForm(@RequestParam String studentId,  Model model) {
        model.addAttribute("schedule", new Schedule());
        model.addAttribute("studentId", studentId);
        model.addAttribute("timeSlots", scheduleService.getTimeSlot());
        return "study/formSchedule";
    }

    @PostMapping("/save")
    public String createSchedule(@RequestParam String studentId, @ModelAttribute("schedule") Schedule schedule) {
        schedule.setStudentId(studentId);
        scheduleService.createSchedule(schedule);
        return "redirect:/schedules/student/" + schedule.getStudentId() + "/detail";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Schedule schedule = scheduleService.showUpdateForm(id);
        model.addAttribute("schedule", schedule);
        model.addAttribute("studentId", schedule.getStudentId());
        model.addAttribute("timeSlots", scheduleService.getTimeSlot());
        return "study/formSchedule";
    }


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
    @GetMapping("/delete/{id}")
    public String deleteSchedule(@PathVariable Long id,
                                 @RequestParam("studentId") String studentId) {
        scheduleService.deleteSchedule(id);
        return "redirect:/schedules/student/" + studentId + "/detail";
    }
}
