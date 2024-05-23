package id.ac.ui.cs.sofeng.thinktank.service;

import id.ac.ui.cs.sofeng.thinktank.model.Assignment;
import java.util.List;

public interface AssignmentService {
    Assignment addAssignment(Assignment assignment, String npm);
    Assignment updateAssignment(Assignment assignment);
    void deleteAssignment(String assignmentId);
    Assignment getAssignmentById(String assignmentId);
    List<Assignment> getAllAssignmentsByNpm(String npm);
    void markTaskAsComplete(String assignmentId, String task);
}
