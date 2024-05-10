package com.specification.demo.util;

import com.specification.demo.exception.CustomServiceException;
import com.specification.demo.payload.response.ErrorType;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DateUtils {

  private static final Map<String, String> DATE_FORMAT_REGEXPS =
      new HashMap<String, String>() {
        {
          put("^\\d{8}$", "yyyyMMdd");
          put("^\\d{1,2}-\\d{1,2}-\\d{4}$", "dd-MM-yyyy");
          put("^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd");
          put("^\\d{1,2}/\\d{1,2}/\\d{4}$", "MM/dd/yyyy");
          put("^\\d{4}/\\d{1,2}/\\d{1,2}$", "yyyy/MM/dd");
          put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}$", "dd MMM yyyy");
          put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}$", "dd MMMM yyyy");
        }
      };

  private static final Map<String, String> DATE_WITH_TIME_FORMAT_REGEXPS =
      new HashMap<>() {
        {
          put("^\\d{12}$", "yyyyMMddHHmm");
          put("^\\d{8}\\s\\d{4}$", "yyyyMMdd HHmm");
          put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}$", "dd-MM-yyyy HH:mm");
          put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy-MM-dd HH:mm");
          put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}$", "MM/dd/yyyy HH:mm");
          put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy/MM/dd HH:mm");
          put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMM yyyy HH:mm");
          put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMMM yyyy HH:mm");
          put("^\\d{14}$", "yyyyMMddHHmmss");
          put("^\\d{8}\\s\\d{6}$", "yyyyMMdd HHmmss");
          put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd-MM-yyyy HH:mm:ss");
          put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy-MM-dd HH:mm:ss");
          put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "MM/dd/yyyy HH:mm:ss");
          put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy/MM/dd HH:mm:ss");
          put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMM yyyy HH:mm:ss");
          put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMMM yyyy HH:mm:ss");
          put(
              "^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}.\\d{6}$",
              "yyyy-MM-dd HH:mm:ss.SSSSSS");
        }
      };

  private static final Map<String, String> DATE_ALL_FORMAT_REGEXPS =
      new HashMap<>() {
        {
          putAll(DATE_FORMAT_REGEXPS);
          putAll(DATE_WITH_TIME_FORMAT_REGEXPS);
        }
      };

  public static Object parseStringToObjectByFormat(String dateStr) {
    try {
      String pattern = null;
      for (String regexp : DATE_WITH_TIME_FORMAT_REGEXPS.keySet()) {
        if (dateStr.toLowerCase().matches(regexp)) {
          pattern = DATE_ALL_FORMAT_REGEXPS.get(regexp);
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
          return LocalDateTime.parse(dateStr, formatter);
        }
      }
      for (String regexp : DATE_FORMAT_REGEXPS.keySet()) {
        if (dateStr.toLowerCase().matches(regexp)) {
          pattern = DATE_FORMAT_REGEXPS.get(regexp);
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
          return LocalDate.parse(dateStr, formatter);
        }
      }
      throw new CustomServiceException(ErrorType.DATE_FORMAT_NOT_FOUND_ERROR);
    } catch (CustomServiceException ex) {
      throw ex;
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new CustomServiceException(ErrorType.LOCAL_DATE_PARSE_ERROR, ex.getMessage());
    }
  }

  public static String parseLocalDateToString(LocalDate dateStr, String pattern) {
    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
      return dateStr.format(formatter);
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new CustomServiceException(ErrorType.LOCAL_DATE_PARSE_ERROR, ex.getMessage());
    }
  }

  public static synchronized Date addDays(Date date, int day) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DAY_OF_MONTH, day);
    return calendar.getTime();
  }

  public static synchronized Date addHours(Date date, int hour) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.HOUR, hour);
    return calendar.getTime();
  }

  /**
   * Generates Date list with zero time from given Date to given day before
   *
   * <pre>
   * Example;
   *   from : 2022-08-25T12:34:22.204+0300
   *   toDayBefore: 3
   *
   *   result:
   *      [
   *          "2022-08-23T00:00:00.000+0300",
   *          "2022-08-24T00:00:00.000+0300",
   *          "2022-08-25T00:00:00.000+0300"
   *      ]
   * </pre>
   *
   * @param from The Date from will list start
   * @param toDayBefore The number of days before from given date start
   * @return List of Dates With Zero Time
   */
  public static List<Date> generateDateList(Date from, int toDayBefore) {
    List<Date> dates = new ArrayList<>();

    for (int i = toDayBefore - 1; i >= 0; i--) {
      dates.add(generateDateBeforeFromDate(removeTimeFromDate(from), i));
    }

    return dates;
  }

  /**
   * Adds day to given date
   *
   * @param date The Date to add more days
   * @param days The number of days will add to given Date
   * @return New Date with added days
   */
  public static Date generateDateBeforeFromDate(Date date, int days) {
    return new Date(date.getTime() - days * (24 * 60 * 60 * 1000));
  }

  /**
   * Removes time from given Date
   *
   * @param date The Date will remove time
   * @return Date with time zero like XXXX-XX-XXT00:00:00.000+00:00
   */
  public static Date removeTimeFromDate(Date date) {
    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    try {
      return formatter.parse(formatter.format(date));
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
  }
}
