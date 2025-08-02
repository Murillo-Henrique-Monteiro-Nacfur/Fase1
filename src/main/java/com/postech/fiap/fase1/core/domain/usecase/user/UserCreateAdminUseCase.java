package com.postech.fiap.fase1.core.domain.usecase.user;

import com.postech.fiap.fase1.core.validation.user.UserCreateAdminValidation;
import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.gateway.user.UserGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCreateAdminUseCase {
    private final UserGateway userGateway;
    private final List<UserCreateAdminValidation> userCreateAdminValidations;
    private final PasswordEncoder passwordEncoder;

    public UserDomain execute(UserDomain userDomain) {
        validate(userDomain);
        userDomain.setAdminRole();
        userDomain.setPassword(passwordEncoder.encode(userDomain.getPassword()));
        return userGateway.create(userDomain);
    }

    private void validate(UserDomain userDomain) {
        userCreateAdminValidations
                .forEach(restaurantCreationValidation ->
                        restaurantCreationValidation.validate(userDomain));
    }
}
