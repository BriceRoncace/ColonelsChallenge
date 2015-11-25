package gov.idaho.isp.fitness.challenge.persistence;

import gov.idaho.isp.fitness.challenge.District;
import gov.idaho.isp.fitness.challenge.Gender;
import java.util.List;

public class EmployeeCriteria {
  private List<District> districts;
  private List<String> departments;
  private Gender gender;
  private Boolean commissioned;
  private Boolean participant;

  public List<District> getDistricts() {
    return districts;
  }

  public void setDistricts(List<District> districts) {
    this.districts = districts;
  }

  public List<String> getDepartments() {
    return departments;
  }

  public void setDepartments(List<String> departments) {
    this.departments = departments;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public Boolean getCommissioned() {
    return commissioned;
  }

  public void setCommissioned(Boolean commissioned) {
    this.commissioned = commissioned;
  }

  public Boolean isParticipant() {
    return participant;
  }

  public void setParticipant(Boolean participant) {
    this.participant = participant;
  }
}