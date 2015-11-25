package gov.idaho.isp.fitness.challenge.validation;

import gov.idaho.isp.fitness.challenge.ExerciseType;
import gov.idaho.isp.fitness.challenge.FitnessLogEntry;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class CardioTypeRequiredValidator implements ConstraintValidator<CardioTypeRequired, Object> {

  @Override
  public void initialize(CardioTypeRequired a) {
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
    if (e.getExerciseType() == ExerciseType.AEROBIC && e.getCardioType() == null) {
      return false;
    }
    return true;
  }
}