package com.example.training.controller;

import com.example.training.model.TrainingPlan;
import com.example.training.model.TrainingSession;
import com.example.training.service.TrainingPlanService;
import com.example.training.service.TrainingSessionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sessions")
public class TrainingSessionController {
    private final TrainingSessionService sessionService;
    private final TrainingPlanService planService;

    public TrainingSessionController(TrainingSessionService sessionService, TrainingPlanService planService) {
        this.sessionService = sessionService;
        this.planService = planService;
    }

    @PostMapping("/{planId}")
    public String addSessionToPlan(@PathVariable Long planId, @ModelAttribute TrainingSession newSession) {
        TrainingPlan plan = planService.getPlanById(planId);
        sessionService.addSessionToPlan(newSession, plan);
        return "redirect:/plans/" + planId;
    }
}