package com.ryankolbe.meetingroom.UserActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ryankolbe.meetingroom.CustomAdapters.UserCustomAdapter;
import com.ryankolbe.meetingroom.Database.DatabaseClient;
import com.ryankolbe.meetingroom.Database.MeetingDAO;
import com.ryankolbe.meetingroom.Model.User;
import com.ryankolbe.meetingroom.R;

public class RegisterActivity extends AppCompatActivity {
    private User userTemp;
    EditText et_register_username, et_register_password, et_confirm_password;
    Button btn_register_user, btn_login_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_register_username = findViewById(R.id.et_register_username);
        et_register_password = findViewById(R.id.et_register_password);
        et_confirm_password = findViewById(R.id.et_confirm_password);
        btn_register_user = findViewById(R.id.btn_register_user);
        btn_login_intent = findViewById(R.id.btn_login_intent);

        btn_register_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userTemp = new User();
                userTemp.setUsername(et_register_username.getText().toString().trim());
                userTemp.setPassword(et_register_password.getText().toString().trim());
                String confirmPassword = et_confirm_password.getText().toString().trim();

                if (userTemp.getUsername().isEmpty() || userTemp.getPassword().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill in all fields ...", Toast.LENGTH_SHORT).show();
                } else {
                    if (userTemp.getPassword().equals(confirmPassword)) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                DatabaseClient databaseClient = DatabaseClient.getInstance(getApplicationContext());
                                final MeetingDAO meetingDAO = databaseClient.getDatabase().meetingDao();
                                meetingDAO.insertUser(userTemp);
                                UserCustomAdapter customAdapter = new UserCustomAdapter(getApplicationContext(), meetingDAO.getAllUsers());
                                customAdapter.notifyDataSetChanged();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), UserActivity.class));
                                    }
                                });
                            }
                        }).start();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Passwords must match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_login_intent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });
    }
}