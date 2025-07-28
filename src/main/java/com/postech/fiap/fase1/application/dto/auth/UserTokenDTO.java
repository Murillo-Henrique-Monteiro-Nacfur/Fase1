package com.postech.fiap.fase1.application.dto.auth;

import lombok.*;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserTokenDTO {
    private Long id;
    private String name;
    private String login;
    private String email;
    private String role;
    @Setter
    private Date expiration;
}
