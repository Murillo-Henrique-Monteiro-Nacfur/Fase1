package com.postech.fiap.fase1.core.controllers.restaurant;

import com.postech.fiap.fase1.core.domain.usecase.restaurant.RestaurantDeleteUseCase;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.gateway.SessionSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;

import static org.mockito.Mockito.*;

class RestaurantDeleteCoreControllerTest {
    private RestaurantDeleteCoreController controller;
    private RestaurantDeleteUseCase restaurantDeleteUseCase;

    @BeforeEach
    void setUp() throws Exception {
        controller = new RestaurantDeleteCoreController(mock(DataSource.class), mock(SessionSource.class));
        restaurantDeleteUseCase = mock(RestaurantDeleteUseCase.class);
        Field useCaseField = RestaurantDeleteCoreController.class.getDeclaredField("restaurantDeleteUseCase");
        useCaseField.setAccessible(true);
        useCaseField.set(controller, restaurantDeleteUseCase);
    }

    @Test
    void testDelete_Success() {
        Long id = 1L;
        doNothing().when(restaurantDeleteUseCase).execute(id);
        controller.delete(id);
        verify(restaurantDeleteUseCase, times(1)).execute(id);
    }

    @Test
    void testDelete_Exception() {
        Long id = 2L;
        doThrow(new RuntimeException("Erro ao deletar restaurante")).when(restaurantDeleteUseCase).execute(id);
        try {
            controller.delete(id);
        } catch (RuntimeException e) {
            assert(e.getMessage().contains("Erro ao deletar restaurante"));
        }
        verify(restaurantDeleteUseCase, times(1)).execute(id);
    }
}