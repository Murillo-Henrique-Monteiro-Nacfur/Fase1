package com.postech.fiap.fase1.infrastructure.controller.product;

import com.postech.fiap.fase1.core.controllers.product.ProductCreateCoreController;
import com.postech.fiap.fase1.core.controllers.product.ProductReadCoreController;
import com.postech.fiap.fase1.core.dto.product.ProductDTO;
import com.postech.fiap.fase1.core.presenter.ProductPresenter;
import com.postech.fiap.fase1.infrastructure.data.DataRepository;
import com.postech.fiap.fase1.infrastructure.session.SessionRepository;
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

    private final ProductPresenter productPresenter;
    private final DataRepository dataRepository;

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        ProductReadCoreController productReadCoreController = new ProductReadCoreController(dataRepository);
        return ResponseEntity.status(HttpStatus.CREATED).body(productPresenter.toDTO(productReadCoreController.getById(id)));
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/restaurant/{idRestaurant}")
    public ResponseEntity<List<ProductDTO>> findByIdRestaurant(@PathVariable Long idRestaurant) {
        ProductReadCoreController productReadCoreController = new ProductReadCoreController(dataRepository);
        return ResponseEntity.status(HttpStatus.CREATED).body(productReadCoreController.getByIdRestaurant(idRestaurant).stream().map(productPresenter::toDTO).toList());
    }
}
