package com.snap.survey.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snap.survey.model.response.BaseResponse;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BaseResponseUtil {

  private final MessageSource messageSource;
  private final Locale defaultLocale = Locale.getDefault();
  private final ObjectMapper objectMapper;
  private final AppExceptionUtil appExceptionUtil;

  @Autowired
  public BaseResponseUtil(
      MessageSource messageSource, ObjectMapper objectMapper, AppExceptionUtil appExceptionUtil) {
    this.messageSource = messageSource;
    this.objectMapper = objectMapper;
    this.appExceptionUtil = appExceptionUtil;
  }

  public BaseResponse<Void> getResponse(String messageKey, String errorKey) {
    String message = messageSource.getMessage(messageKey, null, defaultLocale);
    Integer errorCode = Integer.valueOf(messageSource.getMessage(errorKey, null, defaultLocale));
    return new BaseResponse<>(errorCode, message, null);
  }

  public BaseResponse<Void> getResponse(String messageKey, Object[] args, String errorKey) {
    String message = messageSource.getMessage(messageKey, args, defaultLocale);
    Integer errorCode = Integer.valueOf(messageSource.getMessage(errorKey, null, defaultLocale));
    return new BaseResponse<>(errorCode, message, null);
  }

  public <T> BaseResponse<T> getSuccessResponse(T data) {
    return getResponse("success.message", "success.code", data);
  }

  public <T> BaseResponse<T> getResponse(String messageKey, String errorKey, T data) {
    String message = messageSource.getMessage(messageKey, null, defaultLocale);
    Integer errorCode = Integer.valueOf(messageSource.getMessage(errorKey, null, defaultLocale));
    return new BaseResponse<>(errorCode, message, data);
  }

  public String getResponseAsJson(String messageKey, String errorKey) {
    String message = messageSource.getMessage(messageKey, null, defaultLocale);
    Integer errorCode = Integer.valueOf(messageSource.getMessage(errorKey, null, defaultLocale));
    BaseResponse<Void> generalResponse = new BaseResponse<>(errorCode, message, null);
    try {
      return objectMapper.writeValueAsString(generalResponse);
    } catch (JsonProcessingException e) {
      log.error("json processing exception error message : {}", e.getMessage());
      throw appExceptionUtil.getAppException(
          "json.process.failed.error.message", "json.process.failed.error.code");
    }
  }
}
