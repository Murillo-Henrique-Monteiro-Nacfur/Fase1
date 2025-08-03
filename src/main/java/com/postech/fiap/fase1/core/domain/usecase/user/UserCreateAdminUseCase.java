package com.postech.fiap.fase1.core.domain.usecase.user;

import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.gateway.session.ISessionGateway;
import com.postech.fiap.fase1.core.gateway.user.IUserGateway;
import com.postech.fiap.fase1.core.domain.validation.user.UserCreateAdminValidation;
import com.postech.fiap.fase1.core.domain.validation.user.implementation.UserAdminAllowedValidator;
import com.postech.fiap.fase1.core.domain.validation.user.implementation.UserEmailAlreadyUsedValidator;
import com.postech.fiap.fase1.core.domain.validation.user.implementation.UserLoginAlreadyUsedValidator;
import com.postech.fiap.fase1.core.domain.validation.user.implementation.UserPasswordValidator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class UserCreateAdminUseCase {
    private final IUserGateway iUserGateway;
    private final List<UserCreateAdminValidation> userCreateAdminValidations;
    private final PasswordEncoder passwordEncoder;

    protected UserCreateAdminUseCase(IUserGateway iUserGateway, PasswordEncoder passwordEncoder, List<UserCreateAdminValidation> userCreateAdminValidations) {
        this.iUserGateway = iUserGateway;
        this.userCreateAdminValidations = userCreateAdminValidations;
        this.passwordEncoder = passwordEncoder;
    }
    public static UserCreateAdminUseCase build(IUserGateway iUserGateway, ISessionGateway sessionGateway) {
        return new UserCreateAdminUseCase(iUserGateway, new BCryptPasswordEncoder(), List.of(UserAdminAllowedValidator.build(sessionGateway), new UserEmailAlreadyUsedValidator(iUserGateway), new UserLoginAlreadyUsedValidator(iUserGateway), new UserPasswordValidator()));
    }
    public UserDomain execute(UserDomain userDomain) {
        validate(userDomain);
        userDomain.setAdminRole();
        userDomain.setPassword(passwordEncoder.encode(userDomain.getPassword()));
        return iUserGateway.createUser(userDomain);
    }

    private void validate(UserDomain userDomain) {
        userCreateAdminValidations
                .forEach(restaurantCreationValidation ->
                        restaurantCreationValidation.validate(userDomain));
    }
}
