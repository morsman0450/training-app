package com.example.training;

import com.example.training.model.User;
import com.example.training.repository.UserRepository;
import com.example.training.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        // Inicializace mock objektů
        MockitoAnnotations.openMocks(this);

        // Přednastavení mockovaných entit
        user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");
    }

    @Test
    void testRegisterUser() {
        // Mockování, že passwordEncoder.encode() vrátí šifrované heslo
        when(passwordEncoder.encode("testPassword")).thenReturn("encodedPassword");

        // Volání metody pro registraci uživatele
        userService.registerUser(user);

        // Ověření, že passwordEncoder.encode() byl zavolán správně s původním heslem
        verify(passwordEncoder, times(1)).encode("testPassword");

        // Ověření, že metoda save byla zavolána
        verify(userRepository, times(1)).save(user);
        assertEquals("encodedPassword", user.getPassword());
    }


    @Test
    void testFindByUsername() {
        // Mockování, že findByUsername vrátí uživatele
        when(userRepository.findByUsername(user.getUsername())).thenReturn(java.util.Optional.of(user));

        // Volání metody pro nalezení uživatele
        User result = userService.findByUsername(user.getUsername());

        // Ověření, že uživatel byl nalezen
        assertNotNull(result);
        assertEquals(user.getUsername(), result.getUsername());
    }

    @Test
    void testFindByUsernameThrowsException() {
        // Mockování, že findByUsername vrátí prázdnou hodnotu (uživatel neexistuje)
        when(userRepository.findByUsername(user.getUsername())).thenReturn(java.util.Optional.empty());

        // Ověření, že metoda vyvolá výjimku, pokud uživatel neexistuje
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.findByUsername(user.getUsername());
        });

        assertEquals("Uživatel s jménem " + user.getUsername() + " nebyl nalezen", exception.getMessage());
    }

    @Test
    void testExistsByUsername() {
        // Mockování, že existsByUsername vrátí true
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);

        // Volání metody pro ověření existence uživatele
        boolean exists = userService.existsByUsername(user.getUsername());

        // Ověření, že vrácená hodnota je true
        assertTrue(exists);
    }

    @Test
    void testExistsByUsernameReturnsFalse() {
        // Mockování, že existsByUsername vrátí false
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);

        // Volání metody pro ověření existence uživatele
        boolean exists = userService.existsByUsername(user.getUsername());

        // Ověření, že vrácená hodnota je false
        assertFalse(exists);
    }
}
