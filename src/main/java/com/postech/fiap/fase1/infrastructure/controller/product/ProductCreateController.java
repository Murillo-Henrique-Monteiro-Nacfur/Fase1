package com.postech.fiap.fase1.infrastructure.controller.product;

import com.postech.fiap.fase1.core.controllers.product.ProductCreateCoreController;
import com.postech.fiap.fase1.core.dto.product.ProductDTO;
import com.postech.fiap.fase1.core.dto.product.ProductRequestDTO;
import com.postech.fiap.fase1.core.presenter.ProductPresenter;
import com.postech.fiap.fase1.infrastructure.data.DataRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductCreateController implements ProductControllerInterface {

    private final ProductPresenter productPresenter;
    private final DataRepository dataRepository;

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO) {
        ProductCreateCoreController productCreateCoreController = new ProductCreateCoreController(dataRepository);
        return ResponseEntity.status(HttpStatus.CREATED).body(productPresenter.toDTO(productCreateCoreController.createProduct(productRequestDTO)));
    }
}
