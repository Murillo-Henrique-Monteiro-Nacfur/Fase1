package com.postech.fiap.fase1.domain.repository;

import com.postech.fiap.fase1.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.login = :login")
    Optional<User> findByLogin(@Param("login") String login);

}
