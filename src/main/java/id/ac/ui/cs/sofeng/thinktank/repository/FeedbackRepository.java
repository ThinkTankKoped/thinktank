package id.ac.ui.cs.sofeng.thinktank.repository;

import id.ac.ui.cs.sofeng.thinktank.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer>{

    Optional<Feedback> findByStudentId(String studentId);
    void deleteByStudentId(String studentId);
}
