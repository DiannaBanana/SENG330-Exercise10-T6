package models;

import java.util.*;

public class SimpleHashStore implements ObservationStore, WhaleStore{
  private final HashMap<Long, Observation> observations = new HashMap<>();
  private final HashMap<Long, Whale> whales = new HashMap<>();

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

  @Override
  public List<Whale> getAllWhales() {
    return new ArrayList<>(whales.values());
  }
}

