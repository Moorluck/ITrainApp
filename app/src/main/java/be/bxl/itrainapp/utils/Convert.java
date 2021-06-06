package be.bxl.itrainapp.utils;

public class Convert {
    public static String convertLongToTime(long time) {
        long totalSec = time;
        long hours = totalSec/3600 % 24 + 2;
        long min = (totalSec/60) % 60;

        String hoursString = convertLongToTimeFormat(hours);
        String minString = convertLongToTimeFormat(min);

        return String.format("%s:%s", hoursString, minString);
    }

    public static String convertLongToTimeFormat(long time) {
        String timeString = null;

        if (time == 0) {
            timeString = "00";
        }
        else if (time > 0 && time < 10) {
            timeString = "0" + String.valueOf(time);
        }
        else {
            timeString = String.valueOf(time);
        }

        return timeString;
    }
}
