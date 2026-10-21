package com.salim.actora.auth.config;


import com.salim.actora.auth.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtConfig {

    @Value("${jwt.secret.key}")
    private String jwtSecret;

    public SecretKey secretKey(){

        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String createAccessToken(User user){

        return Jwts.builder()
                .subject(user.getUsername())
                .claim("id",user.getId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*10))
                .signWith(secretKey())
                .compact();

    }

    public String findUsernameByToken(String token){

        Claims claims = Jwts.parser()
                .verifyWith(secretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }


}
