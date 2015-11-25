package gov.idaho.isp.fitness.challenge;

import gov.idaho.isp.fitness.role.Role;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Employee implements ChallengeParticipant, Comparable<Employee> {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMP_SEQ")
  @SequenceGenerator(name = "EMP_SEQ", sequenceName = "ID_SEQ", allocationSize = 1)
  private Long id;

  @NotNull @Size(max=255)
  private String uuid;

  @NotNull @Size(max=255)
  private String username;

  @NotNull @Size(max=255)
  @Column(name="FIRST_NAME")
  private String firstName;

  @NotNull @Size(max=255)
  @Column(name="LAST_NAME")
  private String lastName;

  @Size(max=255)
  private String title;

  @Size(max=255)
  private String email;

  @NotNull @Size(max=255)
  private String department;

  private LocalDate dob;

  private Gender gender = Gender.U;

  @Column(name="STARTING_BODY_WEIGHT")
  private BigDecimal startingBodyWeight;

  @OneToMany(mappedBy = "employee")
  private List<FitnessLogEntry> fitnessLog;

  private District district;

  private Boolean commissioned;

  @Column(name="ACTIVELY_EMPLOYED")
  private Boolean activelyEmployed;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name="SPOUSE_ID")
  private Spouse spouse;

  @ElementCollection(targetClass=Role.class, fetch = FetchType.EAGER)
  @Enumerated(EnumType.STRING)
  @CollectionTable(name="EMPLOYEE_ROLES")
  @Column(name="ROLE")
  private Set<Role> roles;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
  private Set<SecretBadge> secretBadges;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @Override
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public LocalDate getDob() {
    return dob;
  }

  public void setDob(LocalDate dob) {
    this.dob = dob;
  }

  @Override
  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public District getDistrict() {
    return district;
  }

  public void setDistrict(District district) {
    this.district = district;
  }

  public Boolean getCommissioned() {
    return commissioned;
  }

  public void setCommissioned(Boolean commissioned) {
    this.commissioned = commissioned;
  }

  public Boolean getActivelyEmployed() {
    return activelyEmployed;
  }

  public void setActivelyEmployed(Boolean activelyEmployed) {
    this.activelyEmployed = activelyEmployed;
  }

  public Spouse getSpouse() {
    return spouse;
  }

  public void setSpouse(Spouse spouse) {
    this.spouse = spouse;
  }

  public boolean hasRole(Role role) {
    return roles != null && roles.contains(role);
  }

  public boolean hasRoles(Set<Role> roles) {
    return roles != null && roles.containsAll(roles);
  }

  public Set<Role> getRoles() {
    if (roles == null) {
      roles = new HashSet<>();
    }
    return roles;
  }

  public Set<SecretBadge> getSecretBadges() {
    return secretBadges;
  }

  public Set<SecretBadge> getSecretBadges(ChallengeYear year) {
    if (secretBadges != null) {
      return secretBadges.stream().filter(sb -> sb.getChallengeYear() == year).collect(Collectors.toSet());
    }
    return Collections.emptySet();
  }

  public boolean hasSecretBadge(ChallengeYear year, Badge b) {
    return getSecretBadges(year).stream().anyMatch(sb -> sb.getBadge() == b);
  }

  public void setSecretBadges(Set<SecretBadge> secretBadges) {
    this.secretBadges = secretBadges;
  }

  @Override
  public String getNameLastFirst() {
    if (lastName == null || firstName == null) {
      return null;
    }
    return lastName + ", " + firstName;
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
  public int compareTo(Employee emp) {
    return lastName.compareTo(emp.lastName);
  }

  @Override
  public Boolean isEmployee() {
    return Boolean.TRUE;
  }

  @Override @Transient
  public Boolean isSpouse() {
    return Boolean.FALSE;
  }

  @Override
  public String toString() {
    return "Employee{" + "id=" + id + ", uuid=" + uuid + ", username=" + username + ", firstName=" + firstName + ", lastName=" + lastName + ", title=" + title + ", email=" + email + ", department=" + department + ", dob=" + dob + ", gender=" + gender + ", startingBodyWeight=" + startingBodyWeight + ", district=" + district + ", commissioned=" + commissioned + ", activelyEmployed=" + activelyEmployed + ", spouse=" + spouse + ", roles=" + roles + ", secretBadges=" + secretBadges + '}';
  }
}