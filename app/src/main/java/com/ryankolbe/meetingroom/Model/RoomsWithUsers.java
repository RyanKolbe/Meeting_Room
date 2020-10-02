package com.ryankolbe.meetingroom.Model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class RoomsWithUsers {
    @Embedded
    private Room room;
    @Relation(
            parentColumn = "room_id",
            entityColumn = "user_id",
            associateBy = @Junction(Booking.class)
    )
    private List<User> users;

    public RoomsWithUsers() {
    }

    public RoomsWithUsers(Room room, List<User> users) {
        this.room = room;
        this.users = users;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "RoomsWithUsers{" +
                "room=" + room +
                ", users=" + users +
                '}';
    }
}