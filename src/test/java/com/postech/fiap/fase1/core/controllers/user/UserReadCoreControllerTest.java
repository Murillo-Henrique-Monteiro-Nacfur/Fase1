package com.postech.fiap.fase1.core.controllers.user;

import com.postech.fiap.fase1.core.domain.usecase.user.UserReadUseCase;
import com.postech.fiap.fase1.core.dto.user.UserResponseDTO;
import com.postech.fiap.fase1.core.gateway.DataSource;
import com.postech.fiap.fase1.core.gateway.SessionSource;
import com.postech.fiap.fase1.core.presenter.UserPresenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserReadCoreControllerTest {

    @Mock
    private DataSource dataSource;
    @Mock
    private SessionSource sessionSource;
    @Mock
    private UserReadUseCase userReadUseCase;
    @Mock
    private UserPresenter userPresenter;

    @InjectMocks
    private UserReadCoreController userReadCoreController;

    @BeforeEach
    void setUp() throws Exception {
        userReadCoreController = Mockito.spy(new UserReadCoreController(dataSource, sessionSource));
        var userReadUseCaseField = UserReadCoreController.class.getDeclaredField("userReadUseCase");
        userReadUseCaseField.setAccessible(true);
        userReadUseCaseField.set(userReadCoreController, userReadUseCase);
        var userPresenterField = UserReadCoreController.class.getDeclaredField("userPresenter");
        userPresenterField.setAccessible(true);
        userPresenterField.set(userReadCoreController, userPresenter);
    }

    @Test
    @DisplayName("Deve retornar UserResponseDTO ao buscar por ID")
    void findById_shouldReturnUserResponseDTO() {
        Long id = 1L;
        var userDomain = Mockito.mock(com.postech.fiap.fase1.core.domain.model.UserDomain.class);
        var userResponseDTO = Mockito.mock(UserResponseDTO.class);
        when(userReadUseCase.getById(id)).thenReturn(userDomain);
        when(userPresenter.toResponseDTO(userDomain)).thenReturn(userResponseDTO);

        var result = userReadCoreController.findById(id);
        assertThat(result).isEqualTo(userResponseDTO);
    }

    @Test
    @DisplayName("Deve retornar página de UserResponseDTO ao buscar todos paginados")
    void findAll_shouldReturnPageOfUserResponseDTO() {
        Pageable pageable = Pageable.unpaged();
        var userDomainPage = new PageImpl<>(List.of(Mockito.mock(com.postech.fiap.fase1.core.domain.model.UserDomain.class)));
        var userResponseDTOPage = new PageImpl<>(List.of(Mockito.mock(UserResponseDTO.class)));
        when(userReadUseCase.getAllPaged(pageable)).thenReturn(userDomainPage);
        when(userPresenter.toResponseDTO(userDomainPage)).thenReturn(userResponseDTOPage);

        var result = userReadCoreController.findAll(pageable);
        assertThat(result).isEqualTo(userResponseDTOPage);
    }
}