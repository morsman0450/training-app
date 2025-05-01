package com.example.training.controller;

import com.example.training.model.Exercise;
import com.example.training.model.TrainingPlan;
import com.example.training.service.ExerciseService;
import com.example.training.service.TrainingPlanService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/exercises")
public class ExerciseController {
    private final ExerciseService exerciseService;
    private final TrainingPlanService planService;

    public ExerciseController(ExerciseService exerciseService, TrainingPlanService planService) {
        this.exerciseService = exerciseService;
        this.planService = planService;
    }

    @PostMapping("/{planId}")
    public String addExerciseToPlan(@PathVariable Long planId, @ModelAttribute Exercise newExercise) {
        TrainingPlan plan = planService.getPlanById(planId);
        exerciseService.addExerciseToPlan(newExercise, plan);
        return "redirect:/plans/" + planId;
    }

    @PostMapping("/{id}/delete")
    public String deleteExercise(@PathVariable Long id, @RequestParam Long planId) {
        exerciseService.deleteExercise(id);
        return "redirect:/plans/" + planId;
    }
}