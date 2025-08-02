package com.postech.fiap.fase1.core.presenter;

import com.postech.fiap.fase1.core.dto.auth.UserTokenBodyDTO;
import com.postech.fiap.fase1.core.domain.model.UserDomain;
import lombok.experimental.UtilityClass;

import java.util.Date;

@UtilityClass
public class UserTokenDTOPresenter {

   public UserTokenBodyDTO toUserTokenDTO(UserDomain userDomain, Date expiration) {
        return UserTokenBodyDTO
                .builder()
                .id(userDomain.getId())
                .name(userDomain.getName())
                .login(userDomain.getLogin())
                .email(userDomain.getEmail())
                .role(userDomain.getRole().name())
                .expiration(expiration)
                .build();
    }
}
