package com.example.serius;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class UsersAdapter extends ArrayAdapter<UserProfile> {
    public UsersAdapter(Context context, ArrayList<UserProfile> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserProfile user = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.donor_list, parent, false);
        }

        TextView tvDonorNama = convertView.findViewById(R.id.tvDonorNama);
        TextView tvDonorGoldar = convertView.findViewById(R.id.tvDonorGoldar);
        TextView tvDonorNoHape = convertView.findViewById(R.id.tvDonorNoHape);

        tvDonorNama.setText(user.userName);
        tvDonorGoldar.setText(user.userGoldar);
        tvDonorNoHape.setText(user.userTelepon);

        return convertView;
    }
}
