package com.postech.fiap.fase1.core.gateway.user;

import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.infrastructure.data.entity.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserGateway {

    User getOne(Long idUser);

    boolean hasUserWithSameEmail(Long idUser, String email);

    boolean hasUserWithSameLogin(Long idUser, String email);

    Optional<UserDomain> getById(Long idRestaurent);

    Page<UserDomain> getAllPaged(Pageable pageable);

    UserDomain create(UserDomain userDomain);

    UserDomain update(UserDomain userDomain);

    UserDomain updatePassoword(UserDomain userDomain);

    void delete(UserDomain userDomain);

    UserDomain getUserByLogin(@NotBlank String login);
}
