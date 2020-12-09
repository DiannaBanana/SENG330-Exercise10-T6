package models;

import databuilders.ObservationBuilder;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class ObservationTest {


    @Test
    public void testThrowsOnNull(){
        assertThrows(AssertionError.class, () -> new Observation(null, ""));
        assertThrows(AssertionError.class, () -> new Observation(LocalDateTime.now(), null));
    }


    @Test
    public void idIncrements(){
        Observation o = null;
        for (int i = 0; i < 5; i++) {
            o = new Observation(LocalDateTime.now(), "");
        }
        assertEquals(4, o.getId().longValue());
    }

    @Test
    public void addWhaleOnlyOnce(){
        Whale w = new Whale("orca", 100, "f");
        Observation o = new ObservationBuilder().observedAt(LocalDateTime.now()).atLocation("here").build();
        o.getWhales().add(w);
        o.getWhales().add(w);

        assertEquals(1, o.getWhales().size());
    }
}
