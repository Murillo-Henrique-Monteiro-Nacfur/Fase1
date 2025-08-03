package com.postech.fiap.fase1.webapi.data.repository;

import com.postech.fiap.fase1.webapi.data.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findProductByRestaurantId(Long idRestaurant);

    @Query("SELECT p FROM Product p join fetch p.restaurant r join fetch r.user WHERE p.id = :idProduct")
    Optional<Product> findProductById(@Param("idProduct") Long idProduct);

}
