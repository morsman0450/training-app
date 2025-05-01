package com.example.training.service;

import com.example.training.model.Exercise;
import com.example.training.model.TrainingPlan;
import com.example.training.repository.ExerciseRepository;
import org.springframework.stereotype.Service;

@Service
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;

    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public Exercise addExerciseToPlan(Exercise exercise, TrainingPlan plan) {
        exercise.setTrainingPlan(plan);
        return exerciseRepository.save(exercise);
    }

    public void deleteExercise(Long id) {
        exerciseRepository.deleteById(id);
    }
}