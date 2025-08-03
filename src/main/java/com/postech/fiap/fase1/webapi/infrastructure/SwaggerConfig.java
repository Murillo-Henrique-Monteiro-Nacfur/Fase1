package com.postech.fiap.fase1.webapi.infrastructure;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    public static final String X_AUTH_TOKEN_HEADER = "X-Auth-Token";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Api de Restaurantes")
                        .version("1.0.0")
                        .description("API desenvolvida como backend do restaurante."))
                .addSecurityItem(new SecurityRequirement().addList(X_AUTH_TOKEN_HEADER))
                .components(new Components()
                        .addSecuritySchemes(X_AUTH_TOKEN_HEADER,
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                                        .name(X_AUTH_TOKEN_HEADER)
                                        .description("Por favor, insira o token JWT completo no cabeçalho '" + X_AUTH_TOKEN_HEADER + "'.")));
    }

}
