package com.snap.survey.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
  private final int errorCode;

  public AppException(String message, int errorCode) {
    super(message);
    this.errorCode = errorCode;
  }
}
