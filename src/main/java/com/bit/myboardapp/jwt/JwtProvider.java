package com.bit.myboardapp.jwt;

import com.bit.myboardapp.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtProvider {

    private static final String SECRET_KEY = "bXlib2FyZGFwcHNpbmdsZXByb2plY3RkZW1v";

    SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    public String createJwt(User user){
        Date expireDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .subject(user.getEmail())
                .issuer("My_Board_App")
                .issuedAt(new Date())
                .expiration(expireDate)
                .compact();
    }

    public String validatedAndGetSubject(String token){

        try{
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return claims.getSubject();
        } catch (ExpiredJwtException e){
            throw new RuntimeException("Expired JWT token", e);
        } catch (JwtException e){
            throw new RuntimeException("Invalid JWT token", e);
        }
    }

    public boolean isValidToken(String token){
        try{
            Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e){
            return false;
        }
    }
}
