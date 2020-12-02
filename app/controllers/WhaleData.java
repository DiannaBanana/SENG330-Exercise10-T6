package controllers;

import models.Whale;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WhaleData {
    private String species = "";
    private String location = "";
    private String gender = "";


    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public static List<String> genderOptions(){
        return Arrays.stream(Whale.Gender.values()).map(Whale.Gender::toString).collect(Collectors.toList());
    }

}
