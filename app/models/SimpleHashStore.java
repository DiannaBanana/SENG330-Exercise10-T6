package models;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class SimpleHashStore implements ObservationStore, WhaleStore{
  private final HashMap<Long, Observation> observations = new HashMap<>();
  private final HashMap<Long, Observation> searchResult = new HashMap<>();


  @Override
  public Optional<Observation> getObservationById(long id) {
    return Optional.ofNullable(observations.get(id));
  }

  @Override
  public List<Observation> getObservations() {
    return new ArrayList<>(observations.values());
  }

  @Override
  public void addObservation(Observation toAdd) {
    observations.put(toAdd.getId(), toAdd);
  }

  @Override
  public void clearAll() {
    observations.clear();
  }

  @Override
  public void removeObservationById(long id) {
    observations.remove(id);
  }

  @Override
  public List<Whale> getAllWhales() {
    List<Whale> whales = new ArrayList<>();
    for (Observation o :
            observations.values()) {
      whales.addAll(o.getWhales());
    }
    return whales;
  }

  @Override
  public void addWhale(Whale w, long obsId) throws InvalidParameterException {
    if(observations.containsKey(obsId)){
      observations.get(obsId).getWhales().add(w);
    } else {
      throw new InvalidParameterException("No observation exists with id " + obsId);
    }
  }


}

