package ua.hypson.jsplab.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

/**
 * converter
 * Created by admin on 28.05.2016.
 */
public class BirthdayToAgeConverter {
    public int convert(Date birthday) {
        System.out.println(birthday.toLocalDate());
        LocalDate localDate = birthday.toLocalDate();
        LocalDate now = LocalDate.now();
        Period period = Period.between(localDate, now);
        return period.getYears();
    }

}
