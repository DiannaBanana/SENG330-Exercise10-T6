package controllers;

import java.time.LocalDateTime;

public class ObservationData {
    private String location = "";
    private String time = "";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
        LocalDateTime.parse(time);
    }

    public LocalDateTime parsedTime(){
        return LocalDateTime.parse(time);
    }
}
