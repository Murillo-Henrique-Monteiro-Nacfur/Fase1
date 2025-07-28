package com.postech.fiap.fase1.infrastructure.persistence.mapper;

import com.postech.fiap.fase1.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.domain.model.UserDomain;
import com.postech.fiap.fase1.infrastructure.persistence.entity.Restaurant;
import com.postech.fiap.fase1.infrastructure.persistence.entity.User;
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
                .email(userDomain.getEmail())
                .password(userDomain.getPassword())
                .birthDate(userDomain.getBirthDate())
                .role(userDomain.getRole())
                .addresses(isNull(userDomain.getAddresses()) ? null : addressMapper.toEntity(userDomain.getAddresses()))
                .build();
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
