package com.genesis.org.cn.genesismeituanopenapijavasdk.utils;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.date.format.FastDateFormat;
import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    /**
     * 特殊时间转换
     * @param dateStr 时间字符串
     * @return 结果
     */
    public static LocalDateTime parseSpecialLocalDateTime(String dateStr) {

        if(StringUtils.isBlank(dateStr)){
            return null;
        }

        try {
            Date date = parseSpecialDate(dateStr);
            return LocalDateTimeUtil.of(date);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 特殊时间转换
     * @param dateStr 时间字符串
     * @return 结果
     */
    public static String formatSpecialDate(String dateStr) {

        if(StringUtils.isBlank(dateStr)){
            return dateStr;
        }

        if(!dateStr.contains("Apr")){
            return dateStr;
        }
        return DateUtil.formatDateTime(parseSpecialDate(dateStr));
    }

    /**
     * 特殊时间转换
     * @param dateStr 时间字符串
     * @return 结果
     */
    public static Date parseSpecialDate(String dateStr) {

        if(StringUtils.isBlank(dateStr)){
            return null;
        }

        try {

            if(!dateStr.contains("AM") && !dateStr.contains("PM")){
                return DateUtils.parseDate(dateStr);
            }

            // 定义具有合适模式的 SimpleDateFormat
            FastDateFormat formatter = FastDateFormat.getInstance("MMM d, yyyy h:mm:ss a",TimeZone.getTimeZone("GMT+8"), Locale.US);

            return formatter.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }



    public final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取当天开始时间
     */
    public static LocalDateTime getNowStartLocalTime() {
        return withLocalTime(LocalDateTime.now(), 0, 0, 0);
    }

    /**
     * 获取当天结束时间
     */
    public static LocalDateTime getNowEndLocalTime() {
        return withLocalTime(LocalDateTime.now(), 23, 59, 59);
    }

    /**
     * 设置时间为当天初
     */
    public static LocalDateTime withStartLocalTime(LocalDateTime dateTime) {
        return withLocalTime(dateTime, 0, 0, 0);
    }

    /**
     * 设置时间为当天末
     */
    public static LocalDateTime withEndLocalTime(LocalDateTime dateTime) {
        return withLocalTime(dateTime, 23, 59, 59);
    }

    /**
     * 变更时间时分秒
     */
    public static LocalDateTime withLocalTime(LocalDateTime dateTime, int hour, int minute, int second) {
        if(dateTime == null){
            return null;
        }
        return dateTime.withHour(hour).withMinute(minute).withSecond(second).withNano(0);
    }

    /**
     * 设置毫秒为0
     */
    public static LocalDateTime withNanoZero(LocalDateTime dateTime) {
        if(dateTime == null){
            return null;
        }
        if(dateTime.getNano() == 0){
            return dateTime;
        }
        return dateTime.withNano(0);
    }

    /**
     * 获取当前月第一天
     *
     * @param date 当前时间
     * @return 结果
     */
    public static LocalDateTime getMonthStartTime(LocalDateTime date) {
        return LocalDateTime.of(LocalDate.from(date.with(TemporalAdjusters.firstDayOfMonth())), LocalTime.MIN);
    }

    /**
     * 获取当前月最后一天
     *
     * @param date 当前时间
     * @return 结果
     */
    public static LocalDateTime getMonthEndTime(LocalDateTime date) {
        // 获取当前月第一天及最后一天
        return LocalDateTime.of(LocalDate.from(date.with(TemporalAdjusters.lastDayOfMonth())), LocalTime.MAX);
    }

    /**
     * 获取两个时间之间所有的日期
     *
     * @param start 起始日期
     * @param end   结束日期
     * @return 包含在起始和结束日期之间的所有日期的数组
     */
    public static List<LocalDate> getBetweenDates(LocalDate start, LocalDate end) {
        List<LocalDate> dates = new ArrayList<>();
        if (start.isAfter(end)) {
            return dates;
        }
        LocalDate temp = start;
        while (temp.equals(end) || temp.isBefore(end)) {
            dates.add(temp);
            temp = temp.plusDays(1);
        }
        return dates;
    }

    /**
     * 获取月初时间
     *
     * @param date 当前时间
     * @return 结果
     */
    public static LocalDate getMonthStart(LocalDate date) {
        return date.withDayOfMonth(1);
    }

    /**
     * 获取LocalDate月末时间
     *
     * @param date 当前时间
     * @return 结果
     */
    public static LocalDate getMonthEnd(LocalDate date) {
        return date.withDayOfMonth(date.lengthOfMonth());
    }

    /**
     * date转localDateTime
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        if (ObjectUtils.isEmpty(date)) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * 获取当前时间开始时间
     * @param value 当前时间
     * @return 结果
     */
    public static LocalDateTime getDayStartLocalDateTime(Object value){
        return withStartLocalTime(getLocalDateTime(value));
    }

    /**
     * 获取当前时间结束时间
     * @param value 当前时间
     * @return 结果
     */
    public static LocalDateTime getDayEndLocalDateTime(Object value){
        return withEndLocalTime(getLocalDateTime(value));
    }


    public static LocalDateTime getLocalDateTime(Object value) {
        if(ObjectUtils.isEmpty(value)){
            return null;
        }
        LocalDateTime makeTime;
        if(value instanceof LocalDateTime){
            makeTime = (LocalDateTime) value;
        }else if(value instanceof LocalDate){
            makeTime = LocalDateTimeUtil.of((LocalDate) value);
        }else if(value instanceof Date){
            makeTime = LocalDateTimeUtil.of((Date) value);
        }else{
            String dateStr = (String) value;
            if (StrUtil.contains(dateStr, 'T')) {
                return LocalDateTimeUtil.parse(dateStr);
            }else if(DatePattern.NORM_DATETIME_PATTERN.length() == dateStr.length()){
                makeTime = LocalDateTimeUtil.parse(dateStr, DatePattern.NORM_DATETIME_PATTERN);
            }else if(DatePattern.NORM_DATE_PATTERN.length() == dateStr.length()){
                makeTime = LocalDateTimeUtil.parse(dateStr, DatePattern.NORM_DATE_PATTERN);
            }else{
                Date date = DateUtil.parse(dateStr);
                makeTime = LocalDateTimeUtil.of(date);
            }
        }
        return makeTime;
    }
}
