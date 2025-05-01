package com.example.training.repository;

import com.example.training.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Metoda pro vyhledání uživatele podle uživatelského jména
    Optional<User> findByUsername(String username);

    // Volitelně metoda pro kontrolu, zda již uživatelské jméno existuje
    boolean existsByUsername(String username);
}