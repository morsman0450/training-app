package com.example.training.controller;

import com.example.training.model.User;
import com.example.training.repository.UserRepository;
import com.example.training.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final UserRepository userRepository;

    public AuthController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        // Kontrola, zda uživatelské jméno již existuje
        if (userRepository.existsByUsername(user.getUsername())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Uživatelské jméno již existuje");
            return "redirect:/auth/register?error=true";
        }

        // Registrace uživatele (včetně hashování hesla)
        userService.registerUser(user);
        redirectAttributes.addFlashAttribute("successMessage", "Registrace byla úspěšná. Nyní se můžete přihlásit.");
        return "redirect:/login";
    }
}