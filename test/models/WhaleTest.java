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

}
