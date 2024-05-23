package id.ac.ui.cs.sofeng.thinktank.repository;

import id.ac.ui.cs.sofeng.thinktank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
