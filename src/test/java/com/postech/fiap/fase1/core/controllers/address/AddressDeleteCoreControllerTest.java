package com.postech.fiap.fase1.core.controllers.address;

import com.postech.fiap.fase1.core.domain.usecase.address.AddressDeleteUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
class AddressDeleteCoreControllerTest {

    @Mock
    private AddressDeleteUseCase addressDeleteUseCase;
    @InjectMocks
    private AddressDeleteCoreController addressDeleteCoreController;

    @BeforeEach
    void setUp() {
        addressDeleteCoreController = new AddressDeleteCoreController(addressDeleteUseCase);
    }

    @Test
    void delete() {
        Long id = 1L;

        addressDeleteCoreController.delete(id);

        verify(addressDeleteUseCase).execute(id);
    }
}