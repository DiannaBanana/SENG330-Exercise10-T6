package models;

import java.util.*;

public class SimpleHashStore implements ObservationStore{
  private final HashMap<Long, Observation> observations = new HashMap<>();

  @Override
  public Optional<Observation> getObservationById(long id) {
    return Optional.ofNullable(observations.get(id));
  }

  @Override
  public List<Observation> getObservations() {
    return new ArrayList<>(observations.values());
  }

  @Override
  public void addObservationToStore(Observation toAdd) {
    observations.put(toAdd.getId(), toAdd);
  }
}
