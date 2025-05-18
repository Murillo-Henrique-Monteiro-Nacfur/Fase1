package com.postech.fiap.fase1.domain.assembler;

import com.postech.fiap.fase1.domain.dto.UserDTO;
import com.postech.fiap.fase1.domain.model.User;
import com.postech.fiap.fase1.environment.EnvUser;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import static org.assertj.core.api.Assertions.assertThat;

class UserDisassemblerTest {

    @Test
    void convertsUserToDTOSuccessfully() {
        User user = EnvUser.getUserAdmin();

        UserDTO userDTO = UserDisassembler.toDTO(user);

        assertThat(userDTO).isNotNull();
        assertThat(userDTO.getId()).isEqualTo(user.getId());
        assertThat(userDTO.getName()).isEqualTo(user.getName());
        assertThat(userDTO.getEmail()).isEqualTo(user.getEmail());
        assertThat(userDTO.getLogin()).isEqualTo(user.getLogin());
        assertThat(userDTO.getRole()).isEqualTo(user.getRole());
        assertThat(userDTO.getBirthDate()).isEqualTo(user.getBirthDate().toString());
    }

    @Test
    void convertsEmptyPageToDTO() {
        Page<User> emptyPage = Page.empty();

        Page<UserDTO> result = UserDisassembler.toDTO(emptyPage);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEmpty();
    }

    @Test
    void convertsPageOfUsersToDTO() {
        Page<User> userPage = new PageImpl<>(EnvUser.getListUsers());

        Page<UserDTO> result = UserDisassembler.toDTO(userPage);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(2);

        for (int i = 0; i < 2; i++) {
            UserDTO userDTO = result.getContent().get(i);
            User user = userPage.toList().get(i);
            assertThat(userDTO.getId()).isEqualTo(user.getId());
            assertThat(userDTO.getName()).isEqualTo(user.getName());
            assertThat(userDTO.getEmail()).isEqualTo(user.getEmail());
            assertThat(userDTO.getLogin()).isEqualTo(user.getLogin());
            assertThat(userDTO.getRole()).isEqualTo(user.getRole());
            assertThat(userDTO.getBirthDate()).isEqualTo(user.getBirthDate().toString());
        }

//        User user2 = userPage.toList().get(1);
//        UserDTO userDTO2 = result.getContent().get(1);
//        assertThat(userDTO2.getId()).isEqualTo(user2.getId());
//        assertThat(userDTO2.getName()).isEqualTo(user2.getName());
//        assertThat(userDTO2.getEmail()).isEqualTo(user2.getEmail());
//        assertThat(userDTO2.getLogin()).isEqualTo(user2.getLogin());
//        assertThat(userDTO2.getRole()).isEqualTo(user2.getRole());
//        assertThat(userDTO2.getBirthDate()).isEqualTo(user2.getBirthDate().toString());
    }
}