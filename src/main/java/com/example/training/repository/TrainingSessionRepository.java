package com.example.training.repository;

import com.example.training.model.TrainingPlan;
import com.example.training.model.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {
    List<TrainingSession> findByTrainingPlan(TrainingPlan plan);
}