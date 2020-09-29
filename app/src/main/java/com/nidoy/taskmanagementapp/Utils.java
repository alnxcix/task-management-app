package com.nidoy.taskmanagementapp;

import android.annotation.SuppressLint;

import androidx.room.TypeConverter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM. dd, yyyy");
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
