package gov.idaho.isp.fitness.challenge.service;

import gov.idaho.isp.fitness.challenge.Badge;
import gov.idaho.isp.fitness.challenge.CardioType;
import gov.idaho.isp.fitness.challenge.ChallengeYear;
import gov.idaho.isp.fitness.challenge.Employee;
import gov.idaho.isp.fitness.challenge.FitnessLogEntry;
import gov.idaho.isp.fitness.challenge.Gender;
import gov.idaho.isp.fitness.challenge.SecretBadge;
import gov.idaho.isp.fitness.challenge.persistence.SecretBadgeDirectory;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value = "secretBadgeCalculator")
public class SecretBadgeCalculatorImpl implements SecretBadgeCalculator {
  private SecretBadgeDirectory secretBadgeDirectory;

  private static final BigDecimal MERMAID_SWIM_DISTANCE = new BigDecimal("1");
  private static final BigDecimal SEA_KING_SWIM_DISTANCE = new BigDecimal("10");
  private static final BigDecimal WORKER_HOUR_THRESHOLD = new BigDecimal("2");
  private static final int EARLY_BIRD_THRESHOLD = 5;
  private static final BigDecimal GAZELLE_THRESHOLD = new BigDecimal("10");
  private static final BigDecimal EXTREME_LIFTER_THRESHOLD = new BigDecimal("100000");
  private static final int WORKER_BEE_WORKOUT_THRESHOLD = 50;

  @Override
  public Set<SecretBadge> calcuateSecretBadges(ChallengeYear year, Employee employee) {
    Set<SecretBadge> badges = new HashSet<>();
    badges.add(getMermaidBadgeOrNull(year, employee));
    badges.add(getSeaKingBadgeOrNull(year, employee));
    badges.add(getWorkerBadgeOrNull(year, employee));
    badges.add(getEarlyBirdBadgeOrNull(year, employee));
    badges.add(getGazelleBadgeOrNull(year, employee));
    badges.add(getLuckyDuckOrNull(year, employee));
    badges.add(getHighFlierOrNull(year, employee));
    badges.add(getExtremeLifterOrNull(year, employee));
    badges.add(getWorkerBeeBadgeOrNull(year, employee));
    badges.remove(null);
    return badges;
  }

  private SecretBadge getExtremeLifterOrNull(ChallengeYear year, Employee emp) {
    if (emp.getFitnessLog(year).stream().anyMatch(entry -> EXTREME_LIFTER_THRESHOLD.compareTo(entry.getTotalWeight()) <= 0)) {
      return asSecretBadge(Badge.EXTREME_LIFTER, year, emp);
    }
    return null;
  }

  private SecretBadge getHighFlierOrNull(ChallengeYear year, Employee emp) {
    ChallengeYear lastYear = year.getLastYear();
    if (isParticipant(emp, lastYear)) {
      if ((emp.getDidCompleteAerobicHoursGoal(year) && !emp.getDidCompleteAerobicHoursGoal(lastYear)) ||
          (emp.getDidCompleteWeightGoal(year) && !emp.getDidCompleteWeightGoal(lastYear)) ||
          (emp.getDidCompleteSitupsPushupsGoal(year) && !emp.getDidCompleteSitupsPushupsGoal(lastYear))) {
        return asSecretBadge(Badge.HIGH_FLIER, year, emp);
      }
    }
    return null;
  }

  private boolean isParticipant(Employee emp, ChallengeYear year) {
    List<FitnessLogEntry> fitnessLogs = emp.getFitnessLog(year);
    return fitnessLogs != null && !fitnessLogs.isEmpty();
  }

  private SecretBadge getLuckyDuckOrNull(ChallengeYear year, Employee emp) {
    LocalDate today = LocalDate.now();
    SecretBadge luckyDuck = asSecretBadge(Badge.LUCKY_DUCK, year, emp);

    if (emp.getSecretBadges().contains(luckyDuck)) {
      return luckyDuck;
    }
    else if (isLuckyDuckNeededForThisWeek(year, today)) {
      DayOfWeek dayOfWeek = today.getDayOfWeek();
      int randomVal = ThreadLocalRandom.current().nextInt(1, getMaxRandom(dayOfWeek) + 1);
      if (1 == randomVal) {
        return luckyDuck;
      }
    }
    return null;
  }

  private boolean isLuckyDuckNeededForThisWeek(ChallengeYear year, LocalDate today) {
    Long luckyDucks = secretBadgeDirectory.countLuckyDucks(year);
    Long weekNumber = year.getWeekWithinChallenge(today);
    Long weeksInChallenge = year.getDurationInWeeks();
    return weekNumber <= weeksInChallenge && !luckyDucks.equals(weekNumber);
  }

  private int getMaxRandom(DayOfWeek dayOfWeek) {
    // Monday=70, Tuesday=60 ... Sunday=10
    return (7 - dayOfWeek.ordinal()) * 10;
  }

  private SecretBadge getGazelleBadgeOrNull(ChallengeYear year, Employee emp) {
    if (emp.getFitnessLog(year).stream().anyMatch(entry -> entry.getCardioType() == CardioType.RUNNING && GAZELLE_THRESHOLD.compareTo(entry.getDistance()) <= 0)) {
      return asSecretBadge(Badge.GAZELLE, year, emp);
    }
    return null;
  }

  private SecretBadge getEarlyBirdBadgeOrNull(ChallengeYear year, Employee emp) {
    LocalDate oneWeekIntoChallenge = year.getStart().plusWeeks(1);

    Set<LocalDate> workoutDaysInFirstWeek = emp.getFitnessLog(year).stream()
      .filter(entry -> entry.getActivityDate().isBefore(oneWeekIntoChallenge)) // all training within first week
      .map(FitnessLogEntry::getActivityDate) // only get dates
      .collect(Collectors.toSet()); // return as set

    if (workoutDaysInFirstWeek != null && workoutDaysInFirstWeek.size() >= EARLY_BIRD_THRESHOLD) {
      return asSecretBadge(Badge.EARLY_BIRD, year, emp);
    }

    return null;
  }

  private SecretBadge getWorkerBadgeOrNull(ChallengeYear year, Employee emp) {
    if (emp.getFitnessLog(year).stream().anyMatch(entry -> WORKER_HOUR_THRESHOLD.compareTo(entry.getTotalTimeInHours()) <= 0)) {
      return asSecretBadge(Badge.WORKER, year, emp);
    }
    return null;
  }

  private SecretBadge getWorkerBeeBadgeOrNull(ChallengeYear year, Employee emp) {
    Set<LocalDate> totalWorkoutDays = emp.getFitnessLog(year).stream()
      .map(FitnessLogEntry::getActivityDate) // only get dates
      .collect(Collectors.toSet()); // return as set

    if (WORKER_BEE_WORKOUT_THRESHOLD <= totalWorkoutDays.size()) {
      return asSecretBadge(Badge.BEE, year, emp);
    }
    return null;
  }

  private SecretBadge getMermaidBadgeOrNull(ChallengeYear year, Employee emp) {
    if (isMermaidCandidate(year, emp) && !isMermaidBadgeAlreadyAwardedToSomeoneElse(year, emp)) {
      return asSecretBadge(Badge.MERMAID, year, emp);
    }
    return null;
  }

  private SecretBadge getSeaKingBadgeOrNull(ChallengeYear year, Employee emp) {
    if (isSeaKingCandidate(year, emp) && !isSeaKingBadgeAlreadyAwardedToSomeoneElse(year, emp)) {
      return asSecretBadge(Badge.SEA_KING, year, emp);
    }
    return null;
  }

  private boolean isMermaidBadgeAlreadyAwardedToSomeoneElse(ChallengeYear year, Employee e) {
    List<Employee> mermaids = secretBadgeDirectory.findMermaids(year);
    return !mermaids.isEmpty() && !mermaids.contains(e);
  }

  private boolean isMermaidCandidate(ChallengeYear year, Employee emp) {
    return emp.getGender() == Gender.F && MERMAID_SWIM_DISTANCE.compareTo(emp.getTotalSwimDistance(year)) <= 0;
  }

  private boolean isSeaKingBadgeAlreadyAwardedToSomeoneElse(ChallengeYear year, Employee e) {
    List<Employee> seaKings = secretBadgeDirectory.findSeaKings(year);
    return !seaKings.isEmpty() && !seaKings.contains(e);
  }

  private boolean isSeaKingCandidate(ChallengeYear year, Employee emp) {
    return emp.getGender() == Gender.M && SEA_KING_SWIM_DISTANCE.compareTo(emp.getTotalSwimDistance(year)) <= 0;
  }

  private SecretBadge asSecretBadge(Badge badge, ChallengeYear year, Employee emp) {
    SecretBadge sb = new SecretBadge();
    sb.setBadge(badge);
    sb.setChallengeYear(year);
    sb.setEmployee(emp);
    return sb;
  }

  @Inject
  public void setSecretBadgeDirectory(SecretBadgeDirectory secretBadgeDirectory) {
    this.secretBadgeDirectory = secretBadgeDirectory;
  }
}