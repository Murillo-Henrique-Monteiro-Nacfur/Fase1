package com.postech.fiap.fase1.integration.controller;

import com.postech.fiap.fase1.AbstractIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.postech.fiap.fase1.core.dto.auth.LoginRequestDTO;
import com.postech.fiap.fase1.core.dto.auth.LoginResponseDTO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "/create-adress.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class AddressDeleteControllerIT extends AbstractIntegrationTest {

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
    @DisplayName("Deve deletar um endereço com um usuário autenticado")
    void delete_whenAuthenticated_shouldReturnOk() throws Exception {
        String token = getAuthToken();
        Long addressIdToDelete = 2001L;

        mockMvc.perform(delete("/api/v1/address/{id}", addressIdToDelete)
                        .header("X-Auth-Token",  token))
                .andExpect(status().isOk());
    }
}

