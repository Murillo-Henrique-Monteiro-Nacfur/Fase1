package com.postech.fiap.fase1.core.controllers.address;

import com.postech.fiap.fase1.core.domain.model.AddressDomain;
import com.postech.fiap.fase1.core.domain.usecase.address.AddressUpdateUseCase;
import com.postech.fiap.fase1.core.dto.address.AddressRequestDTO;
import com.postech.fiap.fase1.core.dto.address.AddressResponseDTO;
import com.postech.fiap.fase1.core.presenter.AddressPresenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddressUpdateCoreControllerTest {

    @Mock
    private AddressUpdateUseCase addressUpdateUseCase;

    @Mock
    private AddressPresenter addressPresenter;
    @InjectMocks
    private AddressUpdateCoreController controller;

    @BeforeEach
    void setUp() {
        controller = new AddressUpdateCoreController(addressUpdateUseCase, addressPresenter);
    }

    @Test
    void testUpdate() {
        AddressRequestDTO requestDTO = mock(AddressRequestDTO.class);

        AddressDomain addressDomain = AddressDomain.builder().build();
        AddressResponseDTO expectedResponse = new AddressResponseDTO();

        when(addressPresenter.requestUpdateToInput(requestDTO)).thenReturn(addressDomain);
        when(addressUpdateUseCase.execute(addressDomain)).thenReturn(addressDomain);
        when(addressPresenter.toResponseDTO(addressDomain)).thenReturn(expectedResponse);

        AddressResponseDTO response = controller.update(requestDTO);

        assertNotNull(response);
        assertEquals(expectedResponse, response);
    }
}