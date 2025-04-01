package br.com.seplag.edital.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final Map<String, Long> tokens = new ConcurrentHashMap<>(); // Armazena os tokens e seus tempos de expiração

    @Value("${authentication.token.expiration-time}")
    private long EXPIRATION_TIME; // Tempo de expiração do token em milissegundos

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");

        if (token == null || !isValidToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false; // Interrompe a requisição
        }

        return true; // Permite que a requisição continue
    }

    private boolean isValidToken(String token) {
        System.out.println("EXPIRATION_TIME: " + EXPIRATION_TIME); // Adicione este log
        if (EXPIRATION_TIME == -1) {
            return true; // Tempo de expiração desabilitado
        }

        if (!tokens.containsKey(token)) {
            return false; // Token inválido
        }
        long expirationTime = tokens.get(token);
        return System.currentTimeMillis() < expirationTime; // Verifica se o token expirou
    }

    // Método para gerar um novo token
    public synchronized String generateToken() {
        String token = UUID.randomUUID().toString();
        long expiration = (EXPIRATION_TIME == -1) ? Long.MAX_VALUE : System.currentTimeMillis() + EXPIRATION_TIME;
        tokens.put(token, System.currentTimeMillis() + EXPIRATION_TIME);
        return token;
    }

    // Método para renovar um token
    public synchronized String renewToken(String token) {
        if (!tokens.containsKey(token)) {
            return null; // Token inválido
        }


        tokens.put(token, System.currentTimeMillis() + EXPIRATION_TIME);
        return token;
    }
}