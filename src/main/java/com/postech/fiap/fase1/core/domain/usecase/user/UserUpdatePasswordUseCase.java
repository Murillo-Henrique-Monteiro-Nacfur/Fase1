package com.postech.fiap.fase1.core.domain.usecase.user;

import com.postech.fiap.fase1.core.validation.user.UserUpdatePasswordValidation;
import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.gateway.user.UserGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserUpdatePasswordUseCase {

    private final UserGateway userGateway;
    private final UserReadUseCase userReadUseCase;
    private final List<UserUpdatePasswordValidation> userUpdatePasswordValidations;
    private final PasswordEncoder passwordEncoder;

    public UserDomain execute(UserDomain userDomain) {
        userReadUseCase.getById(userDomain.getId());
        validate(userDomain);
        userDomain.setPassword(passwordEncoder.encode(userDomain.getPassword()));
        return userGateway.updatePassoword(userDomain);
    }

    private void validate(UserDomain userDomain) {
        userUpdatePasswordValidations
                .forEach(restaurantCreationValidation ->
                        restaurantCreationValidation.validate(userDomain));
    }
}
