package com.postech.fiap.fase1.core.domain.usecase.user;

import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.domain.validation.user.UserCreateAdminValidation;
import com.postech.fiap.fase1.core.gateway.session.ISessionGateway;
import com.postech.fiap.fase1.core.gateway.user.IUserGateway;
import com.postech.fiap.fase1.webapi.data.entity.Role;
import com.postech.fiap.fase1.webapi.infrastructure.exception.ApplicationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserCreateAdminUseCaseTest {

    @Mock
    private IUserGateway iUserGateway;
    @Mock
    private ISessionGateway sessionGateway;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserCreateAdminValidation validation1;
    @Mock
    private UserCreateAdminValidation validation2;

    private UserCreateAdminUseCase userCreateAdminUseCase;

    @Captor
    private ArgumentCaptor<UserDomain> userDomainCaptor;

    private UserDomain userToCreate;

    @BeforeEach
    void setUp() {
        userCreateAdminUseCase = new UserCreateAdminUseCase(iUserGateway, passwordEncoder, List.of(validation1, validation2));

        userToCreate = UserDomain.builder()
                .name("Admin User")
                .login("admin")
                .email("admin@test.com")
                .password("rawPassword123")
                .build();
    }

    @Test
    void create_shouldReturnANewUserCreateAdminUseCase() {
        var newUserCreateAdminUseCase = UserCreateAdminUseCase.build(iUserGateway, sessionGateway);

        Assertions.assertNotNull(newUserCreateAdminUseCase);
    }

    @Test
    @DisplayName("Deve criar um usuário administrador com sucesso quando todas as validações passarem")
    void execute_whenValidationsPass_shouldCreateAdminUserSuccessfully() {
        String rawPassword = "rawPassword123";
        String encodedPassword = "encodedPasswordABC";

        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(iUserGateway.createUser(any(UserDomain.class))).thenAnswer(invocation -> invocation.getArgument(0));

        doNothing().when(validation1).validate(userToCreate);
        doNothing().when(validation2).validate(userToCreate);

        UserDomain result = userCreateAdminUseCase.execute(userToCreate);

        verify(validation1).validate(userToCreate);
        verify(validation2).validate(userToCreate);

        verify(passwordEncoder).encode(rawPassword);

        verify(iUserGateway).createUser(userDomainCaptor.capture());
        UserDomain capturedUser = userDomainCaptor.getValue();

        assertThat(capturedUser.getRole()).isEqualTo(Role.ADMIN);
        assertThat(capturedUser.getPassword()).isEqualTo(encodedPassword);
        assertThat(capturedUser.getLogin()).isEqualTo("admin");
        assertThat(result).isNotNull();
        assertThat(result.getRole()).isEqualTo(Role.ADMIN);
    }

    @Test
    @DisplayName("Deve lançar exceção e não criar o usuário se uma validação falhar")
    void execute_whenValidationFails_shouldThrowExceptionAndNotCreateUser() {
        doThrow(new ApplicationException("Login já em uso"))
                .when(validation1).validate(userToCreate);

        assertThatThrownBy(() -> userCreateAdminUseCase.execute(userToCreate))
                .isInstanceOf(ApplicationException.class)
                .hasMessage("Login já em uso");

        verify(validation2, never()).validate(userToCreate);
        verify(passwordEncoder, never()).encode(anyString());
        verify(iUserGateway, never()).createUser(any(UserDomain.class));
    }
}