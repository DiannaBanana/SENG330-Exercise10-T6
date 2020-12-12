package models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class SimpleHashStore implements ObservationStore, WhaleStore{
  private final HashMap<Long, Observation> observations = new HashMap<>();
  private final Logger log = LoggerFactory.getLogger(WhaleModel.class);

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
    log.debug("Adding " + toAdd + " to observation store");
    observations.put(toAdd.getId(), toAdd);
  }

  @Override
  public void clearAll() {
    log.debug("Removing all observations");
    observations.clear();
  }

  @Override
  public void removeObservationById(long id) {
    log.debug("Removing observation " + id);
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
      log.debug("Added whale " + w.getId());
    } else {
      log.error("Error adding whale to model. No observation exists with id " + obsId);
      throw new InvalidParameterException("No observation exists with id " + obsId);
    }
  }


}

