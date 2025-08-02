package com.postech.fiap.fase1.core.gateway.user;

import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.infrastructure.exception.ApplicationException;
import com.postech.fiap.fase1.infrastructure.data.entity.User;
import com.postech.fiap.fase1.infrastructure.data.mapper.UserMapper;
import com.postech.fiap.fase1.infrastructure.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserJpaGateway implements UserGateway {

    private static final String USER_NOT_FOUND = "User not found";
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ApplicationException(USER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public User getOne(Long id) {
        return findById(id);
    }

    @Transactional(readOnly = true)
    public boolean hasUserWithSameEmail(Long idUser, String email) {
        return userRepository.hasUserWithSameEmail(idUser, email);
    }

    @Transactional(readOnly = true)
    public boolean hasUserWithSameLogin(Long idUser, String login) {
        return userRepository.hasUserWithSameLogin(idUser, login);


    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDomain> getById(Long idRestaurent) {
        return userRepository
                .findById(idRestaurent)
                .map(userMapper::toDomain)
                .or(Optional::empty);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDomain> getAllPaged(Pageable pageable) {
        return userRepository.findAll(pageable).
                map(userMapper::toDomain);
    }

    @Override
    @Transactional
    public UserDomain create(UserDomain userDomain) {
        User user = userMapper.toEntity(userDomain);
        return userMapper.toDomain(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserDomain update(UserDomain userDomain) {
        User user = findById(userDomain.getId());
        user = userMapper.updateToEntity(userDomain, user);
        return userMapper.toDomain(userRepository.save(user));
    }

    @Override
    public UserDomain updatePassoword(UserDomain userDomain) {
        User user = findById(userDomain.getId());
        user.setPassword(userDomain.getPassword());
        return userMapper.toDomain(userRepository.save(user));
    }

    @Override
    public void delete(UserDomain userDomain) {
        userRepository.deleteById(userDomain.getId());
    }

    @Override
    public UserDomain getUserByLogin(String login) {
        return userMapper.toDomain(userRepository.findByLogin(login).orElseThrow(
                () -> new ApplicationException(USER_NOT_FOUND)));
    }
}
