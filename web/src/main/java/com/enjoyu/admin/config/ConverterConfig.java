package com.enjoyu.admin.config;

import com.enjoyu.admin.common.exception.ServiceException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static com.enjoyu.admin.common.constant.DateFormatConstant.DEFAULT_DATETIME_PATTERN;
import static com.enjoyu.admin.common.constant.DateFormatConstant.DEFAULT_DATE_PATTERN;

@Configuration
public class ConverterConfig {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN);
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATETIME_PATTERN);

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

    @Bean
    public Converter<String, LocalDate> localDateConverter() {
        return source -> LocalDate.parse(source, DATE_FORMATTER);
    }

    @Bean
    public Converter<String, LocalDateTime> localDateTimeConverter() {
        return source -> LocalDateTime.parse(source, DATE_TIME_FORMATTER);
    }
}
