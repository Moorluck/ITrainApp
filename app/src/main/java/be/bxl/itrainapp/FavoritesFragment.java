package be.bxl.itrainapp;

import android.opengl.Visibility;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import be.bxl.itrainapp.adapter.TrainAdapter;
import be.bxl.itrainapp.models.Train;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritesFragment extends Fragment {

    RecyclerView rvFavorites;
    TextView tvFavorites;
    Spinner spinnerFavorites;
    ImageView btnDeleteFavorites;

    ArrayList<Train> trains = new ArrayList<>();

    public static FavoritesFragment newInstance() {
        FavoritesFragment fragment = new FavoritesFragment();
        return fragment;
    }

    public FavoritesFragment() {
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
        View v = inflater.inflate(R.layout.fragment_favorites, container, false);

        rvFavorites = v.findViewById(R.id.rv_favorites_fragment);
        tvFavorites = v.findViewById(R.id.tv_favorites_fragment);
        spinnerFavorites = v.findViewById(R.id.spinner_favorites_fragment);
        btnDeleteFavorites = v.findViewById(R.id.btn_favorites_fragment_delete);

        btnDeleteFavorites.setOnClickListener(view -> {
            //TODO
        });

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        // Spinner
        //TODO Spinner custom adapter (avec 12sp) et avec les vraies données dans préférences

        List<String> spinnerString = new ArrayList<>();
        spinnerString.add("");
        spinnerString.add("Hello");
        spinnerString.add("World");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                getContext(),
                R.layout.spinner_item,
                R.id.textItem,
                spinnerString
        );

        spinnerFavorites.setAdapter(spinnerAdapter);

        spinnerFavorites.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    tvFavorites.setText(spinnerFavorites.getSelectedItem().toString());
                }
                else {
                    tvFavorites.setText(R.string.tv_fragment_favorites_title);

                    //TODO Chargement des vraies données via requete http
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }


        });

        //Recycler view
        rvFavorites.setLayoutManager(new LinearLayoutManager(getContext()));
        TrainAdapter adapter = new TrainAdapter(trains);
        rvFavorites.setAdapter(adapter);
    }
}