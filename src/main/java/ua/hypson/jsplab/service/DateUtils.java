package ua.hypson.jsplab.service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;

/**
 * converter Created by admin on 28.05.2016.
 */
public class DateUtils {
  public int convert(Date birthday) {
    LocalDate localDate = birthday.toLocalDate();
    LocalDate now = LocalDate.now();
    Period period = Period.between(localDate, now);
    return period.getYears();
  }


  /**
   *
   * @param dateString
   *          String in format yyyy-MM-dd
   * @return java.sql.Date object parsed out of given string
   */
  public Date parseDate(String dateString) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    java.util.Date parsed;
    try {
      parsed = format.parse(dateString);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
    return new java.sql.Date(parsed.getTime());
  }

}
