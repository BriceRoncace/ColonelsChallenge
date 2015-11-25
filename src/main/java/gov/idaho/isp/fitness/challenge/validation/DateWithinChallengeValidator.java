package gov.idaho.isp.fitness.challenge.validation;

import gov.idaho.isp.fitness.challenge.ChallengeYear;
import gov.idaho.isp.fitness.challenge.FitnessLogEntry;
import java.time.LocalDate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateWithinChallengeValidator implements ConstraintValidator<DateWithinChallenge, Object> {

  @Override
  public void initialize(DateWithinChallenge a) {
  }

  @Override
  public boolean isValid(Object object, ConstraintValidatorContext cvc) {
    if (object == null) {
      return true;
    }

    if (!(object instanceof FitnessLogEntry)) {
      throw new IllegalArgumentException("Excepting to validate an instance of FitnessLogEntry, instead got [" + object.getClass() + "]");
    }

    FitnessLogEntry e = (FitnessLogEntry) object;
    return isDateWithinChallenge(e.getActivityDate()) && !isDateInTheFuture(e.getActivityDate());
  }

  private boolean isDateWithinChallenge(LocalDate d) {
    if (d == null) {
      return false;
    }
    
    ChallengeYear currentChallenge = ChallengeYear.getCurrentYear();
    return d.isAfter(currentChallenge.getStart().minusDays(1)) && d.isBefore(currentChallenge.getEnd().plusDays(1));
  }

  private boolean isDateInTheFuture(LocalDate d) {
    return d.isAfter(LocalDate.now());
  }
}