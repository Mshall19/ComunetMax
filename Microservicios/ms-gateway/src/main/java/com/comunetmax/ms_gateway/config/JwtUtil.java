package com.comunetmax.ms_gateway.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;

@Component
public class JwtUtil {
    // IMPORTANTE: Usa la misma lógica de llave que en Usuarios para que coincidan
    private static final String SECRET_KEY = "MiClaveSecretaQueNadieDebeSaber12345678";
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}