package com.genesis.org.cn.genesismeituanopenapijavasdk.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.date.format.FastDateFormat;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
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

            if(!dateStr.contains("Apr")){
                return DateUtils.parseDate(dateStr);
            }

            // 定义具有合适模式的 SimpleDateFormat
            FastDateFormat formatter = FastDateFormat.getInstance("MMM d, yyyy h:mm:ss a", TimeZone.getTimeZone("GMT"), Locale.US);

            return formatter.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
