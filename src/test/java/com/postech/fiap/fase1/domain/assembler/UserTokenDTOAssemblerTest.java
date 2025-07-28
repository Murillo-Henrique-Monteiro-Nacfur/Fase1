package com.postech.fiap.fase1.domain.assembler;

import com.postech.fiap.fase1.application.assembler.UserTokenDTOAssembler;
import com.postech.fiap.fase1.application.dto.auth.UserTokenDTO;
import com.postech.fiap.fase1.infrastructure.persistence.entity.User;
import com.postech.fiap.fase1.environment.EnvUser;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class UserTokenDTOAssemblerTest {

    @Test
    void shouldConvertsUserToUserTokenDTOWithValidData() {
        User user = EnvUser.getUserClient();
        Date expiration = new Date();

        UserTokenDTO result = UserTokenDTOAssembler.toUserTokenDTO(user, expiration);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(user.getId());
        assertThat(result.getName()).isEqualTo(user.getName());
        assertThat(result.getLogin()).isEqualTo(user.getLogin());
        assertThat(result.getEmail()).isEqualTo(user.getEmail());
        assertThat(result.getRole()).isEqualTo(user.getRole().name());
        assertThat(result.getExpiration()).isEqualTo(expiration);
    }

    @Test
    void shouldReturnsNullExpirationWhenExpirationIsNull() {
        User user = EnvUser.getUserClient();

        UserTokenDTO result = UserTokenDTOAssembler.toUserTokenDTO(user, null);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(user.getId());
        assertThat(result.getName()).isEqualTo(user.getName());
        assertThat(result.getLogin()).isEqualTo(user.getLogin());
        assertThat(result.getEmail()).isEqualTo(user.getEmail());
        assertThat(result.getRole()).isEqualTo(user.getRole().name());
        assertThat(result.getExpiration()).isNull();
    }
}