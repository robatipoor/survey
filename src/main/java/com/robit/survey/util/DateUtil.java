package com.robit.survey.util;

import java.util.Calendar;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class DateUtil {

  public Date getExpireDateFromDurationHours(int durationHours) {
    var calendar = Calendar.getInstance();
    calendar.add(Calendar.HOUR, durationHours);
    return calendar.getTime();
  }
}
