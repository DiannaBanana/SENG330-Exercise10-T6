package models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class WhaleTest {

    @Test
    public void sameTypeOfWhaleNotEqual(){
        Whale w1 = new Whale("orca", 100, "m");
        Whale w2 = new Whale("orca", 100, "m");
        assertNotEquals(w1, w2);
    }

    @Test
    public void idIsEqual(){
        Whale w1 = new Whale("orca", 100, "m");
        assertEquals(w1, w1);
    }

    @Test
    public void idIncrements(){
        Whale w = null;
        w.setNumberWhales(0);
        for (int i = 0; i < 5; i++) {
            w = new Whale("beluga", 300, "f");
            System.out.println(w.getId());
        }
        assertEquals(4, w.getId().longValue());
    }

}
