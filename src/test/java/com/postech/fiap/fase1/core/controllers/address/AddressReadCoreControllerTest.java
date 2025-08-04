package com.postech.fiap.fase1.core.controllers.address;

import com.postech.fiap.fase1.core.domain.model.AddressDomain;
import com.postech.fiap.fase1.core.domain.usecase.address.AddressReadUseCase;
import com.postech.fiap.fase1.core.dto.address.AddressResponseDTO;
import com.postech.fiap.fase1.core.presenter.AddressPresenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressReadCoreControllerTest {

    @Mock
    private AddressReadUseCase addressReadUseCase;

    @Mock
    private AddressPresenter addressPresenter;
    @InjectMocks
    private AddressReadCoreController addressReadCoreController;

    @BeforeEach
    void setUp() {
        addressReadCoreController = new AddressReadCoreController(addressReadUseCase, addressPresenter);
    }

    @Test
    void findById() {
        Long id = 1L;
        AddressDomain addressDomain = AddressDomain.builder().build();
        AddressResponseDTO addressResponseDTO = new AddressResponseDTO();

        when(addressReadUseCase.getById(id)).thenReturn(addressDomain);
        when(addressPresenter.toResponseDTO(addressDomain)).thenReturn(addressResponseDTO);

        AddressResponseDTO result = addressReadCoreController.findById(id);

        assertEquals(addressResponseDTO, result);
    }

    @Test
    void findAllPaged() {
        Pageable pageable = PageRequest.of(0, 10);
        AddressDomain addressDomain = AddressDomain.builder().build();
        Page<AddressDomain> pageAddressDomain = new PageImpl<>(Collections.singletonList(addressDomain));
        AddressResponseDTO addressResponseDTO = new AddressResponseDTO();
        Page<AddressResponseDTO> pageAddressResponseDTO = new PageImpl<>(Collections.singletonList(addressResponseDTO));

        when(addressReadUseCase.getAllPaged(pageable)).thenReturn(pageAddressDomain);
        when(addressPresenter.toResponseDTO(pageAddressDomain)).thenReturn(pageAddressResponseDTO);

        Page<AddressResponseDTO> result = addressReadCoreController.findAllPaged(pageable);

        assertEquals(pageAddressResponseDTO, result);
    }
}