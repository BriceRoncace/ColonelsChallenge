package gov.idaho.isp.fitness.challenge;

import java.util.Arrays;
import java.util.Optional;

public enum District {
  DISTRICT_1("1", "D1"),
  DISTRICT_2("2", "D2"),
  DISTRICT_3("3", "D3"),
  DISTRICT_4("4", "D4"),
  DISTRICT_5("5", "D5"),
  DISTRICT_6("6", "D6");

  private final String label;
  private final String altLabel;

  private District(String label, String altLabel) {
    this.label = label;
    this.altLabel =  altLabel;
  }

  public static District valueOf(Integer district) {
    return District.valueOf("DISTRICT_" + district);
  }

  public static Optional<District> valueOfAltLabel(String altLabel) {
    return Arrays.stream(District.values()).filter(d -> d.altLabel.equals(altLabel)).findFirst();
  }

  public String getLabel() {
    return label;
  }

  public String getAltLabel() {
    return altLabel;
  }
}