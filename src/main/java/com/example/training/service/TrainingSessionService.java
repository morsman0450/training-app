package com.example.training.service;

import com.example.training.model.TrainingPlan;
import com.example.training.model.TrainingSession;
import com.example.training.repository.TrainingSessionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TrainingSessionService {
    private final TrainingSessionRepository trainingSessionRepository;

    public TrainingSessionService(TrainingSessionRepository trainingSessionRepository) {
        this.trainingSessionRepository = trainingSessionRepository;
    }

    public TrainingSession addSessionToPlan(TrainingSession session, TrainingPlan plan) {
        session.setTrainingPlan(plan);
        return trainingSessionRepository.save(session);
    }

    public List<TrainingSession> getSessionsForPlan(TrainingPlan plan) {
        return trainingSessionRepository.findByTrainingPlan(plan);
    }
}