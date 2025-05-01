package com.example.training.service;

import com.example.training.model.User;
import com.example.training.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    private User user;

    @BeforeEach
    void setUp() {
        // Inicializace mock objektů
        MockitoAnnotations.openMocks(this);

        // Přednastavení mockovaného uživatele
        user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");
    }

    @Test
    void testLoadUserByUsername_Success() {
        // Mockování repository, že najde uživatele
        when(userRepository.findByUsername("testUser")).thenReturn(java.util.Optional.of(user));

        // Volání metody a ověření výsledku
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("testUser");

        assertNotNull(userDetails);
        assertEquals("testUser", userDetails.getUsername());
        assertEquals("testPassword", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_USER")));
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        // Mockování repository, že uživatel neexistuje
        when(userRepository.findByUsername("testUser")).thenReturn(java.util.Optional.empty());

        // Ověření, že metoda vyhodí výjimku
        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername("testUser");
        });
    }
}
