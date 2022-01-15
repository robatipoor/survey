package com.snap.survey.util;

import com.snap.survey.exception.AppException;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AppExceptionUtil {

  private final Locale defaultLocale = Locale.getDefault();
  private final MessageSource messageSource;

  @Autowired
  public AppExceptionUtil(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  public AppException getAppException(String messageKey, String errorKey) {
    var message = messageSource.getMessage(messageKey, null, defaultLocale);
    var errorCode = Integer.parseInt(messageSource.getMessage(errorKey, null, defaultLocale));
    return new AppException(message, errorCode);
  }
}
