package com.snap.survey.exception;

import com.snap.survey.model.ErrorType;
import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
  private final ErrorType errorType;

  public AppException(ErrorType errorType) {
    this.errorType = errorType;
  }
}
