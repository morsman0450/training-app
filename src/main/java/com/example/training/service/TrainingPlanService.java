package com.example.training.service;

import com.example.training.model.TrainingPlan;
import com.example.training.model.User;
import com.example.training.repository.TrainingPlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingPlanService {
    private final TrainingPlanRepository trainingPlanRepository;
    private final UserService userService;

    public TrainingPlanService(TrainingPlanRepository trainingPlanRepository, UserService userService) {
        this.trainingPlanRepository = trainingPlanRepository;
        this.userService = userService;
    }

    public List<TrainingPlan> getPlansForUser(String username) {
        User user = userService.findByUsername(username);
        return trainingPlanRepository.findByUser(user);
    }

    public TrainingPlan getPlanById(Long id) {
        return trainingPlanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tréninkový plán s ID " + id + " nebyl nalezen"));
    }

    public TrainingPlan savePlan(TrainingPlan plan) {
        return trainingPlanRepository.save(plan);
    }

    public void deletePlan(Long id) {
        trainingPlanRepository.deleteById(id);
    }
}