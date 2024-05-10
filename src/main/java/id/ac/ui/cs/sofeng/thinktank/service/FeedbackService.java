package id.ac.ui.cs.sofeng.thinktank.service;

import id.ac.ui.cs.sofeng.thinktank.model.Feedback;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FeedbackService {

    Feedback addFeedback(Feedback data, MultipartFile document) throws IOException;
    void deleteFeedback(int id);
    Feedback updateFeedback(Feedback data);
    Feedback getFeedbackByStudentId(int studentId);
}
