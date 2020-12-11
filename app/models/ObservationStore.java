package models;

import java.util.List;
import java.util.Optional;

public interface ObservationStore {
  Optional<Observation> getObservationById(long id);
  List<Observation> getObservations();
  void addObservation(Observation toAdd);
  void clearAll();
  void removeObservationById(long id);


  interface IdGenerator{
    long generateId();
  }
}
