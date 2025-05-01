package com.example.training;

import com.example.training.model.TrainingPlan;
import com.example.training.model.User;
import com.example.training.repository.TrainingPlanRepository;
import com.example.training.service.TrainingPlanService;
import com.example.training.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TrainingPlanServiceTest {

    @Mock
    private TrainingPlanRepository trainingPlanRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private TrainingPlanService trainingPlanService;

    private User user;
    private TrainingPlan trainingPlan;

    @BeforeEach
    void setUp() {
        // Inicializace mock objektů
        MockitoAnnotations.openMocks(this);

        // Přednastavení mockovaných entit
        user = new User();
        user.setUsername("testUser");

        trainingPlan = new TrainingPlan();
        trainingPlan.setId(1L);
        trainingPlan.setName("Plan 1");
        trainingPlan.setUser(user);
    }

    @Test
    void testGetPlansForUser() {
        // Mockování, že metoda userService vrátí uživatele
        when(userService.findByUsername("testUser")).thenReturn(user);
        // Mockování, že repository vrátí seznam tréninkových plánů
        when(trainingPlanRepository.findByUser(user)).thenReturn(Collections.singletonList(trainingPlan));

        // Volání metody a ověření výsledku
        List<TrainingPlan> result = trainingPlanService.getPlansForUser("testUser");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Plan 1", result.get(0).getName());
    }

    @Test
    void testGetPlanById() {
        // Mockování, že repository vrátí tréninkový plán podle ID
        when(trainingPlanRepository.findById(1L)).thenReturn(Optional.of(trainingPlan));

        // Volání metody a ověření výsledku
        TrainingPlan result = trainingPlanService.getPlanById(1L);

        assertNotNull(result);
        assertEquals("Plan 1", result.getName());
    }

    @Test
    void testGetPlanById_NotFound() {
        // Mockování, že repository nevrátí žádný plán
        when(trainingPlanRepository.findById(1L)).thenReturn(Optional.empty());

        // Ověření, že metoda vyhodí výjimku
        assertThrows(RuntimeException.class, () -> {
            trainingPlanService.getPlanById(1L);
        });
    }

    @Test
    void testSavePlan() {
        // Mockování, že metoda save vrátí uložený plán
        when(trainingPlanRepository.save(trainingPlan)).thenReturn(trainingPlan);

        // Volání metody a ověření výsledku
        TrainingPlan result = trainingPlanService.savePlan(trainingPlan);

        assertNotNull(result);
        assertEquals("Plan 1", result.getName());
    }

    @Test
    void testDeletePlan() {
        // Volání metody pro smazání
        trainingPlanService.deletePlan(1L);

        // Ověření, že metoda deleteById byla zavolána s odpovídajícím ID
        verify(trainingPlanRepository, times(1)).deleteById(1L);
    }
}
