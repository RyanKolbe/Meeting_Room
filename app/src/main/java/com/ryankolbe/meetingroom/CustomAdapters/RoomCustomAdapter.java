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
import com.ryankolbe.meetingroom.Model.Room;
import com.ryankolbe.meetingroom.R;

import java.util.List;

public class RoomCustomAdapter extends ArrayAdapter<Room> {
    private Context mContext;
    private List<Room> roomList;


    @SuppressLint("SupportAnnotationUsage")
    public RoomCustomAdapter(@NonNull Context context, @LayoutRes List<Room> rooms) {
        super(context, 0, rooms);
        mContext = context;
        roomList = rooms;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View room_list_view = convertView;
        if (room_list_view == null) {
            room_list_view = LayoutInflater.from(mContext)
                    .inflate(R.layout.room_list_view, parent, false);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    roomList = DatabaseClient.getInstance(getContext())
                            .getDatabase().meetingDao().getAllRooms();
                }
            }).start();

            TextView room_id = room_list_view.findViewById(R.id.lv_tv_room_id);
            room_id.setText(String.valueOf(roomList.get(position).getRoomID()));

            TextView room_name = room_list_view.findViewById(R.id.lv_tv_room_name);
            room_name.setText(roomList.get(position).getRoomName());

            TextView room_location = room_list_view.findViewById(R.id.lv_tv_room_location);
            room_location.setText(roomList.get(position).getRoomLocation());

            TextView room_equipment = room_list_view.findViewById(R.id.lv_tv_room_equipment);
            room_equipment.setText(roomList.get(position).getRoomEquipmentAvailable());

            TextView room_availability = room_list_view.findViewById(R.id.lv_tv_room_availability);
            room_availability.setText(roomList.get(position).getRoomAvailable());
        }
        return room_list_view;
    }
}
