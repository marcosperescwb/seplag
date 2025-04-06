package br.com.seplag.edital.config; // Ajuste o pacote

import org.springframework.beans.factory.annotation.Value; // Importar @Value
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Value("${cors.allowed.origins:}")
    private String allowedOriginsProperty;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Divide a string da propriedade em um array, removendo espaços em branco
                String[] origins = allowedOriginsProperty.split("\\s*,\\s*");

                if (origins.length > 0 && !origins[0].isEmpty()) {
                    registry.addMapping("/**") // Aplica a todos os endpoints
                            // Usa o array de origens lido do properties
                            .allowedOrigins(origins)
                            .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS") // Adicione outros métodos se usar
                            .allowedHeaders("*") // Permite todos os headers comuns
                            .allowCredentials(true); // Importante se você usa cookies ou autenticação baseada em sessão/header
                    System.out.println("CORS configurado para permitir origens: " + allowedOriginsProperty); // Log para confirmação
                } else {
                    System.out.println("CORS: Nenhuma origem permitida configurada ou propriedade vazia. CORS estará desabilitado ou restrito.");
                }
            }
        };
    }
}