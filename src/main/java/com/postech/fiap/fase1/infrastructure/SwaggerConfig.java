package com.postech.fiap.fase1.infrastructure;


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
                        .version("1.0.0")                   // Versão da sua API
                        .description("API RESTful desenvolvida em Spring Boot com autenticação JWT via cabeçalho " + X_AUTH_TOKEN_HEADER + "."))
                .addSecurityItem(new SecurityRequirement().addList(X_AUTH_TOKEN_HEADER)) // Aplica o esquema de segurança globalmente
                .components(new Components()
                        .addSecuritySchemes(X_AUTH_TOKEN_HEADER, // O nome do esquema de segurança
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.APIKEY) // Tipo de segurança: API Key
                                        .in(SecurityScheme.In.HEADER)    // Onde a chave será enviada: no cabeçalho
                                        .name(X_AUTH_TOKEN_HEADER)       // O nome exato do cabeçalho HTTP: X-Auth-Token
                                        .description("Por favor, insira o token JWT completo no cabeçalho '" + X_AUTH_TOKEN_HEADER + "'.")));
    }

}
