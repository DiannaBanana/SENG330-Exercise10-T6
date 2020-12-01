package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SimpleHashStoreTest {

  private SimpleHashStore store;

  @BeforeEach
  void setUp() {
    store = new SimpleHashStore();
  }


  @Test
  void testGetInvalidId(){
    assertThat(store.getObservationById(0)).isEmpty();
  }
}