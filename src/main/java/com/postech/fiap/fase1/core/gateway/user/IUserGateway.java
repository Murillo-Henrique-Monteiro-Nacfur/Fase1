package com.postech.fiap.fase1.core.gateway.user;

import com.postech.fiap.fase1.core.domain.model.UserDomain;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IUserGateway {

    boolean hasUserWithSameEmail(Long idUser, String email);

    boolean hasUserWithSameLogin(Long idUser, String email);

    UserDomain getUserById(Long idUser);

    Page<UserDomain> getAllUserPaged(Pageable pageable);

    UserDomain createUser(UserDomain userDomain);

    UserDomain updateUser(UserDomain userDomain);

    UserDomain updateUserPassoword(UserDomain userDomain);

    void deleteUser(UserDomain userDomain);

    Optional<UserDomain> getUserByLogin(@NotBlank String login);
}
