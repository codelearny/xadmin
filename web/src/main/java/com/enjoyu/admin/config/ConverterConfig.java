package com.enjoyu.admin.config;

import com.enjoyu.admin.common.exception.ServiceException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static com.enjoyu.admin.common.constant.DateFormatConstant.*;

/**
 * 自定义时间格式转换
 *
 * @author enjoyu
 */
@Configuration
public class ConverterConfig {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN);
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATETIME_PATTERN);
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_TIME_PATTERN);

    /**
     * RequestParam和PathVariable
     *
     * @return 旧版日期转换器
     */
    @Bean
    public Converter<String, Date> dateConverter() {
        return source -> {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATETIME_PATTERN);
                return formatter.parse(source);
            } catch (ParseException e) {
                throw new ServiceException(e, "Error parsing %s to Date", source);
            }
        };

    }

    /**
     * RequestParam和PathVariable
     *
     * @return 自定义日期转换器
     */
    @Bean
    public Converter<String, LocalDate> localDateConverter() {
        return source -> LocalDate.parse(source, DATE_FORMATTER);
    }

    /**
     * RequestParam和PathVariable
     *
     * @return 自定义日期时间转换器
     */
    @Bean
    public Converter<String, LocalDateTime> localDateTimeConverter() {
        return source -> LocalDateTime.parse(source, DATE_TIME_FORMATTER);
    }

    /**
     * RequestParam和PathVariable
     *
     * @return 自定义时间转换器
     */
    @Bean
    public Converter<String, LocalTime> localTimeConverter() {
        return source -> LocalTime.parse(source, TIME_FORMATTER);
    }

}
