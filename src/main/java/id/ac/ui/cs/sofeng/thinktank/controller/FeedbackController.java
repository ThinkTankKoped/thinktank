package id.ac.ui.cs.sofeng.thinktank.controller;

import id.ac.ui.cs.sofeng.thinktank.model.Feedback;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    @GetMapping("/create")
    public String feedbackPage(Model model) {

        Feedback feedback = new Feedback();
        model.addAttribute("feedback", feedback);

        return "feedback/create-feedback";
    }
}
