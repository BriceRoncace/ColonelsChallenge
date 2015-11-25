package gov.idaho.isp.fitness.challenge;

import gov.idaho.isp.fitness.challenge.persistence.listener.FitnessLogEntryEntityListener;
import gov.idaho.isp.fitness.challenge.validation.CardioTypeRequired;
import gov.idaho.isp.fitness.challenge.validation.DateWithinChallenge;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
import javax.validation.constraints.Size;

@Entity
@EntityListeners(FitnessLogEntryEntityListener.class)
@Table(name="FITNESS_LOG_ENTRY")
@DateWithinChallenge @CardioTypeRequired
public class FitnessLogEntry {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOG_SEQ")
  @SequenceGenerator(name = "LOG_SEQ", sequenceName = "ID_SEQ", allocationSize = 1)
  private Long id;

  @ManyToOne
  @JoinColumn(name="EMPLOYEE_ID")
  private Employee employee;

  @ManyToOne
  @JoinColumn(name="SPOUSE_ID")
  private Spouse spouse;

  @NotNull
  @Column(name="ACTIVITY_DATE")
  private LocalDate activityDate;

  @NotNull
  @Column(name="CHALLENGE_YEAR")
  @Enumerated(EnumType.STRING)
  private ChallengeYear challengeYear;

  @Size(max=255)
  private String title;

  @Size(max=1000)
  private String notes;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name="EXERCISE_TYPE")
  private ExerciseType exerciseType;

  private Integer hours;
  private Integer minutes;
  private Integer seconds;

  @Enumerated(EnumType.STRING)
  @Column(name="CARDIO_TYPE")
  CardioType cardioType;

  private BigDecimal distance;

  @Column(name="WEIGHT")
  private BigDecimal totalWeight;

  private Integer reps;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public Spouse getSpouse() {
    return spouse;
  }

  public void setSpouse(Spouse spouse) {
    this.spouse = spouse;
  }

  public LocalDate getActivityDate() {
    return activityDate;
  }

  public void setActivityDate(LocalDate activityDate) {
    this.activityDate = activityDate;
  }

  public Long getActivityDateEpochMilli() {
    if (activityDate != null) {
      return activityDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    return null;
  }

  public Date getActivityDateAsUtilDate() {
    Long time = getActivityDateEpochMilli();
    return time != null ? new Date(time) : null;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public ChallengeYear getChallengeYear() {
    return challengeYear;
  }

  public void setChallengeYear(ChallengeYear challengeYear) {
    this.challengeYear = challengeYear;
  }

  public ExerciseType getExerciseType() {
    return exerciseType;
  }

  public void setExerciseType(ExerciseType exerciseType) {
    this.exerciseType = exerciseType;
  }

  public Integer getHours() {
    return hours;
  }

  public void setHours(Integer hours) {
    this.hours = hours;
  }

  public Integer getMinutes() {
    return minutes;
  }

  public void setMinutes(Integer minutes) {
    this.minutes = minutes;
  }

  public Integer getSeconds() {
    return seconds;
  }

  public void setSeconds(Integer seconds) {
    this.seconds = seconds;
  }

  public CardioType getCardioType() {
    return cardioType;
  }

  public void setCardioType(CardioType cardioType) {
    this.cardioType = cardioType;
  }

  public BigDecimal getDistance() {
    return distance != null ? distance : BigDecimal.ZERO;
  }

  public void setDistance(BigDecimal distance) {
    this.distance = distance;
  }

  public BigDecimal getTotalWeight() {
    return totalWeight != null ? totalWeight : BigDecimal.ZERO;
  }

  public void setTotalWeight(BigDecimal totalWeight) {
    this.totalWeight = totalWeight;
  }

  public Integer getReps() {
    return reps != null ? reps : 0;
  }

  public void setReps(Integer reps) {
    this.reps = reps;
  }

  public Boolean hasTime() {
    return hours != null || minutes != null || seconds != null;
  }

  public String getFormattedTime() {
    StringBuilder time = new StringBuilder();
    time.append(hours != null ? (hours + ":") : "0:")
        .append(minutes != null ? (minutes < 10 ? "0" + minutes + ":" : minutes + ":") : "00:")
        .append(seconds != null ? (seconds < 10 ? "0" + seconds : seconds) : "00");
    return time.toString();
  }

  public BigDecimal getTotalTimeInHours() {
    BigDecimal h = hours != null ? new BigDecimal(hours.toString()) : BigDecimal.ZERO;
    BigDecimal m = minutes != null ? new BigDecimal(minutes.toString()) : BigDecimal.ZERO;
    BigDecimal s = seconds != null ? new BigDecimal(seconds.toString()) : BigDecimal.ZERO;
    return h.add(m.divide(new BigDecimal("60"), 2, BigDecimal.ROUND_HALF_UP)).add(s.divide(new BigDecimal("3600"), 2, BigDecimal.ROUND_HALF_UP));
  }

  public boolean getBelongsToEmployee() {
    return employee != null;
  }

  public boolean getBelongsToSpouse() {
    return spouse != null;
  }

  @Override
  public String toString() {
    return "FitnessLogEntry{" + "id=" + id + ", spouse=" + spouse + ", activityDate=" + activityDate + ", challengeYear=" + challengeYear + ", title=" + title + ", notes=" + notes + ", exerciseType=" + exerciseType + ", hours=" + hours + ", minutes=" + minutes + ", seconds=" + seconds + ", cardioType=" + cardioType + ", distance=" + distance + ", totalWeight=" + totalWeight + ", reps=" + reps + '}';
  }
}