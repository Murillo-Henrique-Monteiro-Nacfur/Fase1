package com.postech.fiap.fase1.integration;

import com.postech.fiap.fase1.AbstractIntegrationTest;
import com.postech.fiap.fase1.webapi.data.entity.Role;
import com.postech.fiap.fase1.webapi.data.entity.User;
import com.postech.fiap.fase1.webapi.data.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryIT extends AbstractIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void whenFindByLogin_thenReturnUser() {
        User user = new User();
        user.setName("Test User");
        user.setLogin("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRole(Role.ADMIN);
        userRepository.save(user);

        Optional<User> found = userRepository.findByLogin("testuser");

        assertThat(found).isPresent();
        assertThat(found.get().getLogin()).isEqualTo(user.getLogin());
    }

    @Test
    void whenFindByLogin_withNonExistentLogin_thenReturnEmpty() {
        Optional<User> found = userRepository.findByLogin("nonexistent");

        assertThat(found).isNotPresent();
    }
}