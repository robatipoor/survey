package com.snap.survey.util;

import com.snap.survey.config.Constants;
import com.snap.survey.mapper.UserPrincipalMapper;
import com.snap.survey.model.UserPrincipal;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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

  public String generateToken(Map<String, Object> claims) {
    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(new Date())
        .setExpiration(dateUtil.getExpireDateFromDurationHours(jwtExpireDurationHours))
        .signWith(key)
        .compact();
  }

  public String generateToken(UserPrincipal userPrincipal) {
    return generateToken(userPrincipalMapper.toMap(userPrincipal));
  }

  public UserPrincipal getUserPrincipalFromToken(String token) {
    var claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    return userPrincipalMapper.toUserPrincipal(claims);
  }

  public Optional<String> getJwtTokenFromHttpServletRequest(HttpServletRequest request) {
    var bearerToken = request.getHeader(Constants.AUTH_HEADER_NAME);
    if (StringUtils.hasText(bearerToken)
        && bearerToken.startsWith(Constants.BEARER_TOKEN_PREFIX)
        && bearerToken.length() > Constants.BEARER_TOKEN_PREFIX.length()) {
      return Optional.of(bearerToken.substring(Constants.BEARER_TOKEN_PREFIX.length()));
    }
    return Optional.empty();
  }
}
