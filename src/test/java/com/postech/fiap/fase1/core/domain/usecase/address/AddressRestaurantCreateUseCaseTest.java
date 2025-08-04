package com.postech.fiap.fase1.core.domain.usecase.address;

import com.postech.fiap.fase1.core.domain.model.AddressDomain;
import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.core.domain.validation.address.implementation.AddressCreateToUserAllowedValidator;
import com.postech.fiap.fase1.core.gateway.address.IAddressGateway;
import com.postech.fiap.fase1.core.gateway.restaurant.IRestaurantGateway;
import com.postech.fiap.fase1.core.gateway.session.ISessionGateway;
import com.postech.fiap.fase1.webapi.infrastructure.exception.ApplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressRestaurantCreateUseCaseTest {

    @Mock
    private IAddressGateway addressGateway;
    @Mock
    private ISessionGateway sessionGateway;
    @Mock
    private IRestaurantGateway restaurantGateway;
    @Mock
    private AddressCreateToUserAllowedValidator addressCreateToUserAllowedValidator;
    @InjectMocks
    private AddressRestaurantCreateUseCase addressRestaurantCreateUseCase;

    @BeforeEach
    void setUp() {
        addressRestaurantCreateUseCase = new AddressRestaurantCreateUseCase(addressGateway, sessionGateway, restaurantGateway);
        try {
            java.lang.reflect.Field validatorField = AddressRestaurantCreateUseCase.class.getDeclaredField("addressCreateToUserAllowedValidator");
            validatorField.setAccessible(true);
            validatorField.set(addressRestaurantCreateUseCase, addressCreateToUserAllowedValidator);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void execute_deveCriarEnderecoParaRestaurante_quandoDadosValidos() {
        AddressDomain addressDomain = AddressDomain.builder().id(null).build();
        RestaurantDomain restaurantDomain = RestaurantDomain.builder().id(100L).build();
        addressDomain.setAddressable(RestaurantDomain.builder().id(100L).build());

        when(restaurantGateway.getOneById(100L)).thenReturn(restaurantDomain);
        doNothing().when(addressCreateToUserAllowedValidator).validate(addressDomain);
        when(addressGateway.createRestaurantAddress(addressDomain)).thenReturn(addressDomain);

        AddressDomain result = addressRestaurantCreateUseCase.execute(addressDomain);

        assertNotNull(result);
        assertEquals(addressDomain, result);
        verify(addressGateway, times(1)).createRestaurantAddress(addressDomain);
        verify(addressCreateToUserAllowedValidator, times(1)).validate(addressDomain);
        verify(restaurantGateway, times(1)).getOneById(100L);
    }

    @Test
    void execute_deveLancarExcecao_quandoValidacaoFalhar() {
        AddressDomain addressDomain = AddressDomain.builder().id(null).build();
        RestaurantDomain restaurantDomain = RestaurantDomain.builder().id(100L).build();
        addressDomain.setAddressable(RestaurantDomain.builder().id(100L).build());

        when(restaurantGateway.getOneById(100L)).thenReturn(restaurantDomain);
        doThrow(new ApplicationException("Validation failed")).when(addressCreateToUserAllowedValidator).validate(addressDomain);

        assertThrows(ApplicationException.class, () -> addressRestaurantCreateUseCase.execute(addressDomain));
        verify(addressGateway, never()).createRestaurantAddress(addressDomain);
        verify(addressCreateToUserAllowedValidator, times(1)).validate(addressDomain);
        verify(restaurantGateway, times(1)).getOneById(100L);
    }

    @Test
    void execute_deveLancarExcecao_quandoRestauranteNaoEncontrado() {
        AddressDomain addressDomain = AddressDomain.builder().id(null).build();
        addressDomain.setAddressable(RestaurantDomain.builder().id(100L).build());

        when(restaurantGateway.getOneById(100L)).thenReturn(null);

        assertThrows(ApplicationException.class, () -> addressRestaurantCreateUseCase.execute(addressDomain));
        verify(addressGateway, never()).createRestaurantAddress(addressDomain);
        verify(addressCreateToUserAllowedValidator, never()).validate(addressDomain);
        verify(restaurantGateway, times(1)).getOneById(100L);
    }
}