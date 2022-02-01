package com.robit.survey.model.response;

public record BaseResponse<T>(ResponseStatus status, T data) {}
