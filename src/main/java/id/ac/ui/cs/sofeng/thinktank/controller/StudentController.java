package id.ac.ui.cs.sofeng.thinktank.controller;
import id.ac.ui.cs.sofeng.thinktank.repository.UserRepository;
import id.ac.ui.cs.sofeng.thinktank.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/studentform")
    public String form(Model model) {
        String studentName = SecurityContextHolder.getContext().getAuthentication().getName();
        String role = userRepository.findByUsername(studentName).getRole();
        if (role.equals("Educator")) {
            return "dashboardmain";
        } else{
        Student student = new Student();
        model.addAttribute("student", student);
        return "student/studentform";
    }
    }
    @PostMapping("/studentform")
    public String formSubmit(@ModelAttribute Student student) {
        String studentName = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student1 = new Student();
        if(studentService.findStudent(student.getUsername()) != null && studentService.findStudent(student.getNpm()) != null && student.getUsername().equals(studentName)){
            student1.setUsername(student.getUsername());
            student1.setNpm(student.getNpm());
            if(student.getGrades() == 0.0f){
                student1.setGrades(0);
            }
            else{
                student1.setGrades(student.getGrades());
            }
            if (student.getAttendance() == 0){
                student1.setAttendance(0);
            }
            else{
                student1.setAttendance(student.getAttendance());
            }
            if (student.getProgress() == 0){
                student1.setProgress(0);
            }
            else{
                student1.setProgress(student.getProgress());
            }
            studentService.createNewStudent(student);
            return "study/listSchedule";
        }
        else{
            return "redirect:/student/studentform";
        }
    }

    @GetMapping("/list")
    public String list(Model model) {
        String educatorsname = SecurityContextHolder.getContext().getAuthentication().getName();
        String role = userRepository.findByUsername(educatorsname).getRole();
        if (role.equals("Educator")) {
        List<Student> students = studentService.getAllStudent();
        model.addAttribute("educatorsname", educatorsname);
        model.addAttribute("students", students);
        return "student/studentList";
    }else {
        return "study/listSchedule";
    }   }
}
