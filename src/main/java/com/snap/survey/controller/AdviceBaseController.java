package com.snap.survey.controller;

import com.snap.survey.exception.AppException;
import com.snap.survey.model.response.BaseResponse;
import com.snap.survey.util.BaseResponseUtil;
import java.nio.file.AccessDeniedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
@Slf4j
public class AdviceBaseController {

  private final BaseResponseUtil responseUtil;

  public AdviceBaseController(BaseResponseUtil responseUtil) {
    this.responseUtil = responseUtil;
  }

  @ResponseBody
  @ExceptionHandler(AppException.class)
  @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
  BaseResponse<Void> baseExceptionHandler(AppException ex) {
    log.error("app exception error message : {}", ex.getMessage());
    return new BaseResponse<>(ex.getErrorCode(), ex.getMessage(), null);
  }

  @ResponseBody
  @ExceptionHandler(AccessDeniedException.class)
  @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
  BaseResponse<Void> accessDeniedExceptionHandler(AccessDeniedException ex) {
    log.error("access denied exception error message : {}", ex.getMessage());
    return responseUtil.getResponse("access.denied.error.message", "access.denied.error.code");
  }

  @ResponseBody
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
  BaseResponse<Void> exceptionHandler(Exception ex) {
    log.error(
        "unexpected exception : {} error message : {}",
        ex.getClass().getCanonicalName(),
        ex.getMessage());
    return responseUtil.getResponse("unexpected.error.message", "unexpected.error.code");
  }

  @ResponseBody
  @ExceptionHandler({
    BindException.class,
    MethodArgumentTypeMismatchException.class,
    HttpMessageNotReadableException.class
  })
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  BaseResponse<Void> invalidInputExceptionHandler(Exception ex) {
    log.error("invalid input exception error message {}", ex.getMessage());
    return responseUtil.getResponse("invalid.input.error.message", "invalid.input.error.code");
  }
}
