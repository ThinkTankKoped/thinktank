package id.ac.ui.cs.sofeng.thinktank.service;

import id.ac.ui.cs.sofeng.thinktank.model.Feedback;
import id.ac.ui.cs.sofeng.thinktank.model.Student;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FeedbackService {

    Feedback addFeedback(Feedback data, MultipartFile document) throws IOException;
    void deleteFeedback(String id);
    Feedback updateFeedback(Feedback data);
    Feedback getFeedbackByStudentId(String studentId);
    Resource loadFileAsResource(String fileName) throws Exception;

    List<Feedback> getAllFeedback();
    List<Student> getStudentsWithoutFeedback();
}
