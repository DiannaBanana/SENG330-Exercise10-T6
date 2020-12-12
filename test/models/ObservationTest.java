package models;

import databuilders.ObservationBuilder;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class ObservationTest {

    @Test
    public void testThrowsOnNull(){
        assertThrows(AssertionError.class, () -> new Observation(null, ""));
        assertThrows(AssertionError.class, () -> new Observation(LocalDateTime.now(), null));
    }


    @Test
    public void idIncrements(){
        Observation o = null;
        o.setObservationCount(0);
        for (int i = 0; i < 5; i++) {
            o = new Observation(LocalDateTime.now(), "Hawaii");
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

    @Test
    public void addMultipleWhales(){
        Whale w = new Whale("fin", 150, "f");
        Whale x = new Whale("beluga", 300, "m");
        Observation obs = new ObservationBuilder().observedAt(LocalDateTime.now()).atLocation("here").build();
        obs.getWhales().add(w);
        obs.getWhales().add(x);

        assertEquals(2, obs.getWhales().size());
    }

}
