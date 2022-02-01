package com.robit.survey.exception;

import com.robit.survey.model.ErrorType;
import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
  private final ErrorType errorType;

  public AppException(ErrorType errorType) {
    this.errorType = errorType;
  }
}
