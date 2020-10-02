package com.ryankolbe.meetingroom.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.ryankolbe.meetingroom.Model.DateTimeConverter;
import com.ryankolbe.meetingroom.Model.Room;
import com.ryankolbe.meetingroom.Model.User;
import com.ryankolbe.meetingroom.Model.Booking;

@TypeConverters(DateTimeConverter.class)
@Database(entities = {User.class, Room.class, Booking.class}, version = 1, exportSchema = false)
public abstract class ApplicationDatabase extends RoomDatabase {
    public abstract MeetingDAO meetingDao();
}
