package com.postech.fiap.fase1.webapi.data.repository;

import com.postech.fiap.fase1.webapi.data.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findProductByRestaurantId(Long idRestaurant);

}
