package com.ryankolbe.meetingroom.UserActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.ryankolbe.meetingroom.R;
import com.ryankolbe.meetingroom.RoomActivities.RoomActivity;

public class BookingActivity extends AppCompatActivity {
    Button btn_create_booking, btn_delete_booking, btn_update_booking,
            btn_booking_to_user_menu, btn_booking_to_room_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        btn_create_booking = findViewById(R.id.btn_create_booking);
        btn_booking_to_user_menu = findViewById(R.id.btn_booking_to_user_menu);
        btn_booking_to_room_menu = findViewById(R.id.btn_booking_to_room_menu);

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