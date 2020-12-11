package models;

import databuilders.ObservationBuilder;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class WhaleModelTest {
    WhaleModel model;

    @BeforeEach
    public void init(){
        model = new WhaleModel();
    }


    @Test
    public void addObservationToModel(){
        init();
        Observation ob = new ObservationBuilder().atLocation("Home")
                .observedAt(LocalDateTime.of(2001, 12, 12, 12, 12)).build();

        model.getObservationStore().addObservation(ob);

        assertEquals(ob, model.getObservationStore().getObservationById(ob.getId()).get());
    }

    @Test
    public void testSetBehaviourWithMultipleAccess(){
        init();
        Observation ob = new ObservationBuilder().atLocation("Home")
                .observedAt(LocalDateTime.of(2001, 12, 12, 12, 12)).build();

        Whale w = new Whale("orca", 100, "f");
        ob.getWhales().add(w);

        model.getObservationStore().addObservation(ob);
        model.getObservationStore().getObservationById(ob.getId()).get().getWhales().add(w);
        model.getWhaleStore().addWhale(w, ob.getId());

        assertEquals(1, ob.getWhales().size());
    }




}
