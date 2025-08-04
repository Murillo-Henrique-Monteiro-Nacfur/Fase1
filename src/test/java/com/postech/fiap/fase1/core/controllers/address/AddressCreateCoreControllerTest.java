package com.postech.fiap.fase1.core.controllers.address;

import com.postech.fiap.fase1.core.domain.model.AddressDomain;
import com.postech.fiap.fase1.core.domain.usecase.address.AddressRestaurantCreateUseCase;
import com.postech.fiap.fase1.core.domain.usecase.address.AddressUserCreateUseCase;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressCreateCoreControllerTest {

    @Mock
    private AddressUserCreateUseCase addressUserCreateUseCase;

    @Mock
    private AddressRestaurantCreateUseCase addressRestaurantCreateUseCase;

    @Mock
    private AddressPresenter addressPresenter;
    @InjectMocks
    private AddressCreateCoreController addressCreateCoreController;

    @BeforeEach
    void setUp() {
        addressCreateCoreController = new AddressCreateCoreController(addressUserCreateUseCase, addressRestaurantCreateUseCase, addressPresenter);
    }

    @Test
    void createUserAddress() {
        AddressRequestDTO addressRequestDTO = new AddressRequestDTO();
        Long idUser = 1L;
        AddressDomain addressDomain = com.postech.fiap.fase1.core.domain.model.AddressDomain.builder().build();
        AddressResponseDTO addressResponseDTO = new AddressResponseDTO();

        when(addressPresenter.requestToAddressUserDomain(addressRequestDTO, idUser)).thenReturn(addressDomain);
        when(addressUserCreateUseCase.execute(addressDomain)).thenReturn(addressDomain);
        when(addressPresenter.toResponseDTO(addressDomain)).thenReturn(addressResponseDTO);

        AddressResponseDTO result = addressCreateCoreController.createUserAddress(addressRequestDTO, idUser);

        assertEquals(addressResponseDTO, result);
    }

    @Test
    void createRestaurantAddress() {
        AddressRequestDTO addressRequestDTO = AddressRequestDTO.builder().build();
        Long idRestaurant = 1L;
        AddressDomain addressDomain = AddressDomain.builder().build();
        AddressResponseDTO addressResponseDTO = new AddressResponseDTO();

        when(addressPresenter.requestToAddressRestaurantDomain(addressRequestDTO, idRestaurant)).thenReturn(addressDomain);
        when(addressRestaurantCreateUseCase.execute(addressDomain)).thenReturn(addressDomain);
        when(addressPresenter.toResponseDTO(addressDomain)).thenReturn(addressResponseDTO);

        AddressResponseDTO result = addressCreateCoreController.createRestaurantAddress(addressRequestDTO, idRestaurant);

        assertEquals(addressResponseDTO, result);
    }
}