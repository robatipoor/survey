package com.snap.survey.config;

import com.snap.survey.util.BaseResponseUtil;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

  private final BaseResponseUtil baseResponseUtil;
  private String responseBody;

  public AuthenticationEntryPointImpl(BaseResponseUtil baseResponseUtil) {
    this.baseResponseUtil = baseResponseUtil;
  }

  @PostConstruct
  public void init() {
    this.responseBody =
        baseResponseUtil.getResponseAsJson("unauthorized.error.message", "unauthorized.error.code");
  }

  @Override
  public void commence(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      AuthenticationException e)
      throws IOException {
    log.error("unauthorized error message : {}", e.getMessage());
    httpServletResponse.setContentType("application/json");
    httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    httpServletResponse.getOutputStream().println(responseBody);
  }
}
