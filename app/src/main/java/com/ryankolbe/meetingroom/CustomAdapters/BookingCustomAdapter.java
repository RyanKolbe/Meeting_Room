package com.ryankolbe.meetingroom.CustomAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ryankolbe.meetingroom.Database.DatabaseClient;
import com.ryankolbe.meetingroom.Model.Booking;
import com.ryankolbe.meetingroom.Model.Room;
import com.ryankolbe.meetingroom.Model.User;
import com.ryankolbe.meetingroom.R;

import java.util.List;

import static com.ryankolbe.meetingroom.R.id.lv_tv_booking_date;
import static com.ryankolbe.meetingroom.R.id.lv_tv_booking_room;
import static com.ryankolbe.meetingroom.R.id.lv_tv_booking_username;

public class BookingCustomAdapter extends ArrayAdapter<Booking> {
    private Context mContext;
    private List<Booking> bookingList;
    private List<User> userList;
    private List<Room> roomList;


    @SuppressLint("SupportAnnotationUsage")
    public BookingCustomAdapter(@NonNull Context context, @NonNull List<Booking> bookings,
                                @NonNull List<User> users, @NonNull List<Room> rooms) {
        super(context, 0, bookings);
        mContext = context;
        bookingList = bookings;
        userList = users;
        roomList = rooms;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View booking_list_view = convertView;
        if (booking_list_view == null) {
            booking_list_view = LayoutInflater.from(mContext)
                    .inflate(R.layout.booking_list_view, parent, false);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    bookingList = DatabaseClient.getInstance(getContext())
                            .getDatabase().meetingDao().getAllBookings();
                    userList = DatabaseClient.getInstance(getContext())
                            .getDatabase().meetingDao().getAllUsers();
                    roomList = DatabaseClient.getInstance(getContext())
                            .getDatabase().meetingDao().getAllRooms();

                }
            }).start();

            String bookingId = bookingList.get(position).getRoomID()
                    + " " + bookingList.get(position).getUserID();
            TextView booking_id = booking_list_view.findViewById(lv_tv_booking_username);
            booking_id.setText(bookingId);

            TextView user_name = booking_list_view.findViewById(lv_tv_booking_username);
            user_name.setText(userList.get(position).getUsername());

            TextView room_name = booking_list_view.findViewById(lv_tv_booking_room);
            room_name.setText(roomList.get(position).getRoomName());

            TextView booking_date = booking_list_view.findViewById(lv_tv_booking_date);
            booking_date.setText(String.valueOf(bookingList.get(position).getMeetingDate()));

            TextView booking_start_time = booking_list_view.findViewById(lv_tv_booking_date);
            booking_start_time.setText(String.valueOf(bookingList.get(position).getStartTime()));

            TextView booking_end_time = booking_list_view.findViewById(lv_tv_booking_date);
            booking_end_time.setText(String.valueOf(bookingList.get(position).getEndTime()));
        }
        return booking_list_view;
    }
}