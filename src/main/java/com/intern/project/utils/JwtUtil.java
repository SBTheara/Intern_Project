package com.intern.project.utils;

import com.intern.project.dto.EmailPassResponse;
import com.intern.project.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  private static final long JWT_EXPIRATION = 360000;
  private static final String SECRET = "f027da2d7ab92525a5d128db793302e90805882ba3916e569b7759dcaab5bf52";

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public EmailPassResponse generateToken(User user) {

    Map<String, Object> claims = new HashMap<>();
    claims.put("email", user.getEmail());
    claims.put("family_name", user.getFirstName());
    claims.put("given_name", user.getLastName());
    claims.put("name", user.getUsername());
    var jwt =
        Jwts.builder()
            .claims(claims)
            .subject(user.getUserSubjectId())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
            .signWith(getSigningKey());
    jwt.header().type("JWT").keyId(String.valueOf(RandomUtils.nextLong()));
    return EmailPassResponse.builder().accessToken(jwt.compact()).build();
  }

  public long getExpirationTime() {
    return JWT_EXPIRATION;
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser()
        .verifyWith(getSigningSecretKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  private Key getSigningKey() {
    byte[] keyBytes = SECRET.getBytes(StandardCharsets.UTF_8);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  private SecretKey getSigningSecretKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
