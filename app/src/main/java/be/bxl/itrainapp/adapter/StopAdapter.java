package be.bxl.itrainapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import be.bxl.itrainapp.R;
import be.bxl.itrainapp.models.Stop;

public class StopAdapter extends ArrayAdapter<Stop> {
    public StopAdapter(Context context, ArrayList<Stop> stops) {
        super(context, 0, stops);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Stop stop = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_stop, parent, false);
        }

        TextView tvDeparture = convertView.findViewById(R.id.tv_item_stop_departure);
        TextView tvDelay = convertView.findViewById(R.id.tv_item_stop_delay);
        TextView tvStation = convertView.findViewById(R.id.tv_item_stop_station);

        tvDeparture.setText(stop.getDepartureTime());
        tvDelay.setText(String.valueOf(stop.getRetard()));
        tvStation.setText(stop.getName());

        return convertView;
    }
}
