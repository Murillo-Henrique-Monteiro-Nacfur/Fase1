package com.postech.fiap.fase1.integration;

import com.postech.fiap.fase1.AbstractIntegrationTest;
import com.postech.fiap.fase1.webapi.data.entity.Restaurant;
import com.postech.fiap.fase1.webapi.data.entity.Role;
import com.postech.fiap.fase1.webapi.data.entity.User;
import com.postech.fiap.fase1.webapi.data.repository.RestaurantRepository;
import com.postech.fiap.fase1.webapi.data.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantRepositoryIT extends AbstractIntegrationTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setName("Rest Owner");
        user.setLogin("restowner");
        user.setEmail("restowner@example.com");
        user.setPassword("password");
        user.setRole(Role.ADMIN);
        userRepository.save(user);
    }

    @Test
    void whenSaveRestaurant_thenFindByIdReturnsRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Churrascaria Teste");
        restaurant.setCnpj("12345678000199");
        restaurant.setCuisineType("Churrasco");
        restaurant.setOpenTime(LocalTime.of(10, 0));
        restaurant.setCloseTime(LocalTime.of(22, 0));
        restaurant.setUser(user);
        Restaurant saved = restaurantRepository.save(restaurant);

        Optional<Restaurant> found = restaurantRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Churrascaria Teste");
        assertThat(found.get().getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    void whenFindByNonexistentId_thenReturnEmpty() {
        Optional<Restaurant> found = restaurantRepository.findById(-1L);
        assertThat(found).isEmpty();
    }
}