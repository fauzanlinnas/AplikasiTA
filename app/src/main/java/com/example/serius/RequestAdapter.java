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
        TextView tvRequestNama, tvRequestGoldar, tvRequestJumlah, tvRequestHape, tvRequestNamaDokter, tvRequestTelpRumahSakit;

        RequestBlood request = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.request_list, parent, false);
        }

        tvRequestNama = convertView.findViewById(R.id.tvRequestNama);
        tvRequestGoldar = convertView.findViewById(R.id.tvRequestGoldar);
        tvRequestJumlah = convertView.findViewById(R.id.tvRequestJumlah);
        tvRequestHape = convertView.findViewById(R.id.tvRequestHape);
        tvRequestNamaDokter = convertView.findViewById(R.id.tvRequestNamaDokter);
        tvRequestTelpRumahSakit = convertView.findViewById(R.id.tvRequestTelpRumahSakit);


        tvRequestNama.setText(request.getName());
        tvRequestGoldar.setText(request.getGoldar());
        tvRequestJumlah.setText("Kebutuhan kantong: " + request.getJumlah());
        tvRequestHape.setText(request.getHape());
        tvRequestNamaDokter.setText(request.getNamaDokter());
        tvRequestTelpRumahSakit.setText(request.getNoTelpRumahSakit());

        return convertView;
    }
}
