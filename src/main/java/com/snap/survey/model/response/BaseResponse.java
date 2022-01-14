package com.snap.survey.model.response;

public record BaseResponse<T>(Integer code, String message, T data) {}
