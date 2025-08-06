package com.postech.fiap.fase1.core.domain.usecase.address;

import com.postech.fiap.fase1.core.domain.model.AddressDomain;
import com.postech.fiap.fase1.core.domain.model.AddressableDomain;
import com.postech.fiap.fase1.core.domain.validation.session.ISessionValidation;
import com.postech.fiap.fase1.core.gateway.address.IAddressGateway;
import com.postech.fiap.fase1.core.gateway.session.ISessionGateway;
import com.postech.fiap.fase1.webapi.infrastructure.exception.ApplicationException;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressReadUseCaseTest {

    @Mock
    private IAddressGateway addressGateway;
    @Mock
    private ISessionGateway sessionGateway;
    @Mock
    private ISessionValidation sessionValidation;
    @InjectMocks
    private AddressReadUseCase addressReadUseCase;

    @BeforeEach
    void setUp() {
        addressReadUseCase = new AddressReadUseCase(addressGateway, sessionGateway);
        try {
            java.lang.reflect.Field validatorField = AddressReadUseCase.class.getDeclaredField("sessionValidation");
            validatorField.setAccessible(true);
            validatorField.set(addressReadUseCase, sessionValidation);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getById_deveRetornarEndereco_quandoUsuarioAutorizado() {
        AddressableDomain addressable = mock(AddressableDomain.class);
        when(addressable.getIdUserOwner()).thenReturn(10L);
        AddressDomain addressDomain = AddressDomain.builder().id(1L).build();
        addressDomain.setAddressable(addressable);
        when(addressGateway.getOneByid(1L)).thenReturn(addressDomain);
        doNothing().when(sessionValidation).validate(10L);

        AddressDomain result = addressReadUseCase.getById(1L);
        assertNotNull(result);
        assertEquals(addressDomain, result);
        verify(sessionValidation, times(1)).validate(10L);
        verify(addressGateway, times(1)).getOneByid(1L);
    }

    @Test
    void getById_deveLancarExcecao_quandoUsuarioNaoAutorizado() {
        AddressableDomain addressable = mock(AddressableDomain.class);
        when(addressable.getIdUserOwner()).thenReturn(10L);
        AddressDomain addressDomain = AddressDomain.builder().id(1L).build();
        addressDomain.setAddressable(addressable);
        when(addressGateway.getOneByid(1L)).thenReturn(addressDomain);
        doThrow(new ApplicationException("Unauthorized")).when(sessionValidation).validate(10L);

        assertThrows(ApplicationException.class, () -> addressReadUseCase.getById(1L));
        verify(sessionValidation, times(1)).validate(10L);
        verify(addressGateway, times(1)).getOneByid(1L);
    }

    @Test
    void getById_deveLancarExcecao_quandoEnderecoNaoEncontrado() {
        when(addressGateway.getOneByid(2L)).thenReturn(null);
        assertThrows(NullPointerException.class, () -> addressReadUseCase.getById(2L));
        verify(addressGateway, times(1)).getOneByid(2L);
        verify(sessionValidation, never()).validate(anyLong());
    }

    @Test
    void getAllPaged_deveRetornarPaginaDeEnderecos() {
        Pageable pageable = PageRequest.of(0, 10);
        AddressDomain addressDomain = AddressDomain.builder().id(1L).build();
        Page<AddressDomain> page = new PageImpl<>(Collections.singletonList(addressDomain));
        when(addressGateway.getAllPaged(pageable)).thenReturn(page);

        Page<AddressDomain> result = addressReadUseCase.getAllPaged(pageable);
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(addressGateway, times(1)).getAllPaged(pageable);
    }
}