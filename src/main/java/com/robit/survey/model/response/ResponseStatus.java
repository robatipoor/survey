package com.robit.survey.model.response;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.robit.survey.model.ErrorType;

@JsonTypeInfo(include = As.WRAPPER_OBJECT, use = Id.NAME)
public sealed interface ResponseStatus {
  @JsonTypeName("success")
  record Success(String message) implements ResponseStatus {}

  @JsonTypeName("failure")
  record Failure(ErrorType errorType) implements ResponseStatus {}
}
