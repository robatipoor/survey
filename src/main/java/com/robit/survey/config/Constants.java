package com.robit.survey.config;

public class Constants {

  public static final String BEARER_TOKEN_PREFIX = "Bearer ";
  public static final String AUTH_HEADER_NAME = "Authorization";
  public static final String BEARER = "Bearer";

  // TODO just for local test
  public static final String[] LOCAL_ACCESS_API_LIST = {};

  public static final String[] PUBLIC_ACCESS_API_LIST = {
    "/api/v1/app/ping",
    "/api-docs",
    "/api-docs/**",
    "/swagger-ui/**",
    "/api/v1/user/login",
    "/api/v1/user/register"
  };

  public static final String ADMIN = "hasAuthority('ROLE_ADMIN')";
  public static final String USER = "hasAuthority('ROLE_USER')";
}
