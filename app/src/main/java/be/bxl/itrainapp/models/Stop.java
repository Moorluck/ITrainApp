package be.bxl.itrainapp.models;

public class Stop {

    private String name;
    private String departureTime;
    private int retard;

    public Stop(String name, String departureTime, int retard) {
        this.name = name;
        this.departureTime = departureTime;
        this.retard = retard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public int getRetard() {
        return retard;
    }

    public void setRetard(int retard) {
        this.retard = retard;
    }
}
