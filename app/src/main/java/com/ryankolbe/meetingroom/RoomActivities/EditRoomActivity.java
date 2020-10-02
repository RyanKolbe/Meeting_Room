package com.ryankolbe.meetingroom.RoomActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.ryankolbe.meetingroom.CustomAdapters.RoomCustomAdapter;
import com.ryankolbe.meetingroom.Database.DatabaseClient;
import com.ryankolbe.meetingroom.Database.MeetingDAO;
import com.ryankolbe.meetingroom.Model.Room;
import com.ryankolbe.meetingroom.R;

public class EditRoomActivity extends AppCompatActivity {

    EditText et_edit_room_id, et_edit_room_name, et_edit_room_location,
            et_edit_room_available, et_edit_room_equipment;
    Button btn_delete_room, btn_update_room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_room);

        et_edit_room_id = findViewById(R.id.et_edit_room_id);
        et_edit_room_name = findViewById(R.id.et_edit_room_name);
        et_edit_room_location = findViewById(R.id.et_edit_room_location);
        et_edit_room_available = findViewById(R.id.et_edit_room_available);
        et_edit_room_equipment = findViewById(R.id.et_edit_room_equipment);
        btn_delete_room = findViewById(R.id.btn_delete_room);
        btn_update_room = findViewById(R.id.btn_update_room);

        et_edit_room_id.setText(String.valueOf(getIntent().getLongExtra("room_id", 0)));
        et_edit_room_name.setText(getIntent().getStringExtra("room_name"));
        et_edit_room_location.setText(getIntent().getStringExtra("room_location"));
        et_edit_room_available.setText(getIntent().getStringExtra("room_available"));
        et_edit_room_equipment.setText(getIntent().getStringExtra("room_equipment"));

        final long id = getIntent().getLongExtra("room_id", 0);

        btn_delete_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MeetingDAO meetingDAO = DatabaseClient.getInstance(getApplicationContext())
                                .getDatabase().meetingDao();
                        Room deleteRoom = meetingDAO.searchRoom(id);
                        meetingDAO.deleteRoom(deleteRoom);
                        startActivity(new Intent(getApplicationContext(), RoomActivity.class));
                    }
                }).start();
            }
        });

        btn_update_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String newRoomName = et_edit_room_name.getText().toString();
                        String newRoomLocation = et_edit_room_location.getText().toString();
                        String newRoomAvailability = et_edit_room_available.getText().toString();
                        String newRoomEquipment = et_edit_room_equipment.getText().toString();

                        MeetingDAO meetingDAO = DatabaseClient.getInstance(getApplicationContext()).
                                getDatabase().meetingDao();

                        RoomCustomAdapter roomCustomAdapter = new RoomCustomAdapter(getApplicationContext(),
                                meetingDAO.getAllRooms());
                        Room updateRoom = meetingDAO.searchRoom(id);

                        updateRoom.setRoomName(newRoomName);
                        updateRoom.setRoomLocation(newRoomLocation);
                        updateRoom.setRoomAvailable(newRoomAvailability);
                        updateRoom.setRoomEquipmentAvailable(newRoomEquipment);

                        meetingDAO.updateRoom(updateRoom);
                        roomCustomAdapter.notifyDataSetChanged();
                        startActivity(new Intent(getApplicationContext(), RoomActivity.class));
                    }
                }).start();
            }
        });
    }
}