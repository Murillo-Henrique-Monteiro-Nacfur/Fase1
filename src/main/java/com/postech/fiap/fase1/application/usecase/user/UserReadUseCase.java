package com.postech.fiap.fase1.application.usecase.user;

import com.postech.fiap.fase1.application.validation.user.implementation.UserAdminAllowedValidator;
import com.postech.fiap.fase1.application.validation.user.implementation.UserAllowedValidator;
import com.postech.fiap.fase1.domain.model.UserDomain;
import com.postech.fiap.fase1.infrastructure.exception.ApplicationException;
import com.postech.fiap.fase1.infrastructure.persistence.gateway.user.UserGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserReadUseCase {
    private final UserAllowedValidator userAllowedValidator;
    private final UserAdminAllowedValidator userAdminAllowedValidator;
    private final UserGateway userGateway;

    public UserDomain getById(Long idUser) {
        userAllowedValidator.validate(UserDomain.builder().id(idUser).build());
        return userGateway.getById(idUser)
                .orElseThrow(() -> new ApplicationException("User not found"));
    }

    public Page<UserDomain> getAllPaged(Pageable pageable) {
        userAllowedValidator.validate();
        return userGateway.getAllPaged(pageable);
    }
}
