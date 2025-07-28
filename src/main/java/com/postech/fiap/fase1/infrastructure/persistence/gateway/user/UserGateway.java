package com.postech.fiap.fase1.infrastructure.persistence.gateway.user;

import com.postech.fiap.fase1.domain.model.UserDomain;
import com.postech.fiap.fase1.infrastructure.persistence.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserGateway {

    User getOne(Long idUser);

    boolean hasUserWithSameEmail(Long idUser, String email);

    boolean hasUserWithSameLogin(Long idUser, String email);

    Optional<UserDomain> getById(Long idRestaurent);

    Page<UserDomain> getAllPaged(Pageable pageable);

    UserDomain createOrUpdate(UserDomain userDomain);
}
