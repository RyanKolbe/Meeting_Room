package com.ryankolbe.meetingroom.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "rooms")
public class Room implements Serializable {

    @ColumnInfo(name = "room_id")
    @PrimaryKey(autoGenerate = true)
    private long roomID;

    @ColumnInfo(name = "room_name")
    private String roomName;

    @ColumnInfo(name = "room_location")
    private String roomLocation;

    @ColumnInfo(name = "room_available")
    private String roomAvailable;

    @ColumnInfo(name = "room_equipment_available")
    private String roomEquipmentAvailable;

    public Room() {
    }

    @Ignore
    public Room(long roomID, String roomName, String roomLocation, String roomAvailable, String roomEquipmentAvailable) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.roomLocation = roomLocation;
        this.roomAvailable = roomAvailable;
        this.roomEquipmentAvailable = roomEquipmentAvailable;
    }

    public long getRoomID() {
        return roomID;
    }

    public void setRoomID(long roomID) {
        this.roomID = roomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomLocation() {
        return roomLocation;
    }

    public void setRoomLocation(String roomLocation) {
        this.roomLocation = roomLocation;
    }

    public String getRoomAvailable() {
        return roomAvailable;
    }

    public void setRoomAvailable(String roomAvailable) {
        this.roomAvailable = roomAvailable;
    }

    public String getRoomEquipmentAvailable() {
        return roomEquipmentAvailable;
    }

    public void setRoomEquipmentAvailable(String roomEquipmentAvailable) {
        this.roomEquipmentAvailable = roomEquipmentAvailable;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomID=" + roomID +
                ", roomName='" + roomName + '\'' +
                ", roomLocation='" + roomLocation + '\'' +
                ", roomAvailable='" + roomAvailable + '\'' +
                ", roomEquipmentAvailable='" + roomEquipmentAvailable + '\'' +
                '}';
    }
}
