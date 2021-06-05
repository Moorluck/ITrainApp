package be.bxl.itrainapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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



        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        //TODO Spinner custom adapter (avec 12sp)

        List<String> spinnerString = new ArrayList<>();
        spinnerString.add("Hello");
        spinnerString.add("World");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                getContext(),
                R.layout.support_simple_spinner_dropdown_item,
                android.R.id.text1,
                spinnerString
        );

        spinnerFavorites.setAdapter(spinnerAdapter);
    }
}