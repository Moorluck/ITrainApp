package be.bxl.itrainapp.trainapi;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import be.bxl.itrainapp.models.Stop;
import be.bxl.itrainapp.models.Train;
import be.bxl.itrainapp.utils.Convert;

public class RequestStops extends AsyncTask<String, Void, ArrayList<Stop>> {

    private final String BASE_URL = "https://api.irail.be/vehicle/?id=__trainID__&format=json&lang=fr&alerts=false";

    @Override
    protected ArrayList<Stop> doInBackground(String... trainID) {

        if (trainID == null || trainID.length != 1) {
            throw new RuntimeException("Issue with request");
        }

        String requestResult = null;
        HttpURLConnection connection = null;

        try {
            URL url = new URL(BASE_URL.replace("__trainID__", trainID[0]));

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

        ArrayList<Stop> stops = null;

        if (requestResult != null) {
            stops = parseStopResult(requestResult);
        }

        return stops;
    }

    public interface StopListener {
        void onDataReceived(ArrayList<Stop> stops);
    }

    StopListener stopListener;

    public void setStopListener(StopListener stopListener) {
        this.stopListener = stopListener;
    }

    @Override
    protected void onPostExecute(ArrayList<Stop> stops) {
        super.onPostExecute(stops);

        stopListener.onDataReceived(stops);
    }

    private ArrayList<Stop> parseStopResult(String requestResult) {

        ArrayList<Stop> stops = new ArrayList<>();

        try {
            JSONObject json = new JSONObject(requestResult);
            JSONObject stopsJson = json.getJSONObject("stops");
            int numberOfStop = stopsJson.getInt("number");
            JSONArray arrayOfStop = stopsJson.getJSONArray("stop");

            for (int i = 0; i < numberOfStop - 1; i++) {
                JSONObject stop = arrayOfStop.getJSONObject(i);

                String departure = Convert.convertLongToTime(stop.getLong("time"));
                int delay = stop.getInt("delay")/60;
                String station = stop.getString("station");

                Stop newStop = new Stop(station, departure, delay);

                stops.add(newStop);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return stops;

    }
}
