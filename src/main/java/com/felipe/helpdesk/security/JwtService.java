package com.felipe.helpdesk.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${jwt.secret:SECRET_KEY}")
    private String SECRET_KEY;
    @Value("${jwt.expiration:EXPIRATION}")
    private Integer EXPIRATION;

    // Extrai o Username, no caso o email
    public String extrairUsername(String tokenJwt) {
        return extrairClaim(tokenJwt, Claims::getSubject);
    }

    // Extrai qualquer claims do tokenJWT
    public <T> T extrairClaim(String tokenJwt, Function<Claims, T> claimsResolver){
        final Claims claims = extrairTodasClaims(tokenJwt);
        return claimsResolver.apply(claims);
    }

    // Gera o token JWT sem Claims extras
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    // Gera o token JWT com Claims extras
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Verifica se o username do Token e o mesmo do usuario cadastrado
    // Verifica se o token nao esta espirado
    public boolean isTokenValid(String tokenJwt, UserDetails userDetails){
        final String username = extrairUsername(tokenJwt);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(tokenJwt);
    }

    // Informa se o token esta expirado
    private boolean isTokenExpired(String tokenJwt) {
        return extrairExpiration(tokenJwt).before(new Date());
    }

    // Extrai a data de expiracao do token
    private Date extrairExpiration(String tokenJwt) {
        return extrairClaim(tokenJwt, Claims::getExpiration);
    }

    // Extrai todas as claims do tokenJWT
    private Claims extrairTodasClaims(String tokenJwt){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(tokenJwt)
                .getBody();
    }

    // Decodifica a chave de login
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
