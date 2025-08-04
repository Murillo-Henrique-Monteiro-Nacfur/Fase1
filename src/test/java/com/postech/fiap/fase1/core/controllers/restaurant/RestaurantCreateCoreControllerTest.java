package com.postech.fiap.fase1.core.controllers.restaurant;

import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.core.domain.usecase.restaurant.RestaurantCreateUseCase;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantRequestDTO;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantResponseDTO;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.gateway.SessionSource;
import com.postech.fiap.fase1.core.presenter.RestaurantPresenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RestaurantCreateCoreControllerTest {
    private RestaurantCreateCoreController controller;
    private RestaurantCreateUseCase restaurantCreateUseCase;
    private RestaurantPresenter restaurantPresenter;

    @BeforeEach
    void setUp() throws Exception {
        controller = new RestaurantCreateCoreController(mock(DataSource.class), mock(SessionSource.class));
        restaurantCreateUseCase = mock(RestaurantCreateUseCase.class);
        restaurantPresenter = mock(RestaurantPresenter.class);
        Field useCaseField = RestaurantCreateCoreController.class.getDeclaredField("restaurantCreateUseCase");
        useCaseField.setAccessible(true);
        useCaseField.set(controller, restaurantCreateUseCase);
        Field presenterField = RestaurantCreateCoreController.class.getDeclaredField("restaurantPresenter");
        presenterField.setAccessible(true);
        presenterField.set(controller, restaurantPresenter);
    }

    @Test
    void testCreate_Success() {
        RestaurantRequestDTO requestDTO = mock(RestaurantRequestDTO.class);
        RestaurantDomain restaurantDomain = mock(RestaurantDomain.class);
        RestaurantResponseDTO responseDTO = mock(RestaurantResponseDTO.class);

        when(restaurantPresenter.toDomain(requestDTO)).thenReturn(restaurantDomain);
        when(restaurantCreateUseCase.execute(restaurantDomain)).thenReturn(restaurantDomain);
        when(restaurantPresenter.toResponseDTO(restaurantDomain)).thenReturn(responseDTO);

        RestaurantResponseDTO result = controller.create(requestDTO);
        assertEquals(responseDTO, result);
    }

    @Test
    void testCreate_Exception() {
        RestaurantRequestDTO requestDTO = mock(RestaurantRequestDTO.class);
        RestaurantDomain restaurantDomain = mock(RestaurantDomain.class);
        when(restaurantPresenter.toDomain(requestDTO)).thenReturn(restaurantDomain);
        when(restaurantCreateUseCase.execute(restaurantDomain)).thenThrow(new RuntimeException("Erro ao criar restaurante"));
        try {
            controller.create(requestDTO);
        } catch (RuntimeException e) {
            assert(e.getMessage().contains("Erro ao criar restaurante"));
        }
    }
}