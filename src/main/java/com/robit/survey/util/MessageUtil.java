package com.robit.survey.util;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

@Component
public class MessageUtil {

  private final MessageSource messageSource;
  private final Locale defaultLocale = Locale.getDefault();

  public MessageUtil(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  public Pair<Integer, String> get(String key) {
    var message =
        messageSource.getMessage(String.format("%s.message", key.trim()), null, defaultLocale);
    var errorCode =
        Integer.parseInt(
            messageSource.getMessage(String.format("%s.code", key.trim()), null, defaultLocale));
    return Pair.of(errorCode, message);
  }

  public Pair<Integer, String> get(String key, Object[] args) {
    var message =
        messageSource.getMessage(String.format("%s.message", key.trim()), args, defaultLocale);
    var errorCode =
        Integer.parseInt(
            messageSource.getMessage(String.format("%s.code", key.trim()), null, defaultLocale));
    return Pair.of(errorCode, message);
  }
}
