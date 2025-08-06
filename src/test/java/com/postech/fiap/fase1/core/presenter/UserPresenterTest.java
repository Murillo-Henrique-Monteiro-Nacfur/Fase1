package com.postech.fiap.fase1.core.presenter;

import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.dto.user.*;
import com.postech.fiap.fase1.webapi.data.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserPresenterTest {
    private final UserPresenter presenter = new UserPresenter();

    @Test
    void requestToDomain_shouldMapFieldsCorrectly() {
        UserRequestDTO dto = UserRequestDTO.builder()
                .name("Test User")
                .email("test@example.com")
                .login("testuser")
                .password("pass")
                .passwordConfirmation("pass")
                .birthDate(LocalDate.of(2000, 1, 1))
                .build();
        UserDomain domain = presenter.requestToDomain(dto);
        assertThat(domain.getName()).isEqualTo(dto.getName());
        assertThat(domain.getEmail()).isEqualTo(dto.getEmail());
        assertThat(domain.getLogin()).isEqualTo(dto.getLogin());
        assertThat(domain.getPassword()).isEqualTo(dto.getPassword());
        assertThat(domain.getPasswordConfirmation()).isEqualTo(dto.getPasswordConfirmation());
        assertThat(domain.getBirthDate()).isEqualTo(dto.getBirthDate());
    }

    @Test
    void requestUpdateDetailsToInput_shouldMapFieldsCorrectly() {
        UserRequestUpdateDetailsDTO dto = UserRequestUpdateDetailsDTO.builder()
                .id(1L)
                .name("Test User")
                .email("test@example.com")
                .login("testuser")
                .birthDate(LocalDate.of(2000, 1, 1))
                .build();
        UserDomain domain = presenter.requestUpdateDetailsToInput(dto);
        assertThat(domain.getId()).isEqualTo(dto.getId());
        assertThat(domain.getName()).isEqualTo(dto.getName());
        assertThat(domain.getEmail()).isEqualTo(dto.getEmail());
        assertThat(domain.getLogin()).isEqualTo(dto.getLogin());
        assertThat(domain.getBirthDate()).isEqualTo(dto.getBirthDate());
    }

    @Test
    void requestUpdatePasswordToInput_shouldMapFieldsCorrectly() {
        UserRequestUpdatePasswordDTO dto = UserRequestUpdatePasswordDTO.builder()
                .id(1L)
                .password("pass")
                .passwordConfirmation("pass")
                .build();
        UserDomain domain = presenter.requestUpdatePasswordToInput(dto);
        assertThat(domain.getId()).isEqualTo(dto.getId());
        assertThat(domain.getPassword()).isEqualTo(dto.getPassword());
        assertThat(domain.getPasswordConfirmation()).isEqualTo(dto.getPasswordConfirmation());
    }

    @Test
    void toDTO_shouldMapFieldsCorrectly() {
        UserDomain domain = UserDomain.builder()
                .id(1L)
                .name("Test User")
                .email("test@example.com")
                .login("testuser")
                .role(Role.CLIENT)
                .birthDate(LocalDate.of(2000, 1, 1))
                .password("pass")
                .build();
        UserDTO dto = presenter.toDTO(domain);
        assertThat(dto.getId()).isEqualTo(domain.getId());
        assertThat(dto.getName()).isEqualTo(domain.getName());
        assertThat(dto.getEmail()).isEqualTo(domain.getEmail());
        assertThat(dto.getLogin()).isEqualTo(domain.getLogin());
        assertThat(dto.getRole()).isEqualTo(domain.getRole());
        assertThat(dto.getBirthDate()).isEqualTo(domain.getBirthDate());
        assertThat(dto.getPassword()).isEqualTo(domain.getPassword());
    }

    @Test
    void toResponseDTO_shouldMapFieldsCorrectly() {
        UserDomain domain = UserDomain.builder()
                .id(1L)
                .name("Test User")
                .email("test@example.com")
                .login("testuser")
                .role(Role.ADMIN)
                .birthDate(LocalDate.of(2000, 1, 1))
                .build();
        UserResponseDTO dto = presenter.toResponseDTO(domain);
        assertThat(dto.getId()).isEqualTo(domain.getId());
        assertThat(dto.getName()).isEqualTo(domain.getName());
        assertThat(dto.getEmail()).isEqualTo(domain.getEmail());
        assertThat(dto.getLogin()).isEqualTo(domain.getLogin());
        assertThat(dto.getRole()).isEqualTo(domain.getRole());
        assertThat(dto.getBirthDate()).isEqualTo(domain.getBirthDate().toString());
    }

    @Test
    void toResponseDTOPage_shouldMapListCorrectly() {
        UserDomain domain = UserDomain.builder()
                .id(1L)
                .name("Test User")
                .email("test@example.com")
                .login("testuser")
                .role(Role.ADMIN)
                .birthDate(LocalDate.of(2000, 1, 1))
                .build();
        List<UserDomain> domainList = Collections.singletonList(domain);
        Page<UserDomain> domainPage = new PageImpl<>(domainList);
        Page<UserResponseDTO> dtoPage = presenter.toResponseDTO(domainPage);
        assertThat(dtoPage.getTotalElements()).isEqualTo(1);
        UserResponseDTO dto = dtoPage.getContent().getFirst();
        assertThat(dto.getId()).isEqualTo(domain.getId());
    }

    @Test
    void toDTOPage_shouldMapListCorrectly() {
        UserDomain domain = UserDomain.builder()
                .id(1L)
                .name("Test User")
                .email("test@example.com")
                .login("testuser")
                .role(Role.CLIENT)
                .birthDate(LocalDate.of(2000, 1, 1))
                .password("pass")
                .build();
        List<UserDomain> domainList = Collections.singletonList(domain);
        Page<UserDomain> domainPage = new PageImpl<>(domainList);
        Page<UserDTO> dtoPage = presenter.toDTO(domainPage);
        assertThat(dtoPage.getTotalElements()).isEqualTo(1);
        UserDTO dto = dtoPage.getContent().getFirst();
        assertThat(dto.getId()).isEqualTo(domain.getId());
    }

    @Test
    void toDomain_shouldMapFieldsCorrectly() {
        UserDTO dto = UserDTO.builder()
                .id(1L)
                .name("Test User")
                .login("testuser")
                .email("test@example.com")
                .password("pass")
                .birthDate(LocalDate.of(2000, 1, 1))
                .role(Role.ADMIN)
                .build();
        UserDomain domain = presenter.toDomain(dto);
        assertThat(domain.getId()).isEqualTo(dto.getId());
        assertThat(domain.getName()).isEqualTo(dto.getName());
        assertThat(domain.getLogin()).isEqualTo(dto.getLogin());
        assertThat(domain.getEmail()).isEqualTo(dto.getEmail());
        assertThat(domain.getPassword()).isEqualTo(dto.getPassword());
        assertThat(domain.getBirthDate()).isEqualTo(dto.getBirthDate());
        assertThat(domain.getRole()).isEqualTo(dto.getRole());
    }

    @Test
    void updateToDTO_shouldUpdateFieldsCorrectly() {
        UserDomain domain = UserDomain.builder()
                .name("Updated Name")
                .email("updated@example.com")
                .login("updateduser")
                .birthDate(LocalDate.of(1999, 12, 31))
                .build();
        UserDTO dto = UserDTO.builder()
                .name("Old Name")
                .email("old@example.com")
                .login("olduser")
                .birthDate(LocalDate.of(1990, 1, 1))
                .build();
        presenter.updateToDTO(domain, dto);
        assertThat(dto.getName()).isEqualTo(domain.getName());
        assertThat(dto.getEmail()).isEqualTo(domain.getEmail());
        assertThat(dto.getLogin()).isEqualTo(domain.getLogin());
        assertThat(dto.getBirthDate()).isEqualTo(domain.getBirthDate());
    }
}