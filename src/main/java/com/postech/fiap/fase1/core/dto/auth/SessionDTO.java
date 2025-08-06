package com.postech.fiap.fase1.core.dto.auth;


import com.postech.fiap.fase1.webapi.data.entity.Role;
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

    public SessionDTO(UserTokenBodyDTO userTokenBodyDTO) {
        this.userId = userTokenBodyDTO.getId();
        this.userName = userTokenBodyDTO.getName();
        this.userLogin = userTokenBodyDTO.getLogin();
        this.userEmail = userTokenBodyDTO.getEmail();
        this.userRole = userTokenBodyDTO.getRole();
    }

    public boolean isAdmin() {
        return Role.ADMIN.name().equals(this.userRole);
    }

    public boolean isNotAdmin() {
        return !isAdmin();
    }
}
