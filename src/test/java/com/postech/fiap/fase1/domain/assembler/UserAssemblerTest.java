package com.postech.fiap.fase1.domain.assembler;

import com.postech.fiap.fase1.domain.dto.UserInputDTO;
import com.postech.fiap.fase1.domain.dto.UserRequestDTO;
import com.postech.fiap.fase1.domain.dto.UserRequestUpdateDetailsDTO;
import com.postech.fiap.fase1.domain.dto.UserRequestUpdatePasswordDTO;
import com.postech.fiap.fase1.domain.model.Role;
import com.postech.fiap.fase1.domain.model.User;
import com.postech.fiap.fase1.environment.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserAssemblerTest {

    @Test
    void convertsRequestToInputSuccessfully() {
        UserRequestDTO requestDTO = EnvUserRequestDTO.getUserRequestDTO();

        UserInputDTO result = UserAssembler.requestToInput(requestDTO, Role.CLIENT);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(requestDTO.getName());
        assertThat(result.getEmail()).isEqualTo(requestDTO.getEmail());
        assertThat(result.getLogin()).isEqualTo(requestDTO.getLogin());
        assertThat(result.getPassword()).isEqualTo(requestDTO.getPassword());
        assertThat(result.getPasswordConfirmation()).isEqualTo(requestDTO.getPasswordConfirmation());
        assertThat(result.getBirthDate()).isEqualTo(requestDTO.getBirthDate());
        assertThat(result.getRole()).isEqualTo(Role.CLIENT);
    }

    @Test
    void convertsRequestUpdateDetailsToInputSuccessfully() {
        UserRequestUpdateDetailsDTO updateDetailsDTO = EnvUserRequestUpdateDetailsDTO.getUserRequestUpdateDetailsDTO();

        UserInputDTO result = UserAssembler.requestUpdateDetailsToInput(updateDetailsDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(updateDetailsDTO.getId());
        assertThat(result.getName()).isEqualTo(updateDetailsDTO.getName());
        assertThat(result.getEmail()).isEqualTo(updateDetailsDTO.getEmail());
        assertThat(result.getLogin()).isEqualTo(updateDetailsDTO.getLogin());
        assertThat(result.getBirthDate()).isEqualTo(updateDetailsDTO.getBirthDate());
    }

    @Test
    void convertsRequestUpdatePasswordToInputSuccessfully() {
        UserRequestUpdatePasswordDTO updatePasswordDTO = EnvUserRequestUpdatePasswordDTO.getUserRequestUpdatePasswordDTO();

        UserInputDTO result = UserAssembler.requestUpdatePasswordToInput(updatePasswordDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(updatePasswordDTO.getId());
        assertThat(result.getPassword()).isEqualTo(updatePasswordDTO.getPassword());
        assertThat(result.getPasswordConfirmation()).isEqualTo(updatePasswordDTO.getPasswordConfirmation());
    }

    @Test
    void convertsInputToModelSuccessfully() {
        UserInputDTO inputDTO = EnvUserInputDTO.getUserInputDTO();


        User result = UserAssembler.inputToModel(inputDTO, "encodedPassword");

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(inputDTO.getName());
        assertThat(result.getEmail()).isEqualTo(inputDTO.getEmail());
        assertThat(result.getLogin()).isEqualTo(inputDTO.getLogin());
        assertThat(result.getPassword()).isEqualTo("encodedPassword");
        assertThat(result.getBirthDate()).isEqualTo(inputDTO.getBirthDate());
        assertThat(result.getRole()).isEqualTo(inputDTO.getRole());
    }

    @Test
    void updatesUserDetailsSuccessfully() {
        User user = EnvUser.getUserClient();

        UserInputDTO inputDTO = EnvUserInputDTO.getUserInputDTO();

        UserAssembler.updateUserDetails(user, inputDTO);

        assertThat(user.getName()).isEqualTo(inputDTO.getName());
        assertThat(user.getEmail()).isEqualTo(inputDTO.getEmail());
        assertThat(user.getLogin()).isEqualTo(inputDTO.getLogin());
        assertThat(user.getBirthDate()).isEqualTo(inputDTO.getBirthDate());
    }
}