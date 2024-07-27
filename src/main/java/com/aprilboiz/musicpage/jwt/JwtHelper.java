package com.aprilboiz.musicpage.jwt;

import com.aprilboiz.musicpage.exception.AccessDeniedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@PropertySource(value = "classpath:jwt.properties")
public class JwtHelper {
    @Value("${jwt.token.secret}")
    private String SECRET;
    @Value("${jwt.token.expiration_time}")
    private Long EXPIRATION_TIME;

    public String generateToken(UserDetails userDetails){
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("authorities", userDetails.getAuthorities())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key)
                .compact();
    }

    private Claims extractBody(String token){
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }catch (SignatureException | ExpiredJwtException ex){
            if (ex instanceof ExpiredJwtException){
                throw new AccessDeniedException("Access denied: Token expired");
            }else {
                throw new AccessDeniedException("Access denied: Invalid token type");
            }
        }
    }

    public String extractUsername(String token){
        return extractBody(token).getSubject();
    }

    private boolean isTokenExpired(String token){
        return extractBody(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails){
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}
