package com.snap.survey.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snap.survey.model.ErrorType;
import com.snap.survey.model.response.BaseResponse;
import com.snap.survey.model.response.ResponseStatus;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BaseResponseUtil {

  private final MessageUtil messageUtil;
  private final ObjectMapper objectMapper;
  private final AppExceptionUtil appExceptionUtil;

  public BaseResponseUtil(
      MessageUtil messageUtil, ObjectMapper objectMapper, AppExceptionUtil appExceptionUtil) {
    this.messageUtil = messageUtil;
    this.objectMapper = objectMapper;
    this.appExceptionUtil = appExceptionUtil;
  }

  public BaseResponse<Void> getFailureResponse(ErrorType errorType) {
    return new BaseResponse<>(new ResponseStatus.Failure(errorType), null);
  }

  public BaseResponse<Void> getFailureResponse(
      String key, Function<Pair<Integer, String>, ErrorType> createErrorFunc) {
    var value = messageUtil.get(key);
    return new BaseResponse<>(new ResponseStatus.Failure(createErrorFunc.apply(value)), null);
  }

  public BaseResponse<Void> getBusinessErrorFailureResponse(String key) {
    var value = messageUtil.get(key);
    return new BaseResponse<>(
        new ResponseStatus.Failure(
            new ErrorType.BusinessError(value.getFirst(), value.getSecond())),
        null);
  }

  public BaseResponse<Void> getSystemErrorFailureResponse(String key, String debugInfo) {
    var value = messageUtil.get(key);
    return new BaseResponse<>(
        new ResponseStatus.Failure(
            new ErrorType.SystemError(value.getFirst(), value.getSecond(), debugInfo)),
        null);
  }

  public <T> BaseResponse<T> getSuccessResponse(T data) {
    return new BaseResponse<>(new ResponseStatus.Success("ok"), data);
  }

  public <T> BaseResponse<T> getSuccessResponse() {
    return new BaseResponse<>(new ResponseStatus.Success("ok"), null);
  }

  public String getBusinessErrorFailureResponseAsJson(String key) {
    var value = getBusinessErrorFailureResponse(key);
    try {
      return objectMapper.writeValueAsString(value);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      log.error("json processing exception error message : {}", e.getMessage());
      throw appExceptionUtil.getSystemException("json.process.failed.error", e.getMessage());
    }
  }
}
