package com.postech.fiap.fase1.core.controllers.restaurant;

import com.postech.fiap.fase1.core.domain.model.RestaurantDomain;
import com.postech.fiap.fase1.core.domain.usecase.restaurant.RestaurantUpdateUseCase;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantRequestUpdateDTO;
import com.postech.fiap.fase1.core.dto.restaurant.RestaurantResponseDTO;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.gateway.SessionSource;
import com.postech.fiap.fase1.core.presenter.RestaurantPresenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RestaurantUpdateCoreControllerTest {
    private RestaurantUpdateCoreController controller;
    private RestaurantUpdateUseCase restaurantUpdateUseCase;
    private RestaurantPresenter restaurantPresenter;

    @BeforeEach
    void setUp() throws Exception {
        controller = new RestaurantUpdateCoreController(mock(DataSource.class), mock(SessionSource.class));
        restaurantUpdateUseCase = mock(RestaurantUpdateUseCase.class);
        restaurantPresenter = mock(RestaurantPresenter.class);
        Field useCaseField = RestaurantUpdateCoreController.class.getDeclaredField("restaurantUpdateUseCase");
        useCaseField.setAccessible(true);
        useCaseField.set(controller, restaurantUpdateUseCase);
        Field presenterField = RestaurantUpdateCoreController.class.getDeclaredField("restaurantPresenter");
        presenterField.setAccessible(true);
        presenterField.set(controller, restaurantPresenter);
    }

    @Test
    void testUpdate_Success() {
        RestaurantRequestUpdateDTO requestDTO = mock(RestaurantRequestUpdateDTO.class);
        RestaurantDomain restaurantDomain = mock(RestaurantDomain.class);
        RestaurantResponseDTO responseDTO = mock(RestaurantResponseDTO.class);

        when(restaurantPresenter.toDomain(requestDTO)).thenReturn(restaurantDomain);
        when(restaurantUpdateUseCase.execute(restaurantDomain)).thenReturn(restaurantDomain);
        when(restaurantPresenter.toResponseDTO(restaurantDomain)).thenReturn(responseDTO);

        RestaurantResponseDTO result = controller.update(requestDTO);
        assertEquals(responseDTO, result);
    }
}