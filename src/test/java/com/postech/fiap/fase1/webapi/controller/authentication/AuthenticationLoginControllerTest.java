package com.postech.fiap.fase1.webapi.controller.authentication;

import com.postech.fiap.fase1.core.controllers.authentication.AuthenticationLoginCoreController;
import com.postech.fiap.fase1.core.dto.auth.LoginRequestDTO;
import com.postech.fiap.fase1.core.dto.auth.LoginResponseDTO;
import com.postech.fiap.fase1.webapi.data.DataRepository;
import com.postech.fiap.fase1.webapi.infrastructure.SecurityValues;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationLoginControllerTest {

    @Mock
    private DataRepository dataRepository;

    @Mock
    private SecurityValues securityValues;

    @InjectMocks
    private AuthenticationLoginController authenticationLoginController;

    @Test
    @DisplayName("Deve realizar o login e retornar o token com status 200 (OK)")
    void login_shouldDelegateToCoreControllerAndReturnOk() {
        LoginRequestDTO requestDTO = new LoginRequestDTO("user", "password");
        LoginResponseDTO expectedResponse = new LoginResponseDTO("dummy-jwt-token");
        String dummySecret = "secret-key";

        when(securityValues.getTokenSecret()).thenReturn(dummySecret);

        try (MockedConstruction<AuthenticationLoginCoreController> mockedCoreController = mockConstruction(
                AuthenticationLoginCoreController.class,
                (mock, context) -> {
                    assertThat(context.arguments()).hasSize(2);
                    assertThat(context.arguments().get(0)).isEqualTo(dummySecret);
                    assertThat(context.arguments().get(1)).isEqualTo(dataRepository);
                    when(mock.login(any(LoginRequestDTO.class))).thenReturn(expectedResponse);
                }
        )) {
            ResponseEntity<LoginResponseDTO> responseEntity = authenticationLoginController.login(requestDTO);

            assertThat(responseEntity).isNotNull();
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(responseEntity.getBody()).isNotNull();
            assertThat(responseEntity.getBody()).isEqualTo(expectedResponse);

            List<AuthenticationLoginCoreController> constructedInstances = mockedCoreController.constructed();
            assertThat(constructedInstances).hasSize(1);

            AuthenticationLoginCoreController coreControllerMock = constructedInstances.getFirst();
            verify(coreControllerMock).login(requestDTO);
            verify(securityValues).getTokenSecret();
        }
    }
}