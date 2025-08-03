package com.postech.fiap.fase1.core.controllers.user;

import com.postech.fiap.fase1.core.domain.usecase.user.UserUpdateDetailsUseCase;
import com.postech.fiap.fase1.core.domain.usecase.user.UserUpdatePasswordUseCase;
import com.postech.fiap.fase1.core.dto.user.UserRequestUpdateDetailsDTO;
import com.postech.fiap.fase1.core.dto.user.UserRequestUpdatePasswordDTO;
import com.postech.fiap.fase1.core.dto.user.UserResponseDTO;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.gateway.SessionSource;
import com.postech.fiap.fase1.core.gateway.session.SessionGateway;
import com.postech.fiap.fase1.core.gateway.user.UserGateway;
import com.postech.fiap.fase1.core.presenter.UserPresenter;
import com.postech.fiap.fase1.infrastructure.controller.user.UserControllerInterface;
import com.postech.fiap.fase1.infrastructure.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class UserUpdateCoreController implements UserControllerInterface {
    private static final String USER_IS_NOT_THE_SAME_AS_THE_ONE_IN_THE_REQUEST = "User is not the same as the one in the request";

    private final UserUpdateDetailsUseCase userUpdateDetailsUseCase;
    private final UserUpdatePasswordUseCase userUpdatePasswordUseCase;
    private final UserPresenter userPresenter;

    public UserUpdateCoreController(DataSource dataSource, SessionSource sessionSource) {
        var userJpaGateway = UserGateway.build(dataSource);
        var sessionGateway = SessionGateway.build(sessionSource);
        this.userUpdateDetailsUseCase = new UserUpdateDetailsUseCase(userJpaGateway, sessionGateway);
        this.userUpdatePasswordUseCase = new UserUpdatePasswordUseCase(userJpaGateway, sessionGateway);
        this.userPresenter = new UserPresenter();
    }

    public UserResponseDTO updateUserDetails(Long idUser, UserRequestUpdateDetailsDTO userRequestUpdateDetailsDTO) {
        if (idUser.equals(userRequestUpdateDetailsDTO.getId())) {
            return userPresenter.toResponseDTO(userUpdateDetailsUseCase.execute(userPresenter.requestUpdateDetailsToInput(userRequestUpdateDetailsDTO)));
        }
        throw new ApplicationException(USER_IS_NOT_THE_SAME_AS_THE_ONE_IN_THE_REQUEST, HttpStatus.BAD_REQUEST);
    }

    public UserResponseDTO updateUserPassword(Long idUser, UserRequestUpdatePasswordDTO userRequestUpdatePasswordDTO) {
        if (idUser.equals(userRequestUpdatePasswordDTO.getId())) {
            return userPresenter.toResponseDTO(userUpdatePasswordUseCase.execute(userPresenter.requestUpdatePasswordToInput(userRequestUpdatePasswordDTO)));
        }
        throw new ApplicationException(USER_IS_NOT_THE_SAME_AS_THE_ONE_IN_THE_REQUEST, HttpStatus.BAD_REQUEST);
    }

}
