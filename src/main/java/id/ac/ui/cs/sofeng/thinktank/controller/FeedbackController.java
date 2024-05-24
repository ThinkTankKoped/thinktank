package id.ac.ui.cs.sofeng.thinktank.controller;

import id.ac.ui.cs.sofeng.thinktank.model.Feedback;
import id.ac.ui.cs.sofeng.thinktank.model.Student;
import id.ac.ui.cs.sofeng.thinktank.repository.StudentRepository;
import id.ac.ui.cs.sofeng.thinktank.repository.UserRepository;
import id.ac.ui.cs.sofeng.thinktank.service.FeedbackServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackServiceImpl feedbackService;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    @GetMapping("/create/{npm}")
    public String feedbackPage(Model model, @PathVariable("npm") String npm) {

        String userLoggedIn = SecurityContextHolder.getContext().getAuthentication().getName();
        String role = userRepository.findByUsername(userLoggedIn).getRole();

        if(role.equals("Educator")){
            Feedback feedback = new Feedback();
            model.addAttribute("feedback", feedback);
            model.addAttribute("npm", npm);

            return "feedback/create-feedback";
        }else{
            return "redirect:/feedback/list";
        }

    }

    @PostMapping("/submitFeedback")
    public String saveFeedback(@ModelAttribute Feedback feedback, @RequestParam("document") MultipartFile document) throws IOException {
        if (document != null && !document.isEmpty()) {
            feedbackService.addFeedback(feedback, document);
        }
        return "redirect:/feedback/list";
    }

    @GetMapping("/list")
    public String feedbackList(Model model) {

        String userLoggedIn = SecurityContextHolder.getContext().getAuthentication().getName();
        String role = userRepository.findByUsername(userLoggedIn).getRole();
        Student studentData = studentRepository.findByUsername(userLoggedIn);

        if(role.equals("Educator")){
            List<Feedback> feedback = feedbackService.getAllFeedback();
            model.addAttribute("feedbacks", feedback);

            return "feedback/feedback-list";
        }
        else if(role.equals("Student")){
            Feedback feedback = feedbackService.getFeedbackByStudentId(studentData.getNpm());
            model.addAttribute("feedbacks", feedback);

            return "feedback/feedback-list";
        }
        return "student/studentForm";
    }

    @GetMapping("/students/without-feedback")
    public String getStudentsWithoutFeedback(Model model) {
        List<Student> studentsWithoutFeedback = feedbackService.getStudentsWithoutFeedback();
        model.addAttribute("students", studentsWithoutFeedback);
        return "feedback/studentWithoutFeedback";
    }
}
