package controllers;

import java.time.LocalDateTime;

public class SearchData {

    private String species = "";

    private String time = "";

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public LocalDateTime getParsedTime(){
        return LocalDateTime.parse(time);
    }
}
