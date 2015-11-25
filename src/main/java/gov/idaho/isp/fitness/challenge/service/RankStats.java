package gov.idaho.isp.fitness.challenge.service;

import gov.idaho.isp.fitness.challenge.ChallengeYear;
import gov.idaho.isp.fitness.challenge.Employee;
import java.math.BigDecimal;
import java.util.List;

/**
 * List of participants sorted by rank in each category.
 */
public class RankStats {
  private ChallengeYear challengeYear;
  private List<Employee> maleSitupPushups;
  private List<Employee> femaleSitupPushups;
  private List<Employee> maleResistance;
  private List<Employee> femaleResistance;
  private List<Employee> maleAerobicHours;
  private List<Employee> femaleAerobicHours;
  private List<Employee> maleRun;
  private List<Employee> femaleRun;
  private List<Employee> maleBike;
  private List<Employee> femaleBike;
  private List<Employee> maleSwim;
  private List<Employee> femaleSwim;

  public ChallengeYear getChallengeYear() {
    return challengeYear;
  }

  public void setChallengeYear(ChallengeYear challengeYear) {
    this.challengeYear = challengeYear;
  }

  public List<Employee> getMaleSitupPushups() {
    return maleSitupPushups;
  }

  public void setMaleSitupPushups(List<Employee> maleSitupPushups) {
    removeIfAtZero(maleSitupPushups, (year, emp) -> new BigDecimal(emp.getTotalSitupsPushups(year)));
    this.maleSitupPushups = maleSitupPushups;
  }

  public List<Employee> getFemaleSitupPushups() {
    return femaleSitupPushups;
  }

  public void setFemaleSitupPushups(List<Employee> femaleSitupPushups) {
    removeIfAtZero(femaleSitupPushups, (year, emp) -> new BigDecimal(emp.getTotalSitupsPushups(year)));
    this.femaleSitupPushups = femaleSitupPushups;
  }

  public List<Employee> getMaleResistance() {
    return maleResistance;
  }

  public void setMaleResistance(List<Employee> maleResistance) {
    removeIfAtZero(maleResistance, (year, emp) -> emp.getTotalWeight(year));
    this.maleResistance = maleResistance;
  }

  public List<Employee> getFemaleResistance() {
    return femaleResistance;
  }

  public void setFemaleResistance(List<Employee> femaleResistance) {
    removeIfAtZero(femaleResistance, (year, emp) -> emp.getTotalWeight(year));
    this.femaleResistance = femaleResistance;
  }

  public List<Employee> getMaleAerobicHours() {
    return maleAerobicHours;
  }

  public void setMaleAerobicHours(List<Employee> maleAerobicHours) {
    removeIfAtZero(maleAerobicHours, (year, emp) -> emp.getTotalAerobicHours(year));
    this.maleAerobicHours = maleAerobicHours;
  }

  public List<Employee> getFemaleAerobicHours() {
    return femaleAerobicHours;
  }

  public void setFemaleAerobicHours(List<Employee> femaleAerobicHours) {
    removeIfAtZero(femaleAerobicHours, (year, emp) -> emp.getTotalAerobicHours(year));
    this.femaleAerobicHours = femaleAerobicHours;
  }

  public List<Employee> getMaleRun() {
    return maleRun;
  }

  public void setMaleRun(List<Employee> maleRun) {
    removeIfAtZero(maleRun, (year, emp) -> emp.getTotalRunDistance(year));
    this.maleRun = maleRun;
  }

  public List<Employee> getFemaleRun() {
    return femaleRun;
  }

  public void setFemaleRun(List<Employee> femaleRun) {
    removeIfAtZero(femaleRun, (year, emp) -> emp.getTotalRunDistance(year));
    this.femaleRun = femaleRun;
  }

  public List<Employee> getMaleBike() {
    return maleBike;
  }

  public void setMaleBike(List<Employee> maleBike) {
    removeIfAtZero(maleBike, (year, emp) -> emp.getTotalBikeDistance(year));
    this.maleBike = maleBike;
  }

  public List<Employee> getFemaleBike() {
    return femaleBike;
  }

  public void setFemaleBike(List<Employee> femaleBike) {
    removeIfAtZero(femaleBike, (year, emp) -> emp.getTotalBikeDistance(year));
    this.femaleBike = femaleBike;
  }

  public List<Employee> getMaleSwim() {
    return maleSwim;
  }

  public void setMaleSwim(List<Employee> maleSwim) {
    removeIfAtZero(maleSwim, (year, emp) -> emp.getTotalSwimDistance(year));
    this.maleSwim = maleSwim;
  }

  public List<Employee> getFemaleSwim() {
    return femaleSwim;
  }

  public void setFemaleSwim(List<Employee> femaleSwim) {
    removeIfAtZero(femaleSwim, (year, emp) -> emp.getTotalSwimDistance(year));
    this.femaleSwim = femaleSwim;
  }

  private static interface EventAreaTotal {
    BigDecimal getTotal(ChallengeYear year, Employee e);
  }

  private void removeIfAtZero(List<Employee> employees, EventAreaTotal total) {
    if (employees != null) {
      employees.removeIf(e -> BigDecimal.ZERO.equals(total.getTotal(challengeYear, e)));
    }
  }
}