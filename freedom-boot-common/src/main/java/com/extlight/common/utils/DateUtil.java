package com.extlight.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Author MoonlightL
 * @Title: DateUtil
 * @ProjectName freedom-boot
 * @Description: 日期工具类
 * @Date 2019/7/8 20:04
 */
public class DateUtil {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(DATETIME_FORMAT);

    /**
     *  LocalDate 转 Date
     * @param localDate
     * @return
     */
    public static Date ld2Date(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * LocalDateTime 转 Date
     * @param localDateTime
     * @return
     */
    public static Date ldt2Date(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Date 转 LocalDate
     * @param date
     * @return
     */
    public static LocalDate date2LocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Date 转 LocalDateTime
     * @param date
     * @return
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * localDate 转时间戳
     * @param localDate
     * @return
     */
    public static long toMilli(LocalDate localDate) {
        return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * LocalDateTime 转时间戳
     * @param localDateTime
     * @return
     */
    public static long toMilli(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }

    /**
     *  LocalDate 转字符串
     * @param localDate
     * @return
     */
    public static String toStr(LocalDate localDate) {
        return DATE_FORMATTER.format(localDate);
    }

    /**
     * LocalDateTime 转字符串
     * @param localDateTime
     * @return
     */
    public static String toStr(LocalDateTime localDateTime) {
        return DATETIME_FORMATTER.format(localDateTime);
    }
}
