package id.ac.ui.cs.sofeng.thinktank.repository;

import id.ac.ui.cs.sofeng.thinktank.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    Task findByTaskId(String taskId);
    String deleteByTaskId(String taskId);
    List<Task> findAllByAssignmentId(String assignmentId);

    @Query("SELECT MAX(CAST(SUBSTRING(t.taskId, 4) AS int)) FROM Task t WHERE t.taskId LIKE CONCAT(:assignmentIdPrefix, '%')")
    Integer findMaxTaskNumberByAssignmentIdPrefix(String assignmentIdPrefix);

}