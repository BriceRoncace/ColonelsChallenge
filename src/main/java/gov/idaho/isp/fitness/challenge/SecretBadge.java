package gov.idaho.isp.fitness.challenge;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="EMPLOYEE_SECRET_BADGE")
public class SecretBadge {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BADGE_SEQ")
  @SequenceGenerator(name = "BADGE_SEQ", sequenceName = "ID_SEQ", allocationSize = 1)
  private Long id;

  @Enumerated(EnumType.STRING)
  private Badge badge;

  @ManyToOne
  @JoinColumn(name="EMPLOYEE_ID")
  private Employee employee;

  @NotNull
  @Column(name="CHALLENGE_YEAR")
  @Enumerated(EnumType.STRING)
  private ChallengeYear challengeYear;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Badge getBadge() {
    return badge;
  }

  public void setBadge(Badge badge) {
    this.badge = badge;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public ChallengeYear getChallengeYear() {
    return challengeYear;
  }

  public void setChallengeYear(ChallengeYear challengeYear) {
    this.challengeYear = challengeYear;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 73 * hash + Objects.hashCode(this.badge);
    hash = 73 * hash + Objects.hashCode(this.employee.getId());
    hash = 73 * hash + Objects.hashCode(this.challengeYear);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final SecretBadge other = (SecretBadge) obj;
    if (this.badge != other.badge) {
      return false;
    }
    if (!Objects.equals(this.employee.getId(), other.employee.getId())) {
      return false;
    }
    if (this.challengeYear != other.challengeYear) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "SecretBadge{" + "id=" + id + ", badge=" + badge + ", employee=" + employee.getNameLastFirst() + ", challengeYear=" + challengeYear + '}';
  }
}