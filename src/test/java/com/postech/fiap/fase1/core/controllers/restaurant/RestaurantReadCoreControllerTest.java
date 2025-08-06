package com.postech.fiap.fase1.core.controllers.restaurant;

import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.core.domain.usecase.restaurant.RestaurantReadUseCase;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantResponseDTO;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.presenter.RestaurantPresenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Field;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RestaurantReadCoreControllerTest {
    private RestaurantReadCoreController controller;
    private RestaurantReadUseCase restaurantReadUseCase;
    private RestaurantPresenter restaurantPresenter;

    @BeforeEach
    void setUp() throws Exception {
        controller = new RestaurantReadCoreController(mock(DataSource.class));
        restaurantReadUseCase = mock(RestaurantReadUseCase.class);
        restaurantPresenter = mock(RestaurantPresenter.class);
        Field useCaseField = RestaurantReadCoreController.class.getDeclaredField("restaurantReadUseCase");
        useCaseField.setAccessible(true);
        useCaseField.set(controller, restaurantReadUseCase);
        Field presenterField = RestaurantReadCoreController.class.getDeclaredField("restaurantPresenter");
        presenterField.setAccessible(true);
        presenterField.set(controller, restaurantPresenter);
    }

    @Test
    void testFindById_Success() {
        Long id = 1L;
        RestaurantDomain restaurantDomain = mock(RestaurantDomain.class);
        RestaurantResponseDTO responseDTO = mock(RestaurantResponseDTO.class);

        when(restaurantReadUseCase.getById(id)).thenReturn(restaurantDomain);
        when(restaurantPresenter.toResponseDTO(restaurantDomain)).thenReturn(responseDTO);

        RestaurantResponseDTO result = controller.findById(id);
        assertEquals(responseDTO, result);
    }

    @Test
    void testGetAllPaged_Success() {
        Pageable pageable = mock(Pageable.class);
        RestaurantDomain restaurantDomain = mock(RestaurantDomain.class);
        Page<RestaurantDomain> domainPage = new PageImpl<>(Collections.singletonList(restaurantDomain));
        RestaurantResponseDTO responseDTO = mock(RestaurantResponseDTO.class);
        Page<RestaurantResponseDTO> responsePage = new PageImpl<>(Collections.singletonList(responseDTO));

        when(restaurantReadUseCase.getAllPaged(pageable)).thenReturn(domainPage);
        when(restaurantPresenter.toPageResponseDTOs(domainPage)).thenReturn(responsePage);

        Page<RestaurantResponseDTO> result = controller.getAllPaged(pageable);
        assertEquals(responsePage, result);
    }
}