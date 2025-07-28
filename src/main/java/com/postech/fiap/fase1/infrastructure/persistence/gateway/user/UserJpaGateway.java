package com.postech.fiap.fase1.infrastructure.persistence.gateway.user;

import com.postech.fiap.fase1.domain.model.UserDomain;
import com.postech.fiap.fase1.infrastructure.exception.ApplicationException;
import com.postech.fiap.fase1.infrastructure.persistence.entity.Restaurant;
import com.postech.fiap.fase1.infrastructure.persistence.entity.User;
import com.postech.fiap.fase1.infrastructure.persistence.mapper.UserMapper;
import com.postech.fiap.fase1.infrastructure.persistence.repository.UserRepository;
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
    public UserDomain createOrUpdate(UserDomain userDomain) {
        final User user = userMapper.toEntity(userDomain);
        return userMapper.toDomain(userRepository.save(user));
    }


//
//    public Page<User> findAll(Pageable pageable) {
//        return userRepository.findAll(pageable);
//    }
//
//    public User create(UserDomain userDomain) {
//        userValidator.validateUserCreation(userDomain);
//        User user = UserAssembler.inputToModel(userDomain, passwordEncoder.encode(userDomain.getPassword()));
//        return userRepository.save(user);
//    }
//
//    public User updateDetails(SessionDTO sessionDTO, UserDomain userDomain) {
//        User user = findById(userDomain.getId());
//        userValidator.validateUserUpdateDetails(sessionDTO, userDomain);
//        UserAssembler.updateUserDetails(user, userDomain);
//        return userRepository.save(user);
//    }
//
//    public User updatePassword(SessionDTO sessionDTO, UserDomain userDomain) {
//        User user = findById(userDomain.getId());
//        userValidator.validateUserUpdatePassword(sessionDTO, userDomain);
//        user.setPassword(passwordEncoder.encode(userDomain.getPassword()));
//        return userRepository.save(user);
//    }
//
//    public void delete(SessionDTO sessionDTO, Long idUser) {
//        User user = findById(idUser);
//        userValidator.verifyUserLoggedIsAdminOrOwner(sessionDTO, user.getId());
//        userRepository.delete(user);
//    }
}
