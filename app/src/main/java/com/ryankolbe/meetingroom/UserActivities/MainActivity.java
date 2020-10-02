package com.ryankolbe.meetingroom.UserActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ryankolbe.meetingroom.Database.DatabaseClient;
import com.ryankolbe.meetingroom.Database.MeetingDAO;
import com.ryankolbe.meetingroom.Model.User;
import com.ryankolbe.meetingroom.R;

public class MainActivity extends AppCompatActivity {
    EditText et_login_username, et_login_password;
    Button btn_login_user, btn_register_intent;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_login_username = findViewById(R.id.et_login_username);
        et_login_password = findViewById(R.id.et_login_password);
        btn_login_user = findViewById(R.id.btn_login_user);
        btn_register_intent = findViewById(R.id.btn_register_intent);

        btn_login_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = new User();
                user.setUsername(et_login_username.getText().toString().trim());
                user.setPassword(et_login_password.getText().toString().trim());

                if (user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill in all fields ...", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseClient databaseClient = DatabaseClient.getInstance(getApplicationContext());
                    final MeetingDAO meetingDAO = databaseClient.getDatabase().meetingDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            User userEntity = meetingDAO.loginUser(user.getUsername(), user.getPassword());
                            if (userEntity == null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        startActivity(new Intent(MainActivity.this, UserActivity.class));
                                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }).start();
                }
                et_login_username.setText("");
                et_login_password.setText("");
            }
        });

        btn_register_intent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }
}