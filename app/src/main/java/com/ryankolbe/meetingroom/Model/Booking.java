package com.ryankolbe.meetingroom.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

import java.sql.Date;
import java.sql.Time;


@Entity(primaryKeys = {"room_id", "user_id"}, tableName = "bookings")
public class Booking {

    @ColumnInfo(name = "room_id")
    private long roomID;

    @ColumnInfo(name = "user_id")
    private long userID;

    @ColumnInfo(name = "meeting_date")
    private Date meetingDate;

    @ColumnInfo(name = "start_time")
    private Time startTime;

    @ColumnInfo(name = "end_time")
    private Time endTime;

    @ColumnInfo(name = "comment")
    private String comment;

    public Booking() {
    }

    @Ignore
    public Booking(long roomID, long userID, Date meetingDate, Time startTime, Time endTime, String comment) {
        this.roomID = roomID;
        this.userID = userID;
        this.meetingDate = meetingDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.comment = comment;
    }

    public long getRoomID() {
        return roomID;
    }

    public void setRoomID(long roomID) {
        this.roomID = roomID;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public Date getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(Date meetingDate) {
        this.meetingDate = meetingDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}