package models;

import javax.inject.Singleton;

@Singleton
public class WhaleModel {
  private final ObservationStore observationStore;
  private final WhaleStore whaleStore;

  public WhaleModel(){
    SimpleHashStore store = new SimpleHashStore();
    observationStore = store;
    whaleStore = store;
  }

  public ObservationStore getObservationStore(){
    return observationStore;
  }

  public WhaleStore getWhaleStore(){return whaleStore;}
}
