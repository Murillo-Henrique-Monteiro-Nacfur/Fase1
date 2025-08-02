package com.postech.fiap.fase1.infrastructure.data.repository;

import com.postech.fiap.fase1.infrastructure.data.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @Query("""
            SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
            FROM Restaurant r
            WHERE (:idRestaurant IS NULL OR r.id <> :idRestaurant)
            AND r.cnpj = :cnpj
            """)
    boolean hasRestaurantWithCNPJ(@Param("idRestaurant") Long idRestaurant, @Param("cnpj") String cnpj);
}
