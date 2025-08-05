package com.postech.fiap.fase1.integration;

import com.postech.fiap.fase1.AbstractIntegrationTest;
import com.postech.fiap.fase1.webapi.data.entity.Product;
import com.postech.fiap.fase1.webapi.data.entity.Restaurant;
import com.postech.fiap.fase1.webapi.data.entity.Role;
import com.postech.fiap.fase1.webapi.data.entity.User;
import com.postech.fiap.fase1.webapi.data.repository.ProductRepository;
import com.postech.fiap.fase1.webapi.data.repository.RestaurantRepository;
import com.postech.fiap.fase1.webapi.data.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductRepositoryIT extends AbstractIntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setName("Test User");
        user.setLogin("example");
        user.setEmail("testeemailinexixteste@teste.com");
        user.setPassword("password");
        user.setRole(Role.ADMIN);
        userRepository.save(user);

        Restaurant r = new Restaurant();
        r.setName("Test Restaurant");
        r.setDescription("Description");
        r.setCnpj("12345678000199");
        r.setCuisineType("Brazilian");
        r.setOpenTime(LocalTime.of(11, 0));
        r.setCloseTime(LocalTime.of(22, 0));
        r.setUser(user);
        restaurant = restaurantRepository.save(r);
    }

    @Test
    void whenFindByRestaurantId_thenReturnProducts() {
        Product product = new Product();
        product.setName("Feijoada");
        product.setDescription("A classic Brazilian dish.");
        product.setPrice(new BigDecimal("45.50"));
        product.setRestaurant(restaurant);
        productRepository.save(product);

        List<Product> foundProducts = productRepository.findProductByRestaurantId(restaurant.getId());

        assertThat(foundProducts).hasSize(1);
        assertThat(foundProducts.getFirst().getName()).isEqualTo("Feijoada");
        assertThat(foundProducts.getFirst().getRestaurant().getId()).isEqualTo(restaurant.getId());
    }

    @Test
    void whenFindByRestaurantId_withNoProducts_thenReturnEmptyList() {
        List<Product> foundProducts = productRepository.findProductByRestaurantId(restaurant.getId());

        assertThat(foundProducts).isEmpty();
    }
}