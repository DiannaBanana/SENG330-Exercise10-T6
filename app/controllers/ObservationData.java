package controllers;

import play.data.validation.Constraints;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ObservationData {
    @Constraints.Required
    private String location = "";

    @Constraints.Required
    private String time = "";

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

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

        LocalDateTime.parse(time, formatter);
    }

    public LocalDateTime parsedTime(){
        return LocalDateTime.parse(time, formatter);
    }
}
