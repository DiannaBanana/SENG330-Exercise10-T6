package databuilders;

import models.Observation;
import models.Whale;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ObservationBuilder {

    private LocalDateTime time;
    private String location = "";
    private final List<Whale> whales = new ArrayList<>();

    public ObservationBuilder atLocation(String s){
        location = s;
        return this;
    }

    public ObservationBuilder observedAt(LocalDateTime time){
        this.time = time;
        return this;
    }

    public ObservationBuilder withWhale(Whale w){
        whales.add(w);
        return this;
    }

    public ObservationBuilder withNWhalesSpecies(int n){
        for (int i = 0; i < n; i++) {
            whales.add(new Whale(Whale.Species.UNKNOWN, 0, Whale.Gender.UNKNOWN));
        }
        return this;
    }

    public Observation build(){
        Observation o = new Observation(time, location);
        o.getWhales().addAll(whales);
        return o;
    }

}
