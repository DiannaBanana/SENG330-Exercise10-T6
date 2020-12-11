package models;

import java.util.Objects;

public class Whale {

  private static long numberWhales = 0;

  private final Long id;
  private Species species;
  private int estimatedWeight;
  private Gender gender;


  public Whale(String species, int estimatedWeight, String gender) {
    this(Species.fromString(species), estimatedWeight, Gender.fromString(gender));
  }

  public Whale(Species species, int estimatedWeight, Gender gen) {
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

  public Species getSpecies() {
    return species;
  }

  public void setSpecies(Species species) {
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

  public enum Species {
    UNKNOWN,
    HUMPBACK,
    KILLER,
    BLUE,
    FIN,
    GREY,
    ORCA,
    SPERM,
    BELUGA;

    public static Species fromString(String species) {
      if (species.equalsIgnoreCase("Humpback")) {
        return HUMPBACK;
      } else if (species.equalsIgnoreCase("Killer")) {
        return KILLER;
      } else if (species.equalsIgnoreCase("Blue")) {
        return BLUE;
      } else if (species.equalsIgnoreCase("Fin")) {
        return FIN;
      } else if (species.equalsIgnoreCase("Grey")) {
        return GREY;
      } else if (species.equalsIgnoreCase("Orca")) {
        return ORCA;
      } else if (species.equalsIgnoreCase("Sperm")) {
        return SPERM;
      } else if (species.equalsIgnoreCase("Beluga")) {
        return BELUGA;
      } else {
        return UNKNOWN;
      }
    }

    @Override
    public String toString() {
      switch (this) {
        case HUMPBACK:
          return "Humpback";
        case KILLER:
          return "Killer";
        case BLUE:
          return "Blue";
        case FIN:
          return "Fin";
        case GREY:
          return "Grey";
        case ORCA:
          return "Orca";
        case SPERM:
          return "Sperm";
        case BELUGA:
          return "Beluga";
        case UNKNOWN:
          return "Unknown";
        default:
          throw new IllegalArgumentException();
      }
    }
  }

  public enum Gender {
    UNKNOWN,
    MALE,
    FEMALE;



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