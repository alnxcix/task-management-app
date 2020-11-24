package com.nidoy.taskmanagementapp;

import androidx.room.TypeConverter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class Converters {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static LocalTime fromString(String value) {
        return value == null ? null : LocalTime.parse(value);
    }

    @TypeConverter
    public static String localTimeToString(LocalTime localTime) {
        return localTime == null ? null : localTime.toString();
    }

    @TypeConverter
    public static LocalDateTime stringToLocalDateTime(String value) {
        return value == null ? null : LocalDateTime.parse(value);
    }

    @TypeConverter
    public static String localDateTimeToString(LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.toString();
    }
}
