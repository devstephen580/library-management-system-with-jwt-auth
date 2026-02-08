package com.devstephen.library_management_system_with_jwt_auth.jwt;

import com.devstephen.library_management_system_with_jwt_auth.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  @Value("${jwt.secretkey}")
  private String secretKey;

  @Value("${jwt.expiration}")
  private Long jwtExpiration;

  public String extractUsername(String jwtToken) {
    return extractClaims(jwtToken, Claims::getSubject);
  }

  private <T> T extractClaims(String jwtToken, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(jwtToken);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String jwtToken) {
    return Jwts.parser()
        .verifyWith(getSignInKey())
        .build().parseSignedClaims(jwtToken)
        .getPayload();
  }

  private SecretKey getSignInKey() {
    return Keys.hmacShaKeyFor(secretKey.getBytes());
  }


  public boolean isTokenValid(String jwtToken, User userDetails) {
    final String username = extractUsername(jwtToken);

    return (userDetails.getUsername().equals(username) && !isTokenExpired(jwtToken));
  }

  private boolean isTokenExpired(String jwtToken) {
    return extractExpiration(jwtToken).before(new Date());
  }

  private Date extractExpiration(String jwtToken) {
    return extractClaims(jwtToken, Claims::getExpiration);
  }

  public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }


  public String generateToken (Map<String, Object> extractClaims, UserDetails userDetails){
    return Jwts.builder()
        .claims(extractClaims)
        .subject(userDetails.getUsername())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
        .signWith(getSignInKey())
        .compact();
  }



}
