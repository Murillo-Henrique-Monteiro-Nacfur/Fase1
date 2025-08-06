package com.postech.fiap.fase1.core.controllers.product;

import com.postech.fiap.fase1.core.domain.usecase.product.ProductDeleteUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductDeleteCoreControllerTest {
    @Mock
    private ProductDeleteUseCase productDeleteUseCase;
    @InjectMocks
    private ProductDeleteCoreController controller;

    @BeforeEach
    void setUp() {
        controller = new ProductDeleteCoreController(productDeleteUseCase);
    }

    @Test
    void testDeleteCallsUseCase() {
        Long productId = 123L;
        controller.delete(productId);
        verify(productDeleteUseCase, times(1)).delete(productId);
    }
}