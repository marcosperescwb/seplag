package br.com.seplag.edital.config;

import br.com.seplag.edital.interceptor.AuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    // Define os paths padrões do Swagger UI e SpringDoc OpenAPI
    private static final String[] SWAGGER_PATHS = {
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs", // Endpoint padrão da definição OpenAPI
            "/v3/api-docs/**" // Inclui subgroups, swagger-config, etc.
            // Adicionar "/webjars/**" se você servir webjars diretamente e precisar excluí-los
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/**") // Protege tudo
                .excludePathPatterns(SWAGGER_PATHS)     // Exclui documentação Swagger
                .excludePathPatterns("/api/v1/auth/**") // Exclui autenticação
                .excludePathPatterns("/public/**")      // Exemplo: Exclui uma área pública
                .excludePathPatterns("/error");         // Geralmente bom excluir a página de erro padrão
    }
}