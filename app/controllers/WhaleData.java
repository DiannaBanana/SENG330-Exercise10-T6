package controllers;

import models.Whale;
import play.data.validation.Constraints;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WhaleData {
    private String species = "";
    private String gender = "";

    @Constraints.Min(value = 0)
    private int estimatedWeight = 0;


    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
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

    public int getEstimatedWeight() {
        return estimatedWeight;
    }

    public void setEstimatedWeight(int estimatedWeight) {
        this.estimatedWeight = estimatedWeight;
    }
}
