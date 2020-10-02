package com.ryankolbe.meetingroom.Model;

import androidx.room.TypeConverter;

import java.sql.Date;
import java.sql.Time;

public class DateTimeConverter {

    @TypeConverter
    public static Date toDate(Long dateLong) {
        return dateLong == null ? null : new Date(dateLong);
    }

    @TypeConverter
    public static Long fromDate(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Time toTime(Long timeLong) {
        return timeLong == null ? null : new Time(timeLong);
    }

    @TypeConverter
    public static long fromTime(Time time) {
        return time == null ? null : time.getTime();
    }
}