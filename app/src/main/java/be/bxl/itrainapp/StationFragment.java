package be.bxl.itrainapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Trace;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import be.bxl.itrainapp.adapter.TrainAdapter;
import be.bxl.itrainapp.models.Train;
import be.bxl.itrainapp.trainapi.RequestListOfTrain;

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
    ImageView btnSearch, btnFavorite;

    ArrayList<Train> trains = new ArrayList<>();

    boolean isFavorite = false;

    public static TrainAdapter adapter;

    public void setTvStationText(String stationName) {
        tvStation.setText(stationName);
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

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


        tvStation = v.findViewById(R.id.tv_favorites_fragment);
        etStation = v.findViewById(R.id.et_station_fragment);
        btnSearch = v.findViewById(R.id.btn_station_fragment_search);
        btnFavorite = v.findViewById(R.id.btn_fragment_station_favorites);

        // Gestion du Recycler view
        rvStation = v.findViewById(R.id.rv_station_fragment);
        rvStation.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TrainAdapter(trains);
        rvStation.setAdapter(adapter);

        // Gestion de la requête http



        btnSearch.setOnClickListener(view -> {
            setBtnFavoriteStatus();
            onSearchButtonClickListener.onSearchButtonClick(etStation.getText().toString());
        });

        btnFavorite.setOnClickListener(view2 -> {
            isFavorite = !isFavorite;

            if (!trains.isEmpty()) {
                favoriteItemClickListener.onFavoriteItemClick(trains.get(0).getStation());
                setBtnFavoriteStatus();
            }
        });

        return v;
    }

    // Favorite click

    public interface FavoriteItemClickListener {
        void onFavoriteItemClick(String stationName);
    }

    public static FavoriteItemClickListener favoriteItemClickListener;

    public void setFavoriteItemClickListener(FavoriteItemClickListener favoriteItemClickListener) {
        StationFragment.favoriteItemClickListener = favoriteItemClickListener;
    }

    // Search click

    public interface OnSearchButtonClickListener {
       void onSearchButtonClick(String etValue);
    }

    OnSearchButtonClickListener onSearchButtonClickListener;

    public void setOnSearchButtonClickListener(OnSearchButtonClickListener onSearchButtonClickListener) {
        this.onSearchButtonClickListener = onSearchButtonClickListener;
    }

    // Set btn favorite status

    public void setBtnFavoriteStatus() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

        if (!trains.isEmpty()) {
            String stationNameInPreference = prefs.getString(trains.get(0).getStation(), null);

            if (stationNameInPreference != null) {
                isFavorite = true;
                btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_24);
            } else {
                isFavorite = false;
                btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24);
            }
        }


    }

}