package com.postech.fiap.fase1.core.presenter;

import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.webapi.data.entity.Role;
import com.postech.fiap.fase1.core.dto.auth.UserTokenBodyDTO;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class UserTokenDTOPresenterTest {

    @Test
    void toUserTokenDTO_shouldMapFieldsCorrectly() {
        Long id = 1L;
        String name = "Test User";
        String login = "testuser";
        String email = "test@example.com";
        Role role = Role.CLIENT;
        Date expiration = new Date();

        UserDomain userDomain = UserDomain.builder()
                .id(id)
                .name(name)
                .login(login)
                .email(email)
                .role(role)
                .build();

        UserTokenBodyDTO dto = UserTokenDTOPresenter.toUserTokenDTO(userDomain, expiration);

        assertThat(dto.getId()).isEqualTo(id);
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getLogin()).isEqualTo(login);
        assertThat(dto.getEmail()).isEqualTo(email);
        assertThat(dto.getRole()).isEqualTo(role.name());
        assertThat(dto.getExpiration()).isEqualTo(expiration);
    }

    @Test
    void toUserTokenDTO_shouldHandleNullFields() {
        Role role = Role.ADMIN;
        UserDomain userDomain = UserDomain.builder()
                .id(null)
                .name(null)
                .login(null)
                .email(null)
                .role(role)
                .build();
        Date expiration = null;

        UserTokenBodyDTO dto = UserTokenDTOPresenter.toUserTokenDTO(userDomain, expiration);

        assertThat(dto.getId()).isNull();
        assertThat(dto.getName()).isNull();
        assertThat(dto.getLogin()).isNull();
        assertThat(dto.getEmail()).isNull();
        assertThat(dto.getRole()).isEqualTo(role.name());
        assertThat(dto.getExpiration()).isNull();
    }
}