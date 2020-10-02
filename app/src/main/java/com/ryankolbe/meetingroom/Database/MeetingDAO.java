package com.ryankolbe.meetingroom.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ryankolbe.meetingroom.Model.Booking;
import com.ryankolbe.meetingroom.Model.Room;
import com.ryankolbe.meetingroom.Model.User;

import java.util.List;

@Dao
public interface MeetingDAO {

    // User SQL Queries and methods
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    @Query("SELECT * FROM USERS WHERE user_name = :name AND user_password = :password")
    User loginUser(String name, String password);

    @Query("SELECT * FROM USERS WHERE user_id = :id")
    User searchUser(Long id);

    //Room SQL Queries and methods
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRoom(Room room);

    @Update
    void updateRoom(Room room);

    @Delete
    void deleteRoom(Room room);

    @Query("SELECT * FROM rooms")
    List<Room> getAllRooms();

    @Query("SELECT * FROM rooms WHERE room_id = :roomId")
    Room searchRoom(Long roomId);

    // Bookings SQL Queries and methods
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBooking(Booking booking);

    @Update
    void updateBooking(Booking booking);

    @Delete
    void deleteBooking(Booking Booking);

    @Query("SELECT * FROM booking")
    List<Booking> getAllBookings();

    @Query("SELECT * FROM Booking WHERE user_id = :userID AND room_id = :roomID")
    User searchBooking(Long userID, Long roomID);
}
