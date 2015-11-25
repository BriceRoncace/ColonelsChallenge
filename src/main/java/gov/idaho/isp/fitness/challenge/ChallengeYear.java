package gov.idaho.isp.fitness.challenge;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;

public enum ChallengeYear {
  Y2014("2014", LocalDate.of(2014, Month.SEPTEMBER, 1), LocalDate.of(2014, Month.NOVEMBER, 9)),
  Y2015("2015", LocalDate.of(2015, Month.SEPTEMBER, 7), LocalDate.of(2015, Month.NOVEMBER, 15));

  private final String label;
  private final LocalDate start;
  private final LocalDate end;

  private ChallengeYear(String label, LocalDate start, LocalDate end) {
    this.label = label;
    this.start = start;
    this.end = end;
  }

  public String getLabel() {
    return label;
  }

  public LocalDate getStart() {
    return start;
  }

  public LocalDate getEnd() {
    return end;
  }

  public Long getDurationInWeeks() {
    return getDurationInDays() / 7;
  }

  public Long getDurationInDays() {
    return start.until(end, ChronoUnit.DAYS) + 1;
  }

  public Long getWeekWithinChallenge(LocalDate date) {
    return (long) Math.ceil((start.until(date, ChronoUnit.DAYS) + 1) / 7.0);
  }

  public ChallengeYear getLastYear() {
    String lastYear = String.valueOf(start.minusYears(1).getYear());
    return ChallengeYear.valueOf("Y" + lastYear);
  }

  public boolean isCurrent() {
    return this == ChallengeYear.getCurrentYear();
  }

  public static ChallengeYear getCurrentYear() {
    String currentYear = String.valueOf(LocalDate.now().getYear());
    return ChallengeYear.valueOf("Y" + currentYear);
  }
}