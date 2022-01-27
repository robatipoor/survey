package com.snap.survey.model.response;

public record BaseResponse<T>(ResponseStatus status, T data) {}
