package com.postech.fiap.fase1.core.gateway.user;

import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.dto.user.UserDTO;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.presenter.UserPresenter;
import com.postech.fiap.fase1.infrastructure.exception.ApplicationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserGatewayTest {

    @Mock
    private DataSource dataSource;

    @Mock
    private UserPresenter userPresenter;

    @InjectMocks
    private UserGateway userGateway;

    private UserDomain userDomain;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        userDomain = UserDomain.builder()
                .id(1L)
                .name("Test User")
                .login("testuser")
                .email("test@example.com")
                .password("password123")
                .build();

        userDTO = UserDTO.builder()
                .id(1L)
                .name("Test User")
                .login("testuser")
                .email("test@example.com")
                .password("password123")
                .build();
    }

    @Test
    void create_shouldReturnANewUserGateway() {
        var newUserGateway = UserGateway.build(dataSource);

        Assertions.assertNotNull(newUserGateway);
    }

    @Test
    void getOneUser_whenUserExists_shouldReturnUserDTO() {
        // Arrange
        when(dataSource.getUserById(1L)).thenReturn(Optional.of(userDTO));

        // Act
        UserDTO result = userGateway.getOneUser(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(dataSource).getUserById(1L);
    }

    @Test
    void getOneUser_whenUserDoesNotExist_shouldThrowApplicationException() {
        // Arrange
        when(dataSource.getUserById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> userGateway.getOneUser(1L))
                .isInstanceOf(ApplicationException.class)
                .hasMessage("User not found");
        verify(dataSource).getUserById(1L);
    }

    @Test
    void getUserById_whenUserExists_shouldReturnUserDomain() {
        // Arrange
        when(dataSource.getUserById(1L)).thenReturn(Optional.of(userDTO));
        when(userPresenter.toDomain(userDTO)).thenReturn(userDomain);

        // Act
        UserDomain result = userGateway.getUserById(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(dataSource).getUserById(1L);
        verify(userPresenter).toDomain(userDTO);
    }

    @Test
    void getAllUserPaged_shouldReturnPageOfUserDomain() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<UserDTO> userDTOPage = new PageImpl<>(List.of(userDTO), pageable, 1);

        when(dataSource.getAllUserPaged(pageable)).thenReturn(userDTOPage);
        when(userPresenter.toDomain(userDTO)).thenReturn(userDomain);

        // Act
        Page<UserDomain> result = userGateway.getAllUserPaged(pageable);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().getFirst().getLogin()).isEqualTo("testuser");
        verify(dataSource).getAllUserPaged(pageable);
        verify(userPresenter).toDomain(userDTO);
    }

    @Test
    void createUser_shouldReturnCreatedUserDomain() {
        // Arrange
        when(userPresenter.toDTO(userDomain)).thenReturn(userDTO);
        when(dataSource.createUser(userDTO)).thenReturn(userDTO);
        when(userPresenter.toDomain(userDTO)).thenReturn(userDomain);

        // Act
        UserDomain result = userGateway.createUser(userDomain);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getLogin()).isEqualTo("testuser");
        verify(userPresenter).toDTO(userDomain);
        verify(dataSource).createUser(userDTO);
        verify(userPresenter).toDomain(userDTO);
    }

    @Test
    void updateUser_whenUserExists_shouldReturnUpdatedUserDomain() {
        // Arrange
        UserDomain updatedInfoDomain = UserDomain.builder().build();
        updatedInfoDomain.setId(1L);
        updatedInfoDomain.setName("Updated Name");

        UserDTO updatedDTO = UserDTO.builder().build();
        updatedDTO.setId(1L);
        updatedDTO.setName("Updated Name");

        when(dataSource.getUserById(1L)).thenReturn(Optional.of(userDTO));
        when(userPresenter.updateToDTO(updatedInfoDomain, userDTO)).thenReturn(updatedDTO);
        when(dataSource.updateUser(updatedDTO)).thenReturn(updatedDTO);
        when(userPresenter.toDomain(updatedDTO)).thenReturn(updatedInfoDomain);

        // Act
        UserDomain result = userGateway.updateUser(updatedInfoDomain);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Updated Name");
        verify(dataSource).getUserById(1L);
        verify(userPresenter).updateToDTO(updatedInfoDomain, userDTO);
        verify(dataSource).updateUser(updatedDTO);
        verify(userPresenter).toDomain(updatedDTO);
    }

    @Test
    void updateUser_whenUserDoesNotExist_shouldThrowApplicationException() {
        // Arrange
        when(dataSource.getUserById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> userGateway.updateUser(userDomain))
                .isInstanceOf(ApplicationException.class)
                .hasMessage("User not found");
        verify(dataSource).getUserById(1L);
        verify(userPresenter, never()).updateToDTO(any(), any());
        verify(dataSource, never()).updateUser(any());
    }

    @Test
    void updateUserPassoword_shouldUpdatePasswordAndReturnUserDomain() {
        // Arrange
        UserDomain passwordUpdateDomain = UserDomain.builder().build();
        passwordUpdateDomain.setId(1L);
        passwordUpdateDomain.setPassword("newPassword");

        UserDTO updatedPasswordDTO = UserDTO.builder().build();
        updatedPasswordDTO.setId(1L);
        updatedPasswordDTO.setPassword("newPassword");

        when(dataSource.getUserById(1L)).thenReturn(Optional.of(userDTO));
        when(dataSource.updateUserPassoword(any(UserDTO.class))).thenReturn(updatedPasswordDTO);
        when(userPresenter.toDomain(updatedPasswordDTO)).thenReturn(passwordUpdateDomain);

        // Act
        UserDomain result = userGateway.updateUserPassoword(passwordUpdateDomain);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getPassword()).isEqualTo("newPassword");
        verify(dataSource).getUserById(1L);
        verify(dataSource).updateUserPassoword(argThat(dto -> dto.getPassword().equals("newPassword")));
        verify(userPresenter).toDomain(updatedPasswordDTO);
    }

    @Test
    void deleteUser_shouldCallDataSourceDelete() {
        // Arrange
        doNothing().when(dataSource).deleteUserById(1L);

        // Act
        userGateway.deleteUser(userDomain);

        // Assert
        verify(dataSource).deleteUserById(1L);
    }

    @Test
    void getUserByLogin_whenUserExists_shouldReturnOptionalOfUserDomain() {
        // Arrange
        when(dataSource.getUserByLogin("testuser")).thenReturn(Optional.of(userDTO));
        when(userPresenter.toDomain(userDTO)).thenReturn(userDomain);

        // Act
        Optional<UserDomain> result = userGateway.getUserByLogin("testuser");

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getLogin()).isEqualTo("testuser");
        verify(dataSource).getUserByLogin("testuser");
        verify(userPresenter).toDomain(userDTO);
    }

    @Test
    void getUserByLogin_whenUserDoesNotExist_shouldReturnEmptyOptional() {
        // Arrange
        when(dataSource.getUserByLogin("nonexistent")).thenReturn(Optional.empty());

        // Act
        Optional<UserDomain> result = userGateway.getUserByLogin("nonexistent");

        // Assert
        assertThat(result).isNotPresent();
        verify(dataSource).getUserByLogin("nonexistent");
        verify(userPresenter, never()).toDomain(any());
    }

    @Test
    @DisplayName("Deve retornar true quando o data source indicar que o email já existe")
    void hasUserWithSameEmail_whenEmailExists_shouldReturnTrue() {
        // Arrange (Organização)
        Long userId = 1L;
        String email = "test@example.com";
        when(dataSource.hasUserWithSameEmail(userId, email)).thenReturn(true);

        // Act (Ação)
        boolean result = userGateway.hasUserWithSameEmail(userId, email);

        // Assert (Verificação)
        assertThat(result).isTrue();
        verify(dataSource).hasUserWithSameEmail(userId, email);
    }

    @Test
    @DisplayName("Deve retornar false quando o data source indicar que o email não existe")
    void hasUserWithSameEmail_whenEmailDoesNotExist_shouldReturnFalse() {
        // Arrange
        Long userId = 1L;
        String email = "new@example.com";
        when(dataSource.hasUserWithSameEmail(userId, email)).thenReturn(false);

        // Act
        boolean result = userGateway.hasUserWithSameEmail(userId, email);

        // Assert
        assertThat(result).isFalse();
        verify(dataSource).hasUserWithSameEmail(userId, email);
    }

    @Test
    @DisplayName("Deve retornar true quando o data source indicar que o login já existe")
    void hasUserWithSameLogin_whenLoginExists_shouldReturnTrue() {
        // Arrange
        Long userId = 1L;
        String login = "testuser";
        when(dataSource.hasUserWithSameLogin(userId, login)).thenReturn(true);

        // Act
        boolean result = userGateway.hasUserWithSameLogin(userId, login);

        // Assert
        assertThat(result).isTrue();
        verify(dataSource).hasUserWithSameLogin(userId, login);
    }

    @Test
    @DisplayName("Deve retornar false quando o data source indicar que o login não existe")
    void hasUserWithSameLogin_whenLoginDoesNotExist_shouldReturnFalse() {
        // Arrange
        Long userId = 1L;
        String login = "newuser";
        when(dataSource.hasUserWithSameLogin(userId, login)).thenReturn(false);

        // Act
        boolean result = userGateway.hasUserWithSameLogin(userId, login);

        // Assert
        assertThat(result).isFalse();
        verify(dataSource).hasUserWithSameLogin(userId, login);
    }
}