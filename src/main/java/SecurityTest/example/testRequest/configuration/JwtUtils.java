package SecurityTest.example.testRequest.configuration;

import SecurityTest.example.testRequest.models.AppUser;
import SecurityTest.example.testRequest.repositories.AppUserRepository;
import SecurityTest.example.testRequest.services.AppUserService;
import SecurityTest.example.testRequest.services.dto.AppUserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final AppUserRepository appUserRepository;

    @Value("${jwt.base64-secret}")
    private String secretKey;

    @Value("${jwt.token-validity-in-seconds}")
    private String ExpirationTime;

    public String generateToken(String email) {

      AppUser appUser = appUserRepository.findByEmail(email).orElse(null);

      Map<String, Object> claims = new HashMap<>();
      return createToken(claims , appUser.getUsername());
    }

    private String createToken(Map<String, Object> claims, String username) {
      return Jwts.builder()
              .setClaims(claims)
              .setSubject(username)
              .setIssuedAt(new Date(System.currentTimeMillis()))
              .setExpiration(Date.from(Instant.now().plus(Long.parseLong(ExpirationTime), ChronoUnit.SECONDS))) // Fix: Convert Instant to Date              .signWith( getSignKey() , SignatureAlgorithm.HS256)
              .compact();
    }

  private Key getSignKey() {
      byte[] keyBytes = secretKey.getBytes();
      return new SecretKeySpec(keyBytes , SignatureAlgorithm.HS256.getJcaName());
  }

  public Boolean validateToken(String token , UserDetails userDetails) {
      String username = extractUsername(token);
      return (username.equals(userDetails.getUsername()) && !isExpiredToken(token));
  }

  private boolean isExpiredToken(String token) {
    return extractExpirationTime(token).before(new Date());
  }

  private Date extractExpirationTime(String token) {
      return extractClaims(token, Claims::getExpiration);
  }

  public String extractUsername(String token) {
      return extractClaims(token , Claims::getSubject);
  }

  private <T> T extractClaims(String token, Function<Claims , T> claimsResolver) {

      final Claims claims = extractAllClaims(token);
      return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
      return Jwts.parser()
              .setSigningKey(getSignKey())
              .parseClaimsJws(token)
              .getBody();
  }


}
