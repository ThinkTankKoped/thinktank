package id.ac.ui.cs.sofeng.thinktank.service;

import id.ac.ui.cs.sofeng.thinktank.model.Feedback;
import id.ac.ui.cs.sofeng.thinktank.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final ResourceLoader resourceLoader;

    @Override
    public Feedback addFeedback(Feedback data, MultipartFile document) throws IOException {
        if (document != null && !document.isEmpty()) {

            String fileName = StringUtils.cleanPath(document.getOriginalFilename());

            String uploadDir = "resources/docs/";
            String uploadPath = uploadDir + fileName;
            Path uploadAbsolutePath = Paths.get(uploadPath);
            Files.createDirectories(uploadAbsolutePath.getParent());
            Files.copy(document.getInputStream(), uploadAbsolutePath);


            data.setDocumentPath(uploadPath);
        }
        return feedbackRepository.save(data);
    }

    @Override
    public void deleteFeedback(int id) {
        feedbackRepository.findById(id).ifPresent(feedback -> {
            try {
                Files.deleteIfExists(Paths.get(feedback.getDocumentPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            feedbackRepository.deleteById(id);
        });
    }

    @Override
    public Feedback updateFeedback(Feedback data) {
        return null;
    }

    @Override
    public Feedback getFeedbackByStudentId(int studentId) {
        return null;
    }
}
