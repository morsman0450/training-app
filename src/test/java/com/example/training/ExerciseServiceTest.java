package com.example.training;

import com.example.training.model.Exercise;
import com.example.training.model.TrainingPlan;
import com.example.training.repository.ExerciseRepository;
import com.example.training.service.ExerciseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ExerciseServiceTest {

    @Mock
    private ExerciseRepository exerciseRepository;

    @InjectMocks
    private ExerciseService exerciseService;

    private Exercise exercise;
    private TrainingPlan trainingPlan;

    @BeforeEach
    void setUp() {
        // Inicializace mock objektů
        MockitoAnnotations.openMocks(this);

        // Přednastavení mockovaných entit
        trainingPlan = new TrainingPlan();
        exercise = new Exercise();
        exercise.setId(1L);
        exercise.setName("Push-up");
    }

    @Test
    void testAddExerciseToPlan() {
        // Mockování, že cvičení bude uloženo
        when(exerciseRepository.save(exercise)).thenReturn(exercise);

        // Volání metody a ověření výsledku
        Exercise result = exerciseService.addExerciseToPlan(exercise, trainingPlan);

        // Ověření, že cvičení bylo přiděleno k plánu a uloženo
        assertNotNull(result);
        assertEquals(trainingPlan, result.getTrainingPlan());
        verify(exerciseRepository, times(1)).save(exercise);
    }

    @Test
    void testDeleteExercise() {
        // Volání metody pro smazání
        exerciseService.deleteExercise(1L);

        // Ověření, že metoda deleteById byla zavolána s odpovídajícím ID
        verify(exerciseRepository, times(1)).deleteById(1L);
    }
}
