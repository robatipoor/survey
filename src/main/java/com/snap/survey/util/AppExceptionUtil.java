package com.snap.survey.util;

import com.snap.survey.exception.AppException;
import com.snap.survey.model.ErrorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AppExceptionUtil {

  private final MessageUtil messageUtil;

  @Autowired
  public AppExceptionUtil(MessageUtil messageUtil) {
    this.messageUtil = messageUtil;
  }

  public AppException getSystemException(String key, String debugInfo) {
    var value = messageUtil.get(key);
    return new AppException(
        new ErrorType.SystemError(value.getFirst(), value.getSecond(), debugInfo));
  }

  public AppException getBusinessException(String key) {
    var value = messageUtil.get(key);
    return new AppException(new ErrorType.BusinessError(value.getFirst(), value.getSecond()));
  }
}
