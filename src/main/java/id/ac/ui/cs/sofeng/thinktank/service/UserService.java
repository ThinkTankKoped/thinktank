package id.ac.ui.cs.sofeng.thinktank.service;

import id.ac.ui.cs.sofeng.thinktank.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    User createUser(String username, String password);
    User findByUsername(String username);
    User editUser(User user);
}
