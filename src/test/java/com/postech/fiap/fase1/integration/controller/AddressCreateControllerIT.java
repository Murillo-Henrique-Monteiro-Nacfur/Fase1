package com.postech.fiap.fase1.integration.controller;

import com.postech.fiap.fase1.AbstractIntegrationTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.postech.fiap.fase1.core.dto.address.AddressRequestDTO;
import com.postech.fiap.fase1.core.dto.auth.LoginRequestDTO;
import com.postech.fiap.fase1.core.dto.auth.LoginResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "/create-adress.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class AddressCreateControllerIT extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private final String testUserLogin = "testuser";
    private final String testUserPassword = "password";

    private String getAuthToken() throws Exception {
        LoginRequestDTO loginRequest = new LoginRequestDTO(testUserLogin, testUserPassword);
        String responseString = mockMvc.perform(post("/api/v1/authentication/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        LoginResponseDTO loginResponse = objectMapper.readValue(responseString, LoginResponseDTO.class);
        return loginResponse.getToken();
    }

    @Test
    @DisplayName("Deve criar endereço de usuário e retornar 201")
    void createUserAddress_ReturnsCreated() throws Exception {
        AddressRequestDTO requestDTO = mockAddressRequestDTO();
        String token = getAuthToken();
        mockMvc.perform(post("/api/v1/address/user/{idUser}", 1001L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Auth-Token", token)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Deve criar endereço de restaurante e retornar 201")
    void createRestaurantAddress_ReturnsCreated() throws Exception {
        AddressRequestDTO requestDTO = mockAddressRequestDTO();
        String token = getAuthToken();
        mockMvc.perform(post("/api/v1/address/restaurant/{idRestaurant}", 3001L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Auth-Token", token)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Deve retornar 400 ao tentar criar endereço de usuário com campo obrigatório vazio")
    void createUserAddress_ReturnsBadRequest_WhenMissingField() throws Exception {
        AddressRequestDTO requestDTO = new AddressRequestDTO();
        String token = getAuthToken();
        mockMvc.perform(post("/api/v1/address/user/{idUser}", 1001L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Auth-Token", token)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());
    }

    private AddressRequestDTO mockAddressRequestDTO() {
        AddressRequestDTO dto =  AddressRequestDTO.builder()
                .street("Rua Teste")
                .number("123")
                .neighborhood("Bairro Teste")
                .city("Cidade Teste")
                .state("SP")
                .zipCode("12345678")
                .country("Brasil")
                .build();
        return dto;
    }
}