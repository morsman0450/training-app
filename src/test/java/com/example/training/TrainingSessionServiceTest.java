package com.example.training;

import com.example.training.model.TrainingPlan;
import com.example.training.model.TrainingSession;
import com.example.training.repository.TrainingSessionRepository;
import com.example.training.service.TrainingSessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TrainingSessionServiceTest {

    @Mock
    private TrainingSessionRepository trainingSessionRepository;

    @InjectMocks
    private TrainingSessionService trainingSessionService;

    private TrainingPlan trainingPlan;
    private TrainingSession trainingSession;

    @BeforeEach
    public void setUp() {
        // Nastavení testovacího objektu TrainingPlan a TrainingSession
        trainingPlan = new TrainingPlan();
        trainingSession = new TrainingSession();
    }

    @Test
    public void testAddSessionToPlan() {
        // Představujeme, že save() vrátí uloženou session
        when(trainingSessionRepository.save(trainingSession)).thenReturn(trainingSession);

        // Zavoláme metodu addSessionToPlan
        TrainingSession savedSession = trainingSessionService.addSessionToPlan(trainingSession, trainingPlan);

        // Ověříme, že save() bylo zavoláno jednou
        verify(trainingSessionRepository, times(1)).save(trainingSession);

        // Ověření, že metoda vrací stejný objekt
        assertNotNull(savedSession);
        assertEquals(trainingSession, savedSession);
    }

    @Test
    public void testGetSessionsForPlan() {
        // Představujeme seznam session pro daný plán
        when(trainingSessionRepository.findByTrainingPlan(trainingPlan)).thenReturn(Collections.singletonList(trainingSession));

        // Zavoláme metodu getSessionsForPlan
        List<TrainingSession> sessions = trainingSessionService.getSessionsForPlan(trainingPlan);

        // Ověříme, že metoda findByTrainingPlan byla zavolána
        verify(trainingSessionRepository, times(1)).findByTrainingPlan(trainingPlan);

        // Ověření, že se vrátil seznam s jednou session
        assertNotNull(sessions);
        assertEquals(1, sessions.size());
        assertEquals(trainingSession, sessions.get(0));
    }
}
