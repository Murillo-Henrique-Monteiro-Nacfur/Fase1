package com.postech.fiap.fase1.webapi.data.mapper;

import com.postech.fiap.fase1.core.dto.user.UserDTO;
import com.postech.fiap.fase1.webapi.data.entity.Role;
import com.postech.fiap.fase1.webapi.data.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    @InjectMocks
    private UserMapper userMapper;

    private User userEntity;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        userDTO = UserDTO.builder()
                .id(1L)
                .name("John Doe DTO")
                .login("johndoe")
                .email("john.dto@example.com")
                .password("password123") // Senha é mapeada no toEntity
                .birthDate(LocalDate.of(1990, 5, 15))
                .role(Role.CLIENT)
                .build();

        userEntity = User.builder()
                .id(1L)
                .name("John Doe Entity")
                .login("johndoe_entity")
                .email("john.entity@example.com")
                .password("entity_secret") // Senha é mapeada no toDTO
                .birthDate(LocalDate.of(1985, 10, 20))
                .role(Role.ADMIN)
                .build();
    }

    @Test
    @DisplayName("Deve mapear UserDTO para a entidade User corretamente")
    void shouldCorrectlyMapDtoToEntity() {
        User resultEntity = userMapper.toEntity(userDTO);

        assertThat(resultEntity)
                .isNotNull()
                .extracting(
                        User::getId,
                        User::getName,
                        User::getEmail,
                        User::getPassword,
                        User::getLogin,
                        User::getRole
                )
                .containsExactly(
                        userDTO.getId(),
                        userDTO.getName(),
                        userDTO.getEmail(),
                        userDTO.getPassword(),
                        userDTO.getLogin(),
                        userDTO.getRole()
                );
    }

    @Test
    @DisplayName("Deve mapear a entidade User para UserDTO corretamente")
    void shouldCorrectlyMapEntityToDto() {
        UserDTO resultDTO = userMapper.toDTO(userEntity);

        assertThat(resultDTO)
                .isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("addresses")
                .isEqualTo(userEntity);
    }

    @Test
    @DisplayName("Deve atualizar uma entidade User com dados de um UserDTO")
    void shouldCorrectlyUpdateEntityFromDto() {
        User updatedEntity = userMapper.updateToEntity(userDTO, userEntity);

        assertThat(updatedEntity).isSameAs(userEntity);

        assertThat(updatedEntity.getName()).isEqualTo(userDTO.getName());
        assertThat(updatedEntity.getEmail()).isEqualTo(userDTO.getEmail());
        assertThat(updatedEntity.getLogin()).isEqualTo(userDTO.getLogin());
        assertThat(updatedEntity.getBirthDate()).isEqualTo(userDTO.getBirthDate());

        assertThat(updatedEntity.getId()).isEqualTo(userEntity.getId()); // ID não muda
        assertThat(updatedEntity.getPassword()).isEqualTo(userEntity.getPassword()); // Senha não muda
        assertThat(updatedEntity.getRole()).isEqualTo(userEntity.getRole()); // Role não muda
    }

    @Test
    @DisplayName("Deve mapear uma Page de entidades User para uma Page de UserDTOs")
    void shouldCorrectlyMapPageOfEntitiesToPageOfDtos() {
        // Arrange
        // Cria uma página de entidades para simular o retorno de um repositório.
        List<User> userList = List.of(userEntity);
        Page<User> userPage = new PageImpl<>(userList, PageRequest.of(0, 10), userList.size());

        // Act
        Page<UserDTO> resultPageDTO = userMapper.toDTO(userPage);

        // Assert
        assertThat(resultPageDTO).isNotNull();
        assertThat(resultPageDTO.getTotalElements()).isEqualTo(userPage.getTotalElements());
        assertThat(resultPageDTO.getContent()).hasSize(1);

        // Compara o conteúdo do primeiro DTO da página com a entidade original.
        assertThat(resultPageDTO.getContent().getFirst())
                .usingRecursiveComparison()
                .ignoringFields("addresses")
                .isEqualTo(userEntity);
    }
}