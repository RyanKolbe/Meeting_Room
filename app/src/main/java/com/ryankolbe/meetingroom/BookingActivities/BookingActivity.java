package com.ryankolbe.meetingroom.BookingActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.ryankolbe.meetingroom.CustomAdapters.BookingCustomAdapter;
import com.ryankolbe.meetingroom.Database.DatabaseClient;
import com.ryankolbe.meetingroom.Model.Booking;
import com.ryankolbe.meetingroom.Model.Room;
import com.ryankolbe.meetingroom.Model.User;
import com.ryankolbe.meetingroom.R;
import com.ryankolbe.meetingroom.RoomActivities.RoomActivity;
import com.ryankolbe.meetingroom.UserActivities.UserActivity;

import java.util.ArrayList;
import java.util.List;

public class BookingActivity extends AppCompatActivity {
    List<Room> roomList = new ArrayList<>();
    List<User> userList = new ArrayList<>();
    List<Booking> bookingList = new ArrayList<>();
    Button btn_create_booking, btn_booking_to_user_menu, btn_booking_to_room_menu;
    ListView lv_booking_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        btn_create_booking = findViewById(R.id.btn_create_booking);
        btn_booking_to_user_menu = findViewById(R.id.btn_booking_to_user_menu);
        btn_booking_to_room_menu = findViewById(R.id.btn_booking_to_room_menu);
        lv_booking_data = findViewById(R.id.lv_booking_data);

        navigation();
        userRefresh();

        btn_create_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CreateBookingActivity.class));
            }
        });

    }

    public void userRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                roomList = DatabaseClient.getInstance(getApplicationContext())
                        .getDatabase().meetingDao().getAllRooms();
                userList = DatabaseClient.getInstance(getApplicationContext())
                        .getDatabase().meetingDao().getAllUsers();
                bookingList = DatabaseClient.getInstance(getApplicationContext())
                        .getDatabase().meetingDao().getAllBookings();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        BookingCustomAdapter bookingCustomAdapter =
                                new BookingCustomAdapter(getApplicationContext(),
                                        bookingList, userList, roomList);
                        lv_booking_data.setAdapter(bookingCustomAdapter);
                        lv_booking_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent = new Intent(getApplicationContext(), EditBookingActivity.class);
                                Booking bookingItem = bookingList.get(i);
                                User userItem = userList.get(i);
                                Room roomItem = roomList.get(i);
                                intent.putExtra("booking_id", bookingItem.getRoomID()
                                        + " " + bookingItem.getUserID());
                                intent.putExtra("user_name", userItem.getUsername());
                                intent.putExtra("meeting_room", roomItem.getRoomName());
                                intent.putExtra("meeting_date", bookingItem.getMeetingDate());
                                intent.putExtra("start_time", bookingItem.getStartTime());
                                intent.putExtra("end_time", bookingItem.getEndTime());
                                startActivity(intent);

                            }
                        });
                    }
                });
            }
        }).start();
    }

    public void navigation() {
        btn_booking_to_room_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RoomActivity.class));
            }
        });

        btn_booking_to_user_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserActivity.class));
            }
        });
    }
}