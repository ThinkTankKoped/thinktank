package id.ac.ui.cs.sofeng.thinktank.service;


import id.ac.ui.cs.sofeng.thinktank.model.User;
import id.ac.ui.cs.sofeng.thinktank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (!(user == null)) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRole())
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    @Override
    public User createUser(String username, String password) {
        User usernameExist = userRepo.findByUsername(username);

        if (username.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("Please fill all the form");
        }  else if (usernameExist != null) {
            throw new IllegalArgumentException("Username already exist");
        } else {
            User user = new User();

            user.setUsername(username);
            user.setPassword(new BCryptPasswordEncoder().encode(password));
            user.setRole("Student");

            return userRepo.save(user);
        }
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepo.findByUsername(username);
        if(user == null) {
            return null;
        }
        else {
            return user;
        }
    }

    @Override
    public User editUser(User user) {
        // TODO Auto-generated method stub
        return null;
    }
}
