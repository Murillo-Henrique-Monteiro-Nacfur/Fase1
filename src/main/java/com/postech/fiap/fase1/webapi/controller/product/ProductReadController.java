package com.postech.fiap.fase1.webapi.controller.product;

import com.postech.fiap.fase1.core.controllers.product.ProductReadCoreController;
import com.postech.fiap.fase1.core.dto.product.ProductResponseDTO;
import com.postech.fiap.fase1.webapi.data.DataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductReadController implements ProductControllerInterface {

    private final DataRepository dataRepository;

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id) {
        ProductReadCoreController productReadCoreController = new ProductReadCoreController(dataRepository);
        return ResponseEntity.status(HttpStatus.OK).body(productReadCoreController.getById(id));
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/restaurant/{idRestaurant}")
    public ResponseEntity<List<ProductResponseDTO>> findByIdRestaurant(@PathVariable Long idRestaurant) {
        ProductReadCoreController productReadCoreController = new ProductReadCoreController(dataRepository);
        return ResponseEntity.status(HttpStatus.OK).body(productReadCoreController.getByIdRestaurant(idRestaurant));
    }
}
