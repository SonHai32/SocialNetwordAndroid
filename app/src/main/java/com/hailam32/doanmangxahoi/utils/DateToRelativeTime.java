package com.hailam32.doanmangxahoi.utils;

import android.text.format.DateUtils;

import java.util.Date;

public class DateToRelativeTime {

  public static String getRelativeTime(Date date) {
    return DateUtils.getRelativeTimeSpanString(date.getTime(), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE).toString();
  }
}
