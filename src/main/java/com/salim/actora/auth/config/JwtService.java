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
import java.util.Map;
import java.util.UUID;

@Component
public class JwtService {

    @Value("${jwt.secret.key}")
    private String jwtSecret;

    @Value("${jwt.access.token.expiration.time}")
    private Long accessTokenTimeLimit;

    @Value("${jwt.refresh.token.expiration.time}")
    private Long refreshTokenTimeLimit;

    public SecretKey secretKey(){

        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String createAccessToken(User user){

        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject(user.getUsername())
                .claims(Map.of("type","access"))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+accessTokenTimeLimit))
                .signWith(secretKey())
                .compact();

    }

    public String createRefreshToken(User user,String jti){

        return Jwts.builder()
                .id(jti)
                .subject(user.getId().toString())
                .claims(Map.of("type","refresh"))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+refreshTokenTimeLimit))
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

    public Claims parse(String token){

        return Jwts.parser()
                .verifyWith(secretKey()).build()
                .parseSignedClaims(token).getPayload();
    }

    public UUID getJtiFromToken(String token){

        return UUID.fromString(parse(token).getId());
    }

    public UUID getUserIdFromToken(String token){

        return UUID.fromString(parse(token).getSubject());
    }

    public boolean isRefreshToken(String token){

        return parse(token).get("type").equals("refresh");
    }

    public boolean isAccessToken(String token){

        return parse(token).get("type").equals("access");
    }


}
