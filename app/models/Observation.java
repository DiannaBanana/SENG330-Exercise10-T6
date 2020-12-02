package models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Observation {
  private static long observationCount = 0;

  private final List<Whale> whales = new ArrayList<>();
  private final LocalDateTime time;
  private final String location;
  private final Long id;

  public Observation(LocalDateTime time, String location){
    this.time = time;
    this.location = location;
    this.id = observationCount++;
  }


  public Long getId() {
    return id;
  }

  public String getLocation() {
    return location;
  }

  public LocalDateTime getTime() {
    return time;
  }

  public List<Whale> getWhales() {
    return whales;
  }
}
