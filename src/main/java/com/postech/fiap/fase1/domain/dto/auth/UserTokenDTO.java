package com.postech.fiap.fase1.domain.dto.auth;

import lombok.*;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserTokenDTO {
    private String name;
    private String login;
    private String email;
    private String role;
    @Setter
    private Date expiration;
}
