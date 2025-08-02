package com.postech.fiap.fase1.infrastructure.data.repository;

import com.postech.fiap.fase1.infrastructure.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.login = :login")
    Optional<User> findByLogin(@Param("login") String login);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("""
            SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END
            FROM User u
            WHERE u.id <> :idUser
            AND (u.email = :email)
            """)
    boolean hasUserWithSameEmail(@Param("idUser") Long idUser,@Param("email") String email);

    @Query("""
            SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END
            FROM User u
            WHERE u.id <> :idUser
            AND (u.login = :login)
            """)
    boolean hasUserWithSameLogin(@Param("idUser") Long idUser,@Param("login") String login);
}
