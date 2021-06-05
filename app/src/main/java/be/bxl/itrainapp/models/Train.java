package be.bxl.itrainapp.models;

import java.util.ArrayList;

public class Train {

    private String id;
    private String destination;
    private int retard;
    private int quai;
    private String depart;
    private ArrayList<Stop> stops;

    public Train(String id, String destination, int retard, int quai, String depart, ArrayList<Stop> stops) {
        this(id, destination, retard, quai, depart);
        this.stops = stops;
    }

    public Train(String id, String destination, int retard, int quai, String depart) {
        this.id = id;
        this.destination = destination;
        this.retard = retard;
        this.quai = quai;
        this.depart = depart;
        this.stops = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getRetard() {
        return retard;
    }

    public void setRetard(int retard) {
        this.retard = retard;
    }

    public int getQuai() {
        return quai;
    }

    public void setQuai(int quai) {
        this.quai = quai;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public ArrayList<Stop> getStops() {
        return stops;
    }

    public void setStops(ArrayList<Stop> stops) {
        this.stops = stops;
    }
}
