package models;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Observation {

  protected static long observationCount = 0;

  private final Set<Whale> whales = new HashSet<>();
  private final LocalDateTime time;
  private final String location;
  private final Long id;

  /**
   *
   * @param time not null
   * @param location not null
   */
  public Observation(@NotNull LocalDateTime time, @NotNull String location){
    assert time != null : "Time cannot be null";
    assert location != null: "location cannot be null";
    this.time = time;
    this.location = location;
    this.id = observationCount++;
  }

  public Long getId() {
    return id;
  }

  public static void setObservationCount(long observationCount) {
    Observation.observationCount = observationCount;
  }

  public String getLocation() {
    return location;
  }

  public LocalDateTime getTime() {
    return time;
  }

  public Set<Whale> getWhales() {
    return whales;
  }

}
