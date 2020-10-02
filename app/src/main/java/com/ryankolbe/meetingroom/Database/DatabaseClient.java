package com.ryankolbe.meetingroom.Database;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {
    private static DatabaseClient dbInstance;
    private ApplicationDatabase database;

    private DatabaseClient(Context context) {

        database = Room.databaseBuilder(
                context, ApplicationDatabase.class,
                "Meeting Room").build();
    }

    public static synchronized DatabaseClient getInstance(Context context) {
        if (dbInstance == null) {
            dbInstance = new DatabaseClient((context));
        }
        return dbInstance;
    }

    public ApplicationDatabase getDatabase() {
        return database;
    }
}
