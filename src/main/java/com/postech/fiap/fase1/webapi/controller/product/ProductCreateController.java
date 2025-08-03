package com.postech.fiap.fase1.webapi.controller.product;

import com.postech.fiap.fase1.core.controllers.product.ProductCreateCoreController;
import com.postech.fiap.fase1.core.dto.product.ProductRequestDTO;
import com.postech.fiap.fase1.core.dto.product.ProductResponseDTO;
import com.postech.fiap.fase1.webapi.data.DataRepository;
import com.postech.fiap.fase1.webapi.infrastructure.session.SessionRepository;
import com.postech.fiap.fase1.webapi.infrastructure.storage.StorageRepository;
import com.postech.fiap.fase1.webapi.utils.InputOutputFilesUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductCreateController implements ProductControllerInterface {

    private final DataRepository dataRepository;
    private final InputOutputFilesUtils inputOutputFilesUtils;
    private final SessionRepository sessionRepository;
    private final StorageRepository storageRepository;

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestPart("file") MultipartFile file, @Valid @RequestPart("product") ProductRequestDTO product) {
        ProductCreateCoreController productCreateCoreController = new ProductCreateCoreController(dataRepository, sessionRepository, storageRepository);
        return ResponseEntity.status(HttpStatus.CREATED).body(productCreateCoreController.createProduct(product, inputOutputFilesUtils.getArrayBytes(file)));
    }
}
