package com.ryankolbe.meetingroom.RoomActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.ryankolbe.meetingroom.CustomAdapters.RoomCustomAdapter;
import com.ryankolbe.meetingroom.Database.DatabaseClient;
import com.ryankolbe.meetingroom.Model.Room;
import com.ryankolbe.meetingroom.R;
import com.ryankolbe.meetingroom.UserActivities.BookingActivity;
import com.ryankolbe.meetingroom.UserActivities.UserActivity;

import java.util.ArrayList;
import java.util.List;

public class RoomActivity extends AppCompatActivity {
    List<Room> roomList = new ArrayList<>();
    Button btn_create_room, btn_delete_room, btn_update_room,
            btn_room_to_user_menu, btn_room_to_booking_menu;
    ListView lv_room_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        btn_create_room = findViewById(R.id.btn_create_room);
        btn_delete_room = findViewById(R.id.btn_delete_room);
        btn_update_room = findViewById(R.id.btn_update_room);
        btn_room_to_user_menu = findViewById(R.id.btn_room_to_user_menu);
        btn_room_to_booking_menu = findViewById(R.id.btn_room_to_booking_menu);
        lv_room_data = findViewById(R.id.lv_room_data);

        roomRefresh();
        navigation();

        btn_create_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CreateRoomActivity.class));
            }
        });
    }

    public void roomRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                roomList = DatabaseClient.getInstance(getApplicationContext())
                        .getDatabase().meetingDao().getAllRooms();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RoomCustomAdapter roomCustomAdapter =
                                new RoomCustomAdapter(getApplicationContext(), roomList);
                        lv_room_data.setAdapter(roomCustomAdapter);
                        lv_room_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent = new Intent(getApplicationContext(), EditRoomActivity.class);
                                Room roomItem = roomList.get(i);
                                intent.putExtra("room_id", roomItem.getRoomID());
                                intent.putExtra("room_name", roomItem.getRoomName());
                                intent.putExtra("room_location", roomItem.getRoomLocation());
                                intent.putExtra("room_available", roomItem.getRoomAvailable());
                                intent.putExtra("room_equipment", roomItem.getRoomEquipmentAvailable());
                                startActivity(intent);
                            }
                        });
                    }
                });
            }
        }).start();

    }

    public void navigation() {
        btn_room_to_booking_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), BookingActivity.class));
            }
        });

        btn_room_to_user_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserActivity.class));
            }
        });
    }
}