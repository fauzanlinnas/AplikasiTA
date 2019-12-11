package com.example.serius;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class RequestAdapter extends ArrayAdapter<RequestBlood> {
    public RequestAdapter(Context context, ArrayList<RequestBlood> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RequestBlood request = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.request_list, parent, false);
        }

        TextView tvRequestNama = convertView.findViewById(R.id.tvRequestNama);
        TextView tvRequestGoldar = convertView.findViewById(R.id.tvRequestGoldar);
        TextView tvRequestJumlah = convertView.findViewById(R.id.tvRequestJumlah);
        TextView tvRequestHape = convertView.findViewById(R.id.tvRequestHape);

        tvRequestNama.setText(request.name);
        tvRequestGoldar.setText(request.goldar);
        tvRequestJumlah.setText("Kebutuhan kantong: " + request.jumlah);
        tvRequestHape.setText(request.hape);

        return convertView;
    }
}
