package com.postech.fiap.fase1.infrastructure.controller.product;

import com.postech.fiap.fase1.core.controllers.product.ProductDeleteCoreController;
import com.postech.fiap.fase1.core.presenter.ProductPresenter;
import com.postech.fiap.fase1.infrastructure.data.DataRepository;
import com.postech.fiap.fase1.infrastructure.session.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductDeleteController implements ProductControllerInterface {

    private final ProductPresenter productPresenter;
    private final DataRepository dataRepository;
    private final SessionRepository sessionRepository;

    @PreAuthorize("hasRole('CLIENT')")
    @DeleteMapping("/{id}")
    public void createProduct(@PathVariable Long id) {
        ProductDeleteCoreController productDeleteCoreController = new ProductDeleteCoreController(dataRepository, sessionRepository);
        productDeleteCoreController.delete(id);
    }
}
