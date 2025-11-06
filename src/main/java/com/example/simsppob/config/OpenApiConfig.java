package com.example.simsppob.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {
        @Bean
        public OpenAPI myOpenAPI() {
                Server localServer = new Server();
                localServer.setUrl("https://take-home-test-api.nutech-integrasi.com");
                localServer.setDescription("Server Development");

                Contact contact = new Contact();
                contact.setName("API Contract SIMS PPOB");

                License license = new License()
                                .name("Mit License")
                                .url("https://mit-test.com");

                Info info = new Info()
                                .title("API Contract SIMS PPOB")
                                .contact(contact)
                                .description("Documentation for Take Home Test API")
                                .termsOfService("http://www.test.com/term")
                                .license(license);

                return new OpenAPI().info(info).servers(List.of(localServer))
                                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                                .components(new Components()
                                                .addSecuritySchemes("Bearer Authentication",
                                                                new SecurityScheme()
                                                                                .name("Bearer Authentication")
                                                                                .type(SecurityScheme.Type.HTTP)
                                                                                .scheme("bearer")
                                                                                .bearerFormat("JWT")));

        }
}
