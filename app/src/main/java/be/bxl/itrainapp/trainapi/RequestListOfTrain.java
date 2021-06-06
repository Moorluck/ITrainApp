package be.bxl.itrainapp.trainapi;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

import be.bxl.itrainapp.models.Train;
import be.bxl.itrainapp.utils.Convert;

public class RequestListOfTrain extends AsyncTask<String, Void, ArrayList<Train>> {


    private final String BASE_URL = "https://api.irail.be/liveboard/?format=json&lang=fr&station=__station__";

    @Override
    protected ArrayList<Train> doInBackground(String... station) {

        if (station == null || station.length != 1) {
            throw new RuntimeException("Issue with request");
        }


        String requestResult = null;
        HttpURLConnection connection = null;

        try {
            URL url = new URL(BASE_URL.replace("__station__", station[0]));

            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(streamReader);

            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }

            reader.close();
            streamReader.close();

            requestResult = builder.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        ArrayList<Train> trains = null;

        if (requestResult != null) {
            trains = parseRequestResult(requestResult);
        }

        return trains;
    }

    //Post execute

    public interface TrainListener {
        void onDataReceived(ArrayList<Train> trains);
    }

    TrainListener trainListener;

    public void setTrainListener(TrainListener trainListener) {
        this.trainListener = trainListener;
    }

    @Override
    protected void onPostExecute(ArrayList<Train> trains) {
        super.onPostExecute(trains);

        if (trainListener != null) {
            trainListener.onDataReceived(trains);
        }

    }

    //Utils

    private ArrayList<Train> parseRequestResult(String requestResult) {

        ArrayList<Train> trains = new ArrayList<>();

        try {
            JSONObject json = new JSONObject(requestResult);

            String stationDepart = json.getString("station");

            JSONObject departures = json.getJSONObject("departures");
            JSONArray departureArray = departures.getJSONArray("departure");


            for (int i = 0; i < 5; i++) {
                JSONObject departure = departureArray.getJSONObject(i);

                String trainID = departure.getString("vehicle");

                String destination = departure.getString("station");
                int retard = departure.getInt("delay")/60;
                int quai = departure.getInt("platform");


                String timeDeparture = Convert.convertLongToTime(departure.getLong("time"));


                trains.add(new Train(trainID, stationDepart, destination, retard, quai, timeDeparture));
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return trains;

    }


}
