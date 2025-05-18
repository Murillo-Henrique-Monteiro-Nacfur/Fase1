package com.postech.fiap.fase1.domain.assembler;

import com.postech.fiap.fase1.domain.dto.auth.UserTokenDTO;
import com.postech.fiap.fase1.domain.model.User;
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
