package com.snap.survey.model.response;

import com.snap.survey.model.ErrorType;

public sealed interface ResponseStatus {
  record Success() implements ResponseStatus {}

  record Failure(ErrorType errorType) implements ResponseStatus {}
}
