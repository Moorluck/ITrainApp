package be.bxl.itrainapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Trace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import be.bxl.itrainapp.adapter.TrainAdapter;
import be.bxl.itrainapp.models.Train;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StationFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */

public class StationFragment extends Fragment {

    RecyclerView rvStation;
    TextView tvStation;
    EditText etStation;
    ImageView btnSearch;

    ArrayList<Train> trains;

    public static StationFragment newInstance() {
        return new StationFragment();
    }

    public StationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_station, container, false);

        rvStation = v.findViewById(R.id.rv_station_fragment);
        tvStation = v.findViewById(R.id.tv_favorites_fragment);
        etStation = v.findViewById(R.id.et_station_fragment);
        btnSearch = v.findViewById(R.id.btn_station_fragment_search);

        btnSearch.setOnClickListener(view -> {
            //TODO charge les données selon le etStation avec requete http
        });

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        trains = new ArrayList<>();

        trains.add(new Train("B000DF0", "Marseille", 1, 2, "10:05"));
        trains.add(new Train("B000DF0", "Marseille", 1, 2, "10:05"));
        trains.add(new Train("B000DF0", "Marseille", 1, 2, "10:05"));
        trains.add(new Train("B000DF0", "Marseille", 1, 2, "10:05"));

        rvStation.setLayoutManager(new LinearLayoutManager(getContext()));

        TrainAdapter adapter = new TrainAdapter(trains);
        rvStation.setAdapter(adapter);
    }
}