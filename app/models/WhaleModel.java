package models;

public class WhaleModel {
  private static final WhaleModel INSTANCE = new WhaleModel();

  public static WhaleModel getInstance(){
    return INSTANCE;
  }

  private final ObservationStore store;

  private WhaleModel(){
    store = new SimpleHashStore();
  }

  public ObservationStore getObservationStore(){
    return store;
  }

}
