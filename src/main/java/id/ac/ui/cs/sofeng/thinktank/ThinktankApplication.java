package id.ac.ui.cs.sofeng.thinktank;

import id.ac.ui.cs.sofeng.thinktank.model.User;
import id.ac.ui.cs.sofeng.thinktank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class ThinktankApplication implements CommandLineRunner {
    private final UserRepository userRepo;
    private final BCryptPasswordEncoder passEncoder;

    public static void main(String[] args) {
        SpringApplication.run(ThinktankApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        if (userRepo.findByUsername("Educator") == null) {
            User user = new User();

            user.setUsername("Educator");
            user.setPassword(passEncoder.encode("admin123"));
            user.setRole("Educator");

            userRepo.save(user);
        }

    }
}
