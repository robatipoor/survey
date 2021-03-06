package com.robit.survey.util;

import com.robit.survey.exception.AppException;
import com.robit.survey.model.ErrorType;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AppExceptionUtil {

  private final MessageUtil messageUtil;

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

  public AppException getException(
      String key, Function<Pair<Integer, String>, ErrorType> createErrorFunc) {
    var value = messageUtil.get(key);
    return new AppException(createErrorFunc.apply(value));
  }
}
