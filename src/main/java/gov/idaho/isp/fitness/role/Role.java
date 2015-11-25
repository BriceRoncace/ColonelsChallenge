package gov.idaho.isp.fitness.role;

public enum Role {
  ADMIN("System Administrator"),
  EMPLOYEE("ISP Employee");

  private final String label;

  private Role(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}