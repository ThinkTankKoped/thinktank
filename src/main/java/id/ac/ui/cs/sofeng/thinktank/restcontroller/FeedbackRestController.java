package id.ac.ui.cs.sofeng.thinktank.restcontroller;


import id.ac.ui.cs.sofeng.thinktank.model.Feedback;
import id.ac.ui.cs.sofeng.thinktank.service.FeedbackServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackRestController {

    private final FeedbackServiceImpl feedbackService;

    @PostMapping("/submitFeedback")
    public Feedback saveFeedback(@ModelAttribute Feedback feedback, @RequestParam("document") MultipartFile document) throws IOException {
        if (document != null && !document.isEmpty()) {
            // Handle file upload and save
            feedback = feedbackService.addFeedback(feedback, document);
        }
        return feedback;
    }
}
