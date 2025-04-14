package com.tasksapi.TaskList.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tasksapi.TaskList.domain.users.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.secret}")
    private String SECRET_KEY;

    public String generateToken(User user) {
        // Implementación de generación del token con JWT
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.create()
                    .withIssuer("Task_API")
                    .withSubject(user.getEmail())
                    .withClaim("id", user.getId())
                    .withExpiresAt(generarFechaExpiracion())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error al generar el token");
        }
    }

    public String getSubject(String token) {
        if (token == null) {
            throw new RuntimeException("Token vacío");
        }
        DecodedJWT decoded = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            decoded = JWT.require(algorithm)
                    // specify any specific claim validations
                    .withIssuer("Task_API")
                    // reusable verifier instance
                    .build()
                    .verify(token);
        } catch (TokenExpiredException exception){
            throw new RuntimeException("Token Expirado");
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Error: " + exception);
        }

        if (decoded.getSubject() == null) {
            throw new RuntimeException("El token no tiene un subjetivo");
        }
        return decoded.getSubject();
    }

    private Instant generarFechaExpiracion() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }
}
