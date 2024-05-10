package id.ac.ui.cs.sofeng.thinktank.controller;

import id.ac.ui.cs.sofeng.thinktank.model.Feedback;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FeedbackController {

    @GetMapping("/feedback")
    public String feedbackPage(Model model) {

        Feedback feedback = new Feedback();
        model.addAttribute("feedback", feedback);

        return "create-feedback";
    }
}
