package be.bxl.itrainapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import be.bxl.itrainapp.adapter.StopAdapter;
import be.bxl.itrainapp.models.Stop;
import be.bxl.itrainapp.trainapi.RequestStops;

public class DetailActivity extends AppCompatActivity {

    public static final String TRAIN_ID_KEY = "trainID";

    ListView listOfStopView;

    String trainID;
    ArrayList<Stop> stops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        listOfStopView = findViewById(R.id.list_of_stops_detail_activity);

        trainID = getIntent().getStringExtra(TRAIN_ID_KEY);

        RequestStops requestStops = new RequestStops();
        requestStops.setStopListener(listOfStop -> {
            if (listOfStop != null && !listOfStop.isEmpty()) {
                // TODO on assigne les valeurs à la listeView et actualise l'adapter
                StopAdapter stopAdapter = new StopAdapter(getApplicationContext(), listOfStop);
                listOfStopView.setAdapter(stopAdapter);
            }
        });

        requestStops.execute(trainID);
    }
}