package com.postech.fiap.fase1.infrastructure.data.repository;

import com.postech.fiap.fase1.infrastructure.data.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {


}
