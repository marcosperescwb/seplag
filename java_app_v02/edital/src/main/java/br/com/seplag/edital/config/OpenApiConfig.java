package br.com.seplag.edital.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Edital API")
                        .version("1.0")
                        .description("API para gerenciar informações do Edital")
                        .termsOfService("http://seplag.com.br/terms/")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}