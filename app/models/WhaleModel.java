package models;

import javax.inject.Singleton;

@Singleton
public class WhaleModel {
  private final ObservationStore observationStore;
  private final WhaleStore whaleStore;
  private final SearchStore searchStore;

  public WhaleModel(){
    SimpleHashStore store = new SimpleHashStore();
    observationStore = store;
    whaleStore = store;
    searchStore = store;
  }

  public ObservationStore getObservationStore(){
    return observationStore;
  }

  public WhaleStore getWhaleStore(){return whaleStore;}

  public SearchStore getSearchStore(){
    return searchStore;
  }
}
