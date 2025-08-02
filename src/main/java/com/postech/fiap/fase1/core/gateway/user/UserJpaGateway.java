package com.postech.fiap.fase1.core.gateway.user;

import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.core.dto.user.UserDTO;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.presenter.UserPresenter;
import com.postech.fiap.fase1.infrastructure.exception.ApplicationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static java.util.Optional.empty;

public class UserJpaGateway implements UserGateway {

    private static final String USER_NOT_FOUND = "User not found";
    private final DataSource dataSource;
    private final UserPresenter userPresenter;

    public UserJpaGateway(DataSource dataSource) {
        this.dataSource = dataSource;
        this.userPresenter = new UserPresenter();
    }

    public UserDTO getOneUser(Long idUser) {
        return dataSource.getUserById(idUser)
                .orElseThrow(() -> new ApplicationException(USER_NOT_FOUND));
    }

    public boolean hasUserWithSameEmail(Long idUser, String email) {
        return dataSource.hasUserWithSameEmail(idUser, email);
    }

    @Transactional(readOnly = true)
    public boolean hasUserWithSameLogin(Long idUser, String login) {
        return dataSource.hasUserWithSameLogin(idUser, login);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDomain getUserById(Long idUser) {
        return userPresenter.toDomain(getOneUser(idUser));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDomain> getAllUserPaged(Pageable pageable) {
        return dataSource.getAllUserPaged(pageable).
                map(userPresenter::toDomain);
    }

    @Override
    @Transactional
    public UserDomain createUser(UserDomain userDomain) {
        return userPresenter.toDomain(dataSource.createUser(userPresenter.toDTO(userDomain)));
    }

    @Override
    @Transactional
    public UserDomain updateUser(UserDomain userDomain) {
        UserDTO userOld = getOneUser(userDomain.getId());
        userOld = userPresenter.updateToDTO(userDomain, userOld);
        return userPresenter.toDomain(dataSource.updateUser(userOld));
    }

    @Override
    public UserDomain updateUserPassoword(UserDomain userDomain) {
        UserDTO userOld = getOneUser(userDomain.getId());
        userOld.setPassword(userDomain.getPassword());
        return userPresenter.toDomain(dataSource.updateUserPassoword(userOld));
    }

    @Override
    public void deleteUser(UserDomain userDomain) {
        dataSource.deleteUserById(userDomain.getId());
    }

    @Override
    public Optional<UserDomain> getUserByLogin(String login) {
        return dataSource.getUserByLogin(login)
                .map(userPresenter::toDomain)
                .or(Optional::empty);
    }
}
