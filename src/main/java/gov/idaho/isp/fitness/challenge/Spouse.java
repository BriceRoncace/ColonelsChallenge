
package gov.idaho.isp.fitness.challenge;

import gov.idaho.isp.fitness.challenge.utils.StringUtils;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

@Entity
public class Spouse implements ChallengeParticipant {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMP_SEQ")
  @SequenceGenerator(name = "EMP_SEQ", sequenceName = "ID_SEQ", allocationSize = 1)
  private Long id;

  @NotNull
  @Column(name="FIRST_NAME")
  private String firstName;

  @NotNull
  @Column(name="LAST_NAME")
  private String lastName;

  @OneToOne(mappedBy = "spouse")
  private Employee sponsor;

  @NotNull
  @Enumerated(EnumType.STRING)
  private Gender gender;

  @NotNull
  @Column(name="STARTING_BODY_WEIGHT")
  private BigDecimal startingBodyWeight;

  @OneToMany(mappedBy = "spouse")
  private List<FitnessLogEntry> fitnessLog;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = StringUtils.trimToNull(firstName);
  }

  @Override
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = StringUtils.trimToNull(lastName);
  }

  public Employee getSponsor() {
    return sponsor;
  }

  public void setSponsor(Employee sponsor) {
    this.sponsor = sponsor;
  }

  @Override
  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  @Override
  public BigDecimal getStartingBodyWeight() {
    return startingBodyWeight;
  }

  public void setStartingBodyWeight(BigDecimal startingBodyWeight) {
    this.startingBodyWeight = startingBodyWeight;
  }

  @Override
  public List<FitnessLogEntry> getFitnessLog() {
    return fitnessLog;
  }

  public void setFitnessLog(List<FitnessLogEntry> fitnessLog) {
    this.fitnessLog = fitnessLog;
  }

  @Override
  public String getNameLastFirst() {
    if (lastName == null || firstName == null) {
      return null;
    }
    return lastName + ", " + firstName;
  }

  @Override
  public Boolean isEmployee() {
    return Boolean.FALSE;
  }

  @Override
  public Boolean isSpouse() {
    return Boolean.TRUE;
  }
}