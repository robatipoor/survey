package com.snap.survey.config;

import com.snap.survey.model.UserPrincipal;
import com.snap.survey.util.TokenUtil;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {

  private final TokenUtil tokenUtil;

  @Autowired
  public AuthorizationFilter(TokenUtil tokenUtil) {
    this.tokenUtil = tokenUtil;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      tokenUtil
          .getJwtTokenFromHttpServletRequest(request)
          .map(
              jwtToken -> {
                UserPrincipal user = tokenUtil.getUserPrincipalFromToken(jwtToken);
                return new UsernamePasswordAuthenticationToken(
                    user, user.getPassword(), user.getAuthorities());
              })
          .ifPresent(
              authToken -> {
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
              });
    } catch (Exception e) {
      log.error(
          "could not set user authentication in security context error message : {}",
          e.getMessage());
    }
    filterChain.doFilter(request, response);
  }
}
