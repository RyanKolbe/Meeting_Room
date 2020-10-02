package com.ryankolbe.meetingroom.UserActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.ryankolbe.meetingroom.Database.DatabaseClient;
import com.ryankolbe.meetingroom.Model.User;
import com.ryankolbe.meetingroom.R;
import com.ryankolbe.meetingroom.RoomActivities.RoomActivity;
import com.ryankolbe.meetingroom.CustomAdapters.UserCustomAdapter;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {
    List<User> users = new ArrayList<>();
    Button btn_create_user, btn_user_to_rooms_menu, btn_user_to_booking_menu;
    ListView lv_user_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        btn_create_user = findViewById(R.id.btn_create_user);
        btn_user_to_rooms_menu = findViewById(R.id.btn_user_to_rooms_menu);
        btn_user_to_booking_menu = findViewById(R.id.btn_user_to_booking_menu);
        lv_user_data = findViewById(R.id.lv_user_data);

        userRefresh();
        navigation();

        btn_create_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }

    public void userRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                users = DatabaseClient.getInstance(getApplicationContext())
                        .getDatabase().meetingDao().getAllUsers();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        UserCustomAdapter userCustomAdapter = new UserCustomAdapter(getApplicationContext(), users);
                        lv_user_data.setAdapter(userCustomAdapter);
                        lv_user_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                                Intent intent = new Intent(getApplicationContext(), EditUserActivity.class);
                                User userItem = users.get(i);
                                intent.putExtra("user_id", userItem.getUserID());
                                intent.putExtra("username", userItem.getUsername());
                                intent.putExtra("password", userItem.getPassword());
                                startActivity(intent);
                            }
                        });
                    }
                });
            }
        }).start();
    }

    public void navigation() {
        btn_user_to_booking_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), BookingActivity.class));
            }
        });

        btn_user_to_rooms_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RoomActivity.class));
            }
        });
    }
}