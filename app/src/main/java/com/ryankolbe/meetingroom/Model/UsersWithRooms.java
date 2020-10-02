package com.ryankolbe.meetingroom.Model;

import androidx.room.Embedded;
import androidx.room.Ignore;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class UsersWithRooms {
    @Embedded
    private User user;
    @Relation(
            parentColumn = "user_id",
            entityColumn = "room_id",
            associateBy = @Junction(Booking.class)
    )
    private List<Room> rooms;

    @Ignore
    public UsersWithRooms(User user, List<Room> rooms) {
        this.user = user;
        this.rooms = rooms;
    }

    public UsersWithRooms() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return "UsersWithRooms{" +
                "user=" + user +
                ", rooms=" + rooms +
                '}';
    }
}
