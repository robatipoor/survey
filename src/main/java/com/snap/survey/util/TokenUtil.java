package com.snap.survey.util;

import com.snap.survey.mapper.UserPrincipalMapper;
import com.snap.survey.model.UserPrincipal;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenUtil {

  private final int jwtExpireDurationHours;
  private final UserPrincipalMapper userPrincipalMapper;
  private final DateUtil dateUtil;

  private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

  public TokenUtil(
      @Value("${survey.app.jwt.expire.duration.hours}") int jwtExpireDurationHours,
      UserPrincipalMapper userPrincipalMapper,
      DateUtil dateUtil) {
    this.jwtExpireDurationHours = jwtExpireDurationHours;
    this.userPrincipalMapper = userPrincipalMapper;
    this.dateUtil = dateUtil;
  }

  public String generateToken(UserPrincipal userPrincipal) {
    return Jwts.builder()
        .setClaims(userPrincipalMapper.toMap(userPrincipal))
        .setIssuedAt(new Date())
        .setExpiration(dateUtil.getExpireDateFromDurationHours(jwtExpireDurationHours))
        .signWith(key)
        .compact();
  }

  public UserPrincipal getUserPrincipalFromToken(String token) {
    Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    return userPrincipalMapper.toUserPrincipal(claims);
  }
}
