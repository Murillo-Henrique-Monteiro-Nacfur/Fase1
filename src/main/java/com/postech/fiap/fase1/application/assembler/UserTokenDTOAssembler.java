package com.postech.fiap.fase1.application.assembler;

import com.postech.fiap.fase1.application.dto.auth.UserTokenDTO;
import com.postech.fiap.fase1.infrastructure.persistence.entity.User;
import lombok.experimental.UtilityClass;

import java.util.Date;

@UtilityClass
public class UserTokenDTOAssembler {

   public UserTokenDTO toUserTokenDTO(User user, Date expiration) {
        return UserTokenDTO
                .builder()
                .id(user.getId())
                .name(user.getName())
                .login(user.getLogin())
                .email(user.getEmail())
                .role(user.getRole().name())
                .expiration(expiration)
                .build();
    }
}
