package be.bxl.itrainapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import be.bxl.itrainapp.adapter.TrainAdapter;
import be.bxl.itrainapp.trainapi.RequestListOfTrain;

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

                transactionToStationFragment.setCustomAnimations(
                        android.R.anim.fade_in,
                        android.R.anim.fade_out,
                        android.R.anim.fade_in,
                        android.R.anim.fade_out
                );

                transactionToStationFragment.replace(R.id.fl_main_fragment_container, stationFragment);

                transactionToStationFragment.commit();

                return true;
            }
            else if (item.getItemId() == R.id.menu_favorites){

                FragmentTransaction transactionToFavoritesFragment = fm.beginTransaction();

                transactionToFavoritesFragment.setCustomAnimations(
                        android.R.anim.fade_in,
                        android.R.anim.fade_out,
                        android.R.anim.fade_in,
                        android.R.anim.fade_out
                );

                transactionToFavoritesFragment.replace(R.id.fl_main_fragment_container, favoritesFragment);


                transactionToFavoritesFragment.commit();

                return true;
            }
            else {
                return false;
            }

        });

        // On Item Click

        TrainAdapter.setListItemClickListener(trainID -> {
            //TODO Go to detail activity
            Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
            intent.putExtra(DetailActivity.TRAIN_ID_KEY, trainID);
            startActivity(intent);
        });

        // Station Fragment

        // Request
        stationFragment.setOnSearchButtonClickListener(station -> {
            RequestListOfTrain requestListOfTrain = new RequestListOfTrain();
            requestListOfTrain.setTrainListener(trainsResult -> {
                if (trainsResult != null && !trainsResult.isEmpty()) {
                    stationFragment.trains.clear();
                    stationFragment.trains.addAll(trainsResult);
                    StationFragment.adapter.notifyDataSetChanged();
                    String stationName = stationFragment.trains.get(0).getStation();
                    stationFragment.setTvStationText(stationName);
                    stationFragment.setBtnFavoriteStatus();
                    hideSoftKeyboard(getWindow().getDecorView());
                }
                else {
                    Toast.makeText(this, getString(R.string.toast_search_fail), Toast.LENGTH_SHORT).show();
                }
            });
            requestListOfTrain.execute(station);
        });

        // Favorites Button in Station Fragment
        stationFragment.setFavoriteItemClickListener(stationName -> {

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = prefs.edit();

            String stationNameInPreference = prefs.getString(stationName, null);

            if (stationNameInPreference != null) {
                editor.remove(stationName);
            }
            else {
                editor.putString(stationName, stationName);
            }

            editor.apply();

        });

        // Favorite Fragment

        // Delete Favorite in Favorite Fragment

        favoritesFragment.setOnDeleteClickListener(stationName -> {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = prefs.edit();

            if (prefs.contains(stationName)) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setCancelable(true);
                builder.setMessage("Voulez vous supprimer cette gare de vos favoris ?");
                builder.setPositiveButton("Oui", (dialog, which) -> {
                    editor.remove(stationName);
                    editor.apply();

                    favoritesFragment.updateFavoritesStationNames();
                    favoritesFragment.spinnerAdapter.notifyDataSetChanged();
                });

                builder.setNegativeButton("Non", (dialog, which) -> {

                });

                AlertDialog alertDialog = builder.create();

                alertDialog.show();

            }

            editor.apply();
        });

        // Request : Spinner selected in favorite fragment

        favoritesFragment.setOnItemSpinnerSelectedListener(stationName -> {
            RequestListOfTrain requestListOfTrain = new RequestListOfTrain();
            requestListOfTrain.setTrainListener(trainsResult -> {
                if (trainsResult != null && !trainsResult.isEmpty()) {
                    favoritesFragment.trains.clear();
                    favoritesFragment.trains.addAll(trainsResult);
                    FavoritesFragment.adapter.notifyDataSetChanged();
                    favoritesFragment.setTvFavoritesText(stationName);
                }
            });
            requestListOfTrain.execute(stationName);
        });
    }

    public void hideSoftKeyboard(View view){
        InputMethodManager imm =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



}