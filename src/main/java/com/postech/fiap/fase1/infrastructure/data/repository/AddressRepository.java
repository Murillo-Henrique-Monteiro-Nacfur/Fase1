package com.postech.fiap.fase1.infrastructure.data.repository;

import com.postech.fiap.fase1.infrastructure.data.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("SELECT a FROM Address a WHERE a.addressable.id = :idUser AND a.addressableType = 'User'")
    Optional<Address> findByUser(@Param("idUser") Long idUser);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Address a WHERE a.addressable.id = :idUser AND a.addressableType = 'User'")
    boolean existsByUserId(Long idUser);
}
