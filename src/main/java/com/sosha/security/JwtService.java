package com.sosha.security;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date; import java.util.Map;
@Component
public class JwtService {
  private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
  public String generate(String userId, String tenantId, String branchId, String role) {
    long now=System.currentTimeMillis();
    return Jwts.builder().setClaims(Map.of("userId",userId,"tenantId",tenantId,"branchId",branchId,"role",role))
      .setIssuedAt(new Date(now)).setExpiration(new Date(now+8*3600*1000L)).signWith(key).compact();
  }
  public Jws<Claims> parse(String token){ return Jwts.parser().setSigningKey(key).build().parseClaimsJws(token); }
  public boolean validate(String t){ try{parse(t);return true;}catch(Exception e){return false;}}
}
