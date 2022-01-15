package com.snap.survey.config;

import java.util.concurrent.TimeUnit;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class MessageSourceConfig {

  private static final long MESSAGE_CACHE_TTL = TimeUnit.DAYS.toMillis(1);

  @Bean
  public MessageSource messageSourceBean() {
    var resource = new ReloadableResourceBundleMessageSource();
    resource.setBasename("classpath:/messages/messages");
    resource.setDefaultEncoding("UTF-8");
    resource.setCacheMillis(MESSAGE_CACHE_TTL);
    return resource;
  }
}
