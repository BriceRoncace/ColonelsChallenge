package gov.idaho.isp.fitness.challenge;

public enum ExerciseType {
	AEROBIC("Aerobic / High Intensity Training", "hours"),
  RESISTANCE("Resistance", "lbs"),
  SITUPS_PUSHUPS("Sit-ups/Push-ups", "reps");

  private final String label;
  private final String units;

  private ExerciseType(String label, String units) {
    this.label = label;
    this.units = units;
  }

  public String getLabel() {
    return label;
  }

  public String getUnits() {
    return units;
  }
}