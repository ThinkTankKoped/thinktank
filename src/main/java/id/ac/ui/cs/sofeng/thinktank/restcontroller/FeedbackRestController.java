package id.ac.ui.cs.sofeng.thinktank.restcontroller;


import id.ac.ui.cs.sofeng.thinktank.model.Feedback;
import id.ac.ui.cs.sofeng.thinktank.service.FeedbackServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackRestController {

    private final FeedbackServiceImpl feedbackService;

//    @PostMapping("/submitFeedback")
//    public Feedback saveFeedback(@ModelAttribute Feedback feedback, @RequestParam("document") MultipartFile document) throws IOException {
//        if (document != null && !document.isEmpty()) {
//            feedback = feedbackService.addFeedback(feedback, document);
//        }
//        return feedback;
//    }

//    @GetMapping("/deleteFeedback/{id}")
//    public void deleteFeedback(@PathVariable String id) {
//        feedbackService.deleteFeedback(id);
//    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        Resource resource = null;
        try {
            resource = feedbackService.loadFileAsResource(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
