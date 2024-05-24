package id.ac.ui.cs.sofeng.thinktank.service;

import id.ac.ui.cs.sofeng.thinktank.model.Feedback;
import id.ac.ui.cs.sofeng.thinktank.model.Student;
import id.ac.ui.cs.sofeng.thinktank.repository.FeedbackRepository;
import id.ac.ui.cs.sofeng.thinktank.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final StudentRepository studentRepository;

    @Override
    public Feedback addFeedback(Feedback data, MultipartFile document) throws IOException {
        if (document != null && !document.isEmpty()) {
            String userLoggedIn = SecurityContextHolder.getContext().getAuthentication().getName();
            Student student = studentRepository.findByUsername(userLoggedIn);

            String fileName = String.valueOf(data.getStudentId()) + StringUtils.cleanPath(document.getOriginalFilename());
            String uploadDir = "resources/docs/";
            String uploadPath = uploadDir + fileName;
            Path uploadAbsolutePath = Paths.get(uploadPath);
            Files.createDirectories(uploadAbsolutePath.getParent());
            Files.copy(document.getInputStream(), uploadAbsolutePath);



            data.setStudentId(data.getStudentId());
            data.setDocumentPath(fileName);
        }
        return feedbackRepository.save(data);
    }

    @Override
    public void deleteFeedback(String id) {
        feedbackRepository.findByStudentId(id).ifPresent(feedback -> {
            try {
                Files.deleteIfExists(Paths.get(feedback.getDocumentPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            feedbackRepository.deleteByStudentId(id);
        });
    }

    @Override
    public Feedback updateFeedback(Feedback data) {
        return null;
    }

    @Override
    public Feedback getFeedbackByStudentId(String studentId) {
        return feedbackRepository.findByStudentId(studentId).orElse(null);
    }

    public Resource loadFileAsResource(String fileName) throws Exception {
        try {
            Path filePath = Paths.get("resources/docs/").resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new Exception("File not found " + fileName);
            }
        } catch (Exception e) {
            throw new Exception("File not found " + fileName, e);
        }
    }

    @Override
    public List<Student> getStudentsWithoutFeedback() {
        List<String> studentIdsWithFeedback = feedbackRepository.findAll()
                .stream()
                .map(Feedback::getStudentId)
                .collect(Collectors.toList());
        return studentRepository.findAll()
                .stream()
                .filter(student -> !studentIdsWithFeedback.contains(student.getNpm()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }
}
