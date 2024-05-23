package id.ac.ui.cs.sofeng.thinktank.controller;

import id.ac.ui.cs.sofeng.thinktank.model.Student;
import id.ac.ui.cs.sofeng.thinktank.model.User;
import id.ac.ui.cs.sofeng.thinktank.repository.StudentRepository;
import id.ac.ui.cs.sofeng.thinktank.repository.UserRepository;
import id.ac.ui.cs.sofeng.thinktank.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.authentication.AnonymousAuthenticationToken;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepo;
    private final UserServiceImpl userService;
    private final StudentRepository studentRepo;

    @GetMapping("/")
    public String redirectToHomePage() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {
            User user = userRepo.findByUsername(auth.getName());
            Student student = studentRepo.findByUsername(auth.getName());

            if (user.getRole().equals("Educator")) {
                return "main-admin";
            }
            if (user.getRole().equals("Student") && student == null) {
                return "student/studentForm";
            }else{
                return "study/listSchedule";
            }
        }
        return "redirect:/home";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "sign-in-basic";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "sign-up-basic";
    }

    @GetMapping("/home")
    public String homePage() {
        return "main-product";
    }

    @GetMapping("/logout")
    public String customLogout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password, RedirectAttributes ra) {
        try {
            userService.createUser(username, password);
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            ra.addAttribute("error", e.getMessage());
            return "redirect:/register";
        }
    }
}
