package com.ryankolbe.meetingroom.UserActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.ryankolbe.meetingroom.Database.DatabaseClient;
import com.ryankolbe.meetingroom.Database.MeetingDAO;
import com.ryankolbe.meetingroom.Model.User;
import com.ryankolbe.meetingroom.R;
import com.ryankolbe.meetingroom.CustomAdapters.UserCustomAdapter;

public class EditUserActivity extends AppCompatActivity {

    EditText et_dialogue_username;
    EditText et_dialogue_password;
    EditText et_dialogue_user_id;
    Button btn_delete_user;
    Button btn_update_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        btn_delete_user = findViewById(R.id.btn_delete_user);
        btn_update_user = findViewById(R.id.btn_update_user);
        et_dialogue_username = findViewById(R.id.et_dialogue_username);
        et_dialogue_password = findViewById(R.id.et_dialogue_password);
        et_dialogue_user_id = findViewById(R.id.et_dialogue_user_id);

        et_dialogue_user_id.setText(String.valueOf(getIntent().getLongExtra("user_id", 0)));
        et_dialogue_username.setText(getIntent().getStringExtra("username"));
        et_dialogue_password.setText(getIntent().getStringExtra("password"));

        final long id = getIntent().getLongExtra("user_id", 0);


        btn_delete_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MeetingDAO meetingDAO = DatabaseClient.getInstance(getApplicationContext())
                                .getDatabase().meetingDao();
                        User deleteUser = meetingDAO.searchUser(id);
                        meetingDAO.deleteUser(deleteUser);
                        startActivity(new Intent(getApplicationContext(), UserActivity.class));
                    }
                }).start();
            }
        });

        btn_update_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String newUserName = et_dialogue_username.getText().toString();
                        String newUserPassword = et_dialogue_password.getText().toString();

                        MeetingDAO meetingDAO = DatabaseClient.getInstance(getApplicationContext())
                                .getDatabase().meetingDao();

                        UserCustomAdapter customAdapter = new UserCustomAdapter(getApplicationContext(),
                                meetingDAO.getAllUsers());
                        User updateUser = meetingDAO.searchUser(id);

                        updateUser.setUsername(newUserName);
                        updateUser.setPassword(newUserPassword);

                        meetingDAO.updateUser(updateUser);
                        customAdapter.notifyDataSetChanged();
                        startActivity(new Intent(getApplicationContext(), UserActivity.class));
                    }
                }).start();
            }
        });
    }
}