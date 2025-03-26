package com.tasktrackingsystem.util;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.Base64;
import java.util.Optional;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Component;
import com.tasktrackingsystem.constants.TaskConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;

@SuppressWarnings("deprecation")
@Component
public class UserAuthenticationTokenUtil {
	
    private static final long EXPIRATION_TIME_MILLIS = 1000 * 60 * 60 * 10; // 10 hours

    private static SecretKey getKey() {
        String secretKey = TaskConstants.SECURITY_KEY;
        byte[] keyBytes = Base64.getDecoder().decode(secretKey.getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec(keyBytes, TaskConstants.ALGORITHM);
    }

    public static String generateToken(String userId, String email) {
        return Jwts.builder()
                .setSubject(email)
                .claim(TaskConstants.USER_ID, userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MILLIS))
                .signWith(getKey())
                .compact();
    }

    public String extractUserEmail(String token) {
        return Optional.ofNullable(extractClaims(token)).map(Claims::getSubject).orElse(null);
    }
        
    public Long extractUserId(String token) {
      Claims claims = extractClaims(token);
      if (claims != null) {
          String userIdString = claims.get(TaskConstants.USER_ID, String.class);
          if (userIdString != null) {
              try {
                  return Long.parseLong(userIdString);
              } catch (NumberFormatException exception) {
                  throw new IllegalArgumentException(TaskConstants.INVALID_USER_TOKEN_FORMAT, exception);
              }
          }
      }
      throw new IllegalArgumentException(TaskConstants.INVALID_USER_ID_TOKEN);
   }

    public Claims extractClaims(String token) {
        try {
             return Jwts.parser().setSigningKey(getKey()).parseClaimsJws(token).getBody();
        } catch (JwtException | IllegalArgumentException e) {
          throw new IllegalArgumentException(TaskConstants.INVALID_USER_TOKEN_FORMAT, e);
      }
    }
    
    public boolean validateToken(String token) {
        String emailFromToken = extractUserEmail(token);
        return  emailFromToken !=null && !isTokenExpired(token);
    }
    
    public static String extractJwtToken(String authHeader) {
    	
        if (authHeader != null && authHeader.startsWith(TaskConstants.BEARER)) {
            return authHeader.substring(7);
        }
        throw new IllegalArgumentException(TaskConstants.INVALID_AUTHORIZATION_HEADER);
    }

    public boolean isTokenExpired(String token) {
    	
        Claims claims = extractClaims(token);
        return claims != null ? claims.getExpiration().before(new Date(System.currentTimeMillis())) : true;
    }

    public String extractUserEmailFromRequest(HttpServletRequest request) {
        String token = request.getHeader(TaskConstants.AUTHORIZATION);
        if (token != null && token.startsWith(TaskConstants.BEARER)) {
            token = token.substring(7);
            return extractUserEmail(token);
        }
       throw new IllegalArgumentException(TaskConstants.INVALID_AUTHORIZATION_HEADER_MISSING);
    }
}
