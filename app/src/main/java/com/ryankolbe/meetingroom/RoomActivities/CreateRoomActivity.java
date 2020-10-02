package com.ryankolbe.meetingroom.RoomActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ryankolbe.meetingroom.CustomAdapters.RoomCustomAdapter;
import com.ryankolbe.meetingroom.Database.DatabaseClient;
import com.ryankolbe.meetingroom.Database.MeetingDAO;
import com.ryankolbe.meetingroom.Model.Room;
import com.ryankolbe.meetingroom.R;

import java.util.ArrayList;
import java.util.List;

public class CreateRoomActivity extends AppCompatActivity {

    private Room roomTemp;
    private List<Room> roomList = new ArrayList<>();
    TextView tv_create_room;
    EditText et_create_room_name, et_create_room_location,
            et_create_room_available, et_create_room_equipment;
    Button btn_create_room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        tv_create_room = findViewById(R.id.tv_create_room);
        et_create_room_name = findViewById(R.id.et_create_room_name);
        et_create_room_location = findViewById(R.id.et_create_room_location);
        et_create_room_available = findViewById(R.id.et_create_room_available);
        et_create_room_equipment = findViewById(R.id.et_create_room_equipment);
        btn_create_room = findViewById(R.id.btn_create_room);

        btn_create_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roomTemp = new Room();
                roomTemp.setRoomName(et_create_room_name.getText().toString().trim());
                roomTemp.setRoomLocation(et_create_room_location.getText().toString().trim());
                roomTemp.setRoomAvailable(et_create_room_available.getText().toString().trim());
                roomTemp.setRoomEquipmentAvailable(et_create_room_equipment.getText().toString().trim());

                if (roomTemp.getRoomName().isEmpty() || roomTemp.getRoomLocation().isEmpty() ||
                        roomTemp.getRoomAvailable().isEmpty() || roomTemp.getRoomEquipmentAvailable().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill in all fields ...", Toast.LENGTH_SHORT).show();
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            MeetingDAO meetingDAO = DatabaseClient.getInstance(getApplicationContext())
                                    .getDatabase().meetingDao();
                            roomList.addAll(meetingDAO.getAllRooms());
                        }
                    }).start();
                    if (roomList.contains(roomTemp)) {
                        Toast.makeText(getApplicationContext(), "Meeting room already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                final MeetingDAO meetingDAO = DatabaseClient.getInstance(getApplicationContext())
                                        .getDatabase().meetingDao();
                                meetingDAO.insertRoom(roomTemp);
                                RoomCustomAdapter customAdapter = new RoomCustomAdapter(getApplicationContext(), meetingDAO.getAllRooms());
                                customAdapter.notifyDataSetChanged();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Room Created", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), RoomActivity.class));
                                    }
                                });
                            }
                        }).start();
                    }


                }
            }
        });


    }
}