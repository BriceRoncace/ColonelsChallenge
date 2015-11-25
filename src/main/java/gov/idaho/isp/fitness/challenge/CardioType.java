package gov.idaho.isp.fitness.challenge;

public enum CardioType {
	RUNNING("Run/Jog/Walk"),
  SWIMMING("Swim"),
  BIKING("Bike/Spin Cycle/Aerodyne"),
  OTHER("Other");

  private final String label;

  private CardioType(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}