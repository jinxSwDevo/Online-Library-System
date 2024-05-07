package com.software2.authenticationService.security.auth;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@Component
@RequiredArgsConstructor
public class JwtService {
    
    @Value("${security.jwt.secret-key}")
    private static final String SECRET_KEY = "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEnYMBd4/Q6YWtldRBedeStLn3mPTSukwbneK2b1RLEapCAjGlDLifVWSci5niqBsZZ5VAWnLwT8lYD8qWVKC4zA==";
    
    
    public String getJwtFromHeader(HttpServletRequest request){
        return request.getHeader("Authorization").substring(7);
    }
    
    //subject is  username or useremail
    public String getUserNameFromJwtToken(String token){
        return getClaim(token, Claims::getSubject);
    }

    public boolean isJWTTokenFound(HttpServletRequest req){
        String authHeader = req.getHeader("Autherization");

        if(authHeader == null || authHeader.startsWith("Bearer")){
            return false;
        }
        return true;
    }

    //is token belongs to user
    public boolean isJWTTokenValid(String token ,UserDetails userDetails){
        String userName = getUserNameFromJwtToken(token);
        return userName.equals(userDetails.getUsername()) && isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }
    

    public <T> T getClaim(String token , Function <Claims , T> claimFunction){
        final Claims claims = getAllClaimsFromToken(token);
        return claimFunction.apply(claims);
    }


    public Claims getAllClaimsFromToken(String token){
            return Jwts
                        .parserBuilder().setSigningKey(getKey()).build()
                        .parseClaimsJws(token).getBody();
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

    public String generateJwtToken(Map<String,Object> extraClaims,UserDetails userDetails){
        return Jwts.builder()
                    .setClaims(extraClaims)
                    .setSubject(userDetails.getUsername())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 100 * 60 * 48))
                    .signWith(getKey(), SignatureAlgorithm.HS256)
                    .compact();
    }

    //generate without claims
    public String generateJwtToken(UserDetails userDetails){
        return generateJwtToken(new HashMap<>(), userDetails);
    }

}
