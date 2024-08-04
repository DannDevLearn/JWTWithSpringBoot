package com.example.jwt.config.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtils {

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    @Value("${jwt.time.expiration}")
    private String TIME_EXPIRATION;

    // Step 1: Generate Access token
    public String generateAccessToken(String email){
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + Long.parseLong(TIME_EXPIRATION)))
                .signWith(getSignatureKey())
                .compact();
    }

    // Get Sign of token
    public Key getSignatureKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

    public String getUserNameSubject(String token){ // Username lo estamos manejando con email
        return getClaim(token, Claims::getSubject);
    }

    public <T> T getClaim(String authToken, Function<Claims, T> claimsResolver){
        Claims payload = Jwts.parser()
                .verifyWith((SecretKey) getSignatureKey())
                .build()
                .parseSignedClaims(authToken)
                .getPayload();
        return claimsResolver.apply(payload);
    }
    
    public boolean isTokenValid(String authToken){
        try{
            Jwts.parser()
                    .verifyWith((SecretKey) getSignatureKey())
                    .build()
                    .parseSignedClaims(authToken);
            return true;
        }catch (MalformedJwtException e){
            log.error("Invalid JWT token: {}", e.getMessage());
        }catch (ExpiredJwtException e){
            log.error("JWT token is expired: {}", e.getMessage());
        }catch (UnsupportedJwtException e){
            log.error("JWT token is unsupported: {}", e.getMessage());
        }catch (IllegalArgumentException e){
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

}
