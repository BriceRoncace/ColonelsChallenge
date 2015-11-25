package gov.idaho.isp.fitness.challenge;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public interface ChallengeParticipant {
  String getFirstName();
  String getLastName();
  String getNameLastFirst();
  BigDecimal getStartingBodyWeight();
  List<FitnessLogEntry> getFitnessLog();
  Gender getGender();
  Boolean isEmployee();
  Boolean isSpouse();

  default List<FitnessLogEntry> getFitnessLog(ChallengeYear year) {
    if (getFitnessLog() != null) {
      return getFitnessLog().stream().filter(entry -> entry.getChallengeYear() == year).collect(Collectors.toList());
    }
    return getFitnessLog();
  }

  default BigDecimal getTotalAerobicHours(ChallengeYear year) {
    List<FitnessLogEntry> entries = getFitnessLog(year);
    if (entries != null) {
      return entries.stream().map(entry -> entry.getTotalTimeInHours()).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    return BigDecimal.ZERO;
  }

  default BigDecimal getTargetAerobicHours(ChallengeYear year) {
    return new BigDecimal("30");
  }

  default BigDecimal getTotalDistance(ChallengeYear year) {
    List<FitnessLogEntry> entries = getFitnessLog(year);
    if (entries != null) {
      return entries.stream().map(entry -> entry != null ? entry.getDistance() : BigDecimal.ZERO).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    return BigDecimal.ZERO;
  }

  default BigDecimal getTotalRunDistance(ChallengeYear year) {
    List<FitnessLogEntry> entries = getFitnessLog(year);
    if (entries != null) {
      return entries.stream().filter(entry -> entry.getCardioType() == CardioType.RUNNING).map(entry -> entry != null ? entry.getDistance() : BigDecimal.ZERO).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    return BigDecimal.ZERO;
  }

  default BigDecimal getTotalBikeDistance(ChallengeYear year) {
    List<FitnessLogEntry> entries = getFitnessLog(year);
    if (entries != null) {
      return entries.stream().filter(entry -> entry.getCardioType() == CardioType.BIKING).map(entry -> entry != null ? entry.getDistance() : BigDecimal.ZERO).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    return BigDecimal.ZERO;
  }

  default BigDecimal getTotalSwimDistance(ChallengeYear year) {
    List<FitnessLogEntry> entries = getFitnessLog(year);
    if (entries != null) {
      return entries.stream().filter(entry -> entry.getCardioType() == CardioType.SWIMMING).map(entry -> entry != null ? entry.getDistance() : BigDecimal.ZERO).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    return BigDecimal.ZERO;
  }

  default Integer getTotalSitupsPushups(ChallengeYear year) {
    List<FitnessLogEntry> entries = getFitnessLog(year);
    if (entries != null) {
      return entries.stream().mapToInt(entry -> entry != null ? entry.getReps() : 0).sum();
    }
    return 0;
  }

  default Integer getTargetSitupsPushups(ChallengeYear year) {
    return 3500;
  }

  default BigDecimal getTotalWeight(ChallengeYear year){
    List<FitnessLogEntry> entries = getFitnessLog(year);
    if (entries != null) {
      return entries.stream().map(entry -> entry != null ? entry.getTotalWeight() : BigDecimal.ZERO).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    return BigDecimal.ZERO;
  }

  default BigDecimal getWeightTarget(ChallengeYear year) {
    List<FitnessLogEntry> entries = getFitnessLog(year);
    if (entries == null) {
      return new BigDecimal("1000").multiply(new BigDecimal("100"));
    }

    if (getGender() == Gender.M) {
      return new BigDecimal("1500").multiply(getStartingBodyWeight());
    }
    else {
      return new BigDecimal("1000").multiply(getStartingBodyWeight());
    }
  }

  default boolean getDidCompleteAerobicHoursGoal(ChallengeYear year) {
    int compareVal = getTotalAerobicHours(year).compareTo(getTargetAerobicHours(year));
    return compareVal >= 0;
  }

  default boolean getDidCompleteSitupsPushupsGoal(ChallengeYear year) {
    int compareVal = getTotalSitupsPushups(year).compareTo(getTargetSitupsPushups(year));
    return compareVal >= 0;
  }

  default boolean getDidCompleteWeightGoal(ChallengeYear year) {
    BigDecimal weightTarget = getWeightTarget(year);
    if (weightTarget == null || weightTarget.equals(BigDecimal.ZERO)) {
      return false;
    }

    int compareVal = getTotalWeight(year).compareTo(weightTarget);
    return compareVal >= 0;
  }

  default int getNumberOfGoalsReached(ChallengeYear year) {
    int goalsMetCount = 0;
    if (getDidCompleteAerobicHoursGoal(year)) {
      goalsMetCount++;
    }
    if (getDidCompleteSitupsPushupsGoal(year)) {
      goalsMetCount++;
    }
    if (getDidCompleteWeightGoal(year)) {
      goalsMetCount++;
    }
    return goalsMetCount;
  }
}