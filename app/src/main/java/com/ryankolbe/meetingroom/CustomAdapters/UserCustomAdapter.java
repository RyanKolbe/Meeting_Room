package com.ryankolbe.meetingroom.CustomAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ryankolbe.meetingroom.Database.DatabaseClient;
import com.ryankolbe.meetingroom.Model.User;
import com.ryankolbe.meetingroom.R;

import java.util.List;

import static com.ryankolbe.meetingroom.R.id.lv_tv_user_id;
import static com.ryankolbe.meetingroom.R.id.lv_tv_username;

public class UserCustomAdapter extends ArrayAdapter<User> {

    private Context mContext;
    private List<User> userList;

    @SuppressLint("SupportAnnotationUsage")
    public UserCustomAdapter(@NonNull Context context, @LayoutRes List<User> users) {
        super(context, 0, users);
        mContext = context;
        userList = users;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View user_list_view = convertView;
        if (user_list_view == null) {
            user_list_view = LayoutInflater.from(mContext).
                    inflate(R.layout.user_list_view, parent, false);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    userList = DatabaseClient.getInstance(getContext())
                            .getDatabase().meetingDao().getAllUsers();
                }
            }).start();

            TextView user_id = user_list_view.findViewById(lv_tv_user_id);
            user_id.setText(String.valueOf(userList.get(position).getUserID()));

            TextView username = user_list_view.findViewById(lv_tv_username);
            username.setText(userList.get(position).getUsername());
        }
        return user_list_view;
    }
}