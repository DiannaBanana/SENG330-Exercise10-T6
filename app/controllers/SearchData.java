package controllers;

import models.Whale;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SearchData {

    private String species = "";

    private String time = "";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

    public String getSpecies() {
        return species;
    }

    public Whale.Species parseSpecies(){
        return Whale.Species.fromString(species);
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

    public boolean isDateValid(){
        try{
            LocalDateTime.parse(time, formatter);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public LocalDateTime getParsedTime(){
        return LocalDateTime.parse(time, formatter);
    }
}
