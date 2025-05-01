package com.example.training.controller;

import com.example.training.model.Exercise;
import com.example.training.model.TrainingPlan;
import com.example.training.model.User;
import com.example.training.service.TrainingPlanService;
import com.example.training.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/plans")
public class TrainingPlanController {
    private final TrainingPlanService trainingPlanService;
    private final UserService userService;

    public TrainingPlanController(TrainingPlanService trainingPlanService, UserService userService) {
        this.trainingPlanService = trainingPlanService;
        this.userService = userService;
    }

    @GetMapping
    public String listPlans(Model model) {
        // Získání přihlášeného uživatele
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // Získání plánů pro uživatele
        List<TrainingPlan> plans = trainingPlanService.getPlansForUser(username);

        // Přidání objektů do modelu
        model.addAttribute("plans", plans);
        model.addAttribute("trainingPlan", new TrainingPlan()); // Pro formulář vytvoření nového plánu

        return "plans";
    }

    @PostMapping
    public String createPlan(@ModelAttribute TrainingPlan trainingPlan) {
        // Získání přihlášeného uživatele
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findByUsername(username);

        // Nastavení uživatele a uložení plánu
        trainingPlan.setUser(user);
        trainingPlanService.savePlan(trainingPlan);

        return "redirect:/plans";
    }

    @GetMapping("/{id}")
    public String viewPlan(@PathVariable Long id, Model model) {
        TrainingPlan plan = trainingPlanService.getPlanById(id);

        // Kontrola, zda plán patří přihlášenému uživateli
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        if (!plan.getUser().getUsername().equals(username)) {
            return "redirect:/plans?error=unauthorized";
        }

        model.addAttribute("plan", plan);
        model.addAttribute("exercise", new Exercise());  // Pro formulář přidání cvičení

        return "plan-detail";
    }
}