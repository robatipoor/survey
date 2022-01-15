package com.snap.survey.config;

public class Constants {

  public static final String BEARER_TOKEN_PREFIX = "Bearer ";
  public static final String AUTH_HEADER_NAME = "Authorization";
  public static final String BEARER = "Bearer";

  // TODO just for local test
  public static final String[] LOCAL_ACCESS_API_LIST = {};

  public static final String[] PUBLIC_ACCESS_API_LIST = {
    "/api/v1/ping", "/api-docs", "/api-docs/**", "/swagger-ui/**"
  };
}
