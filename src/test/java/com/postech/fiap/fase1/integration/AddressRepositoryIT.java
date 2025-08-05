package com.postech.fiap.fase1.integration;

import com.postech.fiap.fase1.AbstractIntegrationTest;
import com.postech.fiap.fase1.webapi.data.entity.Address;
import com.postech.fiap.fase1.webapi.data.entity.Role;
import com.postech.fiap.fase1.webapi.data.entity.User;
import com.postech.fiap.fase1.webapi.data.repository.AddressRepository;
import com.postech.fiap.fase1.webapi.data.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class AddressRepositoryIT extends AbstractIntegrationTest {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setName("Address User");
        user.setLogin("addressuser");
        user.setEmail("addressuser@example.com");
        user.setPassword("password");
        user.setRole(Role.ADMIN);
        userRepository.save(user);
    }

    @Test
    void whenSaveAddress_thenFindByIdReturnsAddress() {
        Address address = new Address();
        address.setStreet("Rua Teste");
        address.setNumber("123");
        address.setNeighborhood("Centro");
        address.setCity("Cidade Teste");
        address.setState("SP");
        address.setZipCode("12345-678");
        address.setCountry("Brasil");
        address.setAddressable(user);
        Address saved = addressRepository.save(address);

        Optional<Address> found = addressRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getStreet()).isEqualTo("Rua Teste");
        assertThat(found.get().getAddressable()).isInstanceOf(User.class);
        assertThat(found.get().getAddressable().getId()).isEqualTo(user.getId());
    }

    @Test
    void whenFindByNonexistentId_thenReturnEmpty() {
        Optional<Address> found = addressRepository.findById(-1L);
        assertThat(found).isEmpty();
    }
}