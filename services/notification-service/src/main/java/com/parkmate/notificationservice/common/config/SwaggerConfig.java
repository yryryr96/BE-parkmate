package com.parkmate.notificationservice.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String BEARER_TOKEN_PREFIX = "Bearer";

    @Bean
    public OpenAPI openAPI() {
        String securityJwtName = "JWT";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(securityJwtName);
        Components components = new Components()
                .addSecuritySchemes("X-User-UUID", new SecurityScheme()
                        .type(SecurityScheme.Type.APIKEY)
                        .in(SecurityScheme.In.HEADER)
                        .name("X-User-UUID")
                        .description("사용자 UUID"))
                .addSecuritySchemes("X-Host-UUID", new SecurityScheme()
                        .type(SecurityScheme.Type.APIKEY)
                        .in(SecurityScheme.In.HEADER)
                        .name("X-Host-UUID")
                        .description("호스트 UUID"))
                .addSecuritySchemes(securityJwtName, new SecurityScheme()
                        .name(securityJwtName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme(BEARER_TOKEN_PREFIX)
                        .bearerFormat(securityJwtName));

        return new OpenAPI()
                .addSecurityItem(securityRequirement)
                .components(components)
                .addServersItem(new Server().url("/notification-service"))
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("NOTIFICATION-SERVICE API DOCS")
                .description("notification-service API 테스트를 위한 Swagger UI")
                .version("1.0.0");
    }
}
