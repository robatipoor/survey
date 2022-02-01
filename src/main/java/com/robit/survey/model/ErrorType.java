package com.robit.survey.model;

public sealed interface ErrorType {
  record BusinessError(Integer code, String message) implements ErrorType {}

  record SystemError(Integer code, String message, String debugInfo) implements ErrorType {}
}
