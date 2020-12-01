package models;

public class Whale {

  private static long numberWhales = 0;

  private final Long id;
  private String species;
  private int estimatedWeight;
  private Gender gender;


  public Whale(String species, int estimatedWeight, String gender) {
    this.id = numberWhales++;
    this.species = species;
    this.estimatedWeight = estimatedWeight;
    this.gender = Gender.fromString(gender);
  }

  public int getEstimatedWeight() {
    return estimatedWeight;
  }

  public void setEstimatedWeight(int estimatedWeight) {
    this.estimatedWeight = estimatedWeight;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public Long getId() {
    return id;
  }

  public String getSpecies() {
    return species;
  }

  public void setSpecies(String species) {
    this.species = species;
  }

  public enum Gender {
    MALE,
    FEMALE,
    UNKNOWN;


    public static Gender fromString(String gender) {
      if (gender.toLowerCase().equals("m")) {
        return MALE;
      } else if (gender.equalsIgnoreCase("f")) {
        return FEMALE;
      } else {
        return UNKNOWN;
      }
    }

    public boolean isMale() {
      return this == MALE;
    }

    public boolean isFemale() {
      return this == FEMALE;
    }
  }
}