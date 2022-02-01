package com.robit.survey.controller;

import com.robit.survey.exception.AppException;
import com.robit.survey.model.response.BaseResponse;
import com.robit.survey.util.BaseResponseUtil;
import java.nio.file.AccessDeniedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
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
    return responseUtil.getFailureResponse(ex.getErrorType());
  }

  @ResponseBody
  @ExceptionHandler(AccessDeniedException.class)
  @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
  BaseResponse<Void> accessDeniedExceptionHandler(AccessDeniedException ex) {
    log.error("access denied exception error message : {}", ex.getMessage());
    return responseUtil.getBusinessErrorFailureResponse("access.denied.error");
  }

  @ResponseBody
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
  BaseResponse<Void> exceptionHandler(Exception ex) {
    log.error(
        "unexpected exception : {} error message : {}",
        ex.getClass().getCanonicalName(),
        ex.getMessage());
    return responseUtil.getSystemErrorFailureResponse("unexpected.error", ex.getMessage());
  }

  @ResponseBody
  @ExceptionHandler({
    BindException.class,
    MethodArgumentTypeMismatchException.class,
    HttpMessageNotReadableException.class,
    BadCredentialsException.class
  })
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  BaseResponse<Void> invalidInputExceptionHandler(Exception ex) {
    log.error("invalid input exception error message : {}", ex.getMessage());
    return responseUtil.getBusinessErrorFailureResponse("invalid.input.error");
  }
}
