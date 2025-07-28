package com.postech.fiap.fase1.application.dto.auth;


import com.postech.fiap.fase1.infrastructure.persistence.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class SessionDTO {
    private Long userId;
    private String userName;
    private String userLogin;
    private String userEmail;
    private String userRole;

    public SessionDTO(UserTokenDTO userTokenDTO) {
        this.userId = userTokenDTO.getId();
        this.userName = userTokenDTO.getName();
        this.userLogin = userTokenDTO.getLogin();
        this.userEmail = userTokenDTO.getEmail();
        this.userRole = userTokenDTO.getRole();
    }

    public boolean isAdmin() {
        return Role.ADMIN.name().equals(this.userRole);
    }

    public boolean isNotAdmin() {
        return !isAdmin();
    }
}
