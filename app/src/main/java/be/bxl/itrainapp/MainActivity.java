package be.bxl.itrainapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import be.bxl.itrainapp.adapter.TrainAdapter;

public class MainActivity extends AppCompatActivity {

    // https://api.irail.be/liveboard/?format=json&station=Gent-Sint-Pieters&lang=fr -> renvoit les trains qui part

    // /vehicle/?id=BE.NMBS.IC1832&date=300917&format=json&lang=en&alerts=false

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Récupère les fragments
        StationFragment stationFragment = StationFragment.newInstance();
        FavoritesFragment favoritesFragment = FavoritesFragment.newInstance();

        // Affiche le fragment initial (station)
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction initialTransaction = fm.beginTransaction();
        initialTransaction.replace(R.id.fl_main_fragment_container, stationFragment);
        initialTransaction.commit();

        // Setup la bottom navigation view pour lancer les différents fragments
        bottomNavigationView = findViewById(R.id.bottom_nav_main);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            if (item.getItemId() == R.id.menu_gare) {

                FragmentTransaction transactionToStationFragment = fm.beginTransaction();
                transactionToStationFragment.replace(R.id.fl_main_fragment_container, stationFragment);

                transactionToStationFragment.commit();

                return true;
            }
            else if (item.getItemId() == R.id.menu_favorites){

                FragmentTransaction transactionToFavoritesFragment = fm.beginTransaction();
                transactionToFavoritesFragment.replace(R.id.fl_main_fragment_container, favoritesFragment);

                transactionToFavoritesFragment.commit();

                return true;
            }
            else {
                return false;
            }

        });

        TrainAdapter.setFavoriteItemClickListener(v -> {
            //TODO
        });

        TrainAdapter.setListItemClickListener(v -> {
            //TODO
        });

    }



}