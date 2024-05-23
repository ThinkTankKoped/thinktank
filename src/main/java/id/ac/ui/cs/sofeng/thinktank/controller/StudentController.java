package id.ac.ui.cs.sofeng.thinktank.controller;
import id.ac.ui.cs.sofeng.thinktank.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import id.ac.ui.cs.sofeng.thinktank.model.Student;
import java.util.List;
@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @GetMapping("/studentform")
    public String form(Model model) {
        Student student = new Student();
        model.addAttribute("student", student);
        return "student/studentform";
    }
    @PostMapping("/studentform")
    public String formSubmit(@ModelAttribute Student student) {
        Student student1 = new Student();
        student1.setUsername(student.getUsername());
        student1.setNpm(student.getNpm());
        student1.setGrades(student.getGrades());
        student1.setAttendance(student.getAttendance());
        student1.setProgress(student.getProgress());
        studentService.createNewStudent(student);
        return "redirect:/student/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Student> students = studentService.getAllStudent();
        model.addAttribute("students", students);
        return "student/studentList";
    }
}
