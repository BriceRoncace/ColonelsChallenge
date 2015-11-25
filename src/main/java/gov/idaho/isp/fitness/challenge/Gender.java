package gov.idaho.isp.fitness.challenge;

public enum Gender {
  M("Male"),
  F("Female"),
  U("Unknown");

  private final String label;

  private Gender(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}