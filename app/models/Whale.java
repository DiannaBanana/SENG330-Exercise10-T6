package models;

import java.util.Objects;

public class Whale {

  private static long numberWhales = 0;

  private final Long id;
  private String species;
  private int estimatedWeight;
  private Gender gender;


  public Whale(String species, int estimatedWeight, String gender) {
    this(species, estimatedWeight, Gender.fromString(gender));
  }

  public Whale(String species, int estimatedWeight, Gender gen) {
    this.id = numberWhales++;
    this.species = species;
    this.estimatedWeight = estimatedWeight;
    this.gender = gen;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Whale)) return false;
    Whale whale = (Whale) o;
    return id.equals(whale.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  public enum Gender {
    MALE ,
    FEMALE ,
    UNKNOWN ;



    public static Gender fromString(String gender) {
      if (gender.equalsIgnoreCase("m") || gender.equalsIgnoreCase("male")) {
        return MALE;
      } else if (gender.equalsIgnoreCase("f") || gender.equalsIgnoreCase("female")) {
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

    @Override
    public String toString() {
      switch (this) {
        case MALE:
          return "Male";
        case FEMALE:
          return "Female";
        case UNKNOWN:
          return "Unknown";
        default:
          throw new IllegalArgumentException();
      }
    }
  }
  }