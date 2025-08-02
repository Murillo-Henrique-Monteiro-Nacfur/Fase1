package com.postech.fiap.fase1.infrastructure.data.mapper;

import com.postech.fiap.fase1.core.domain.model.UserDomain;
import com.postech.fiap.fase1.infrastructure.data.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final AddressMapper addressMapper;

    public User toEntity(UserDomain userDomain) {
        return User.builder()
                .id(userDomain.getId())
                .name(userDomain.getName())
                .login(userDomain.getLogin())
                .email(userDomain.getEmail())
                .password(userDomain.getPassword())
                .birthDate(userDomain.getBirthDate())
                .role(userDomain.getRole())
                .addresses(isNull(userDomain.getAddresses()) ? null : addressMapper.toEntity(userDomain.getAddresses()))
                .build();
    }

    public User updateToEntity(UserDomain userDomain, User user) {
        user.setName(userDomain.getName());
        user.setEmail(userDomain.getEmail());
        user.setLogin(userDomain.getLogin());
        user.setBirthDate(userDomain.getBirthDate());
        return user;
    }

    public UserDomain toDomain(User user) {
        return UserDomain.builder()
                .id(user.getId())
                .name(user.getName())
                .login(user.getLogin())
                .email(user.getEmail())
                .password(user.getPassword())
                .birthDate(user.getBirthDate())
                .role(user.getRole())
                .addresses(isNull(user.getAddresses()) ? null : addressMapper.toDomain(user.getAddresses()))
                .build();
    }

}
