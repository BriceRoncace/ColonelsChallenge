package gov.idaho.isp.fitness.challenge.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {DateWithinChallengeValidator.class})
@Documented
public @interface DateWithinChallenge {
  String message() default "Date of activity must occur during the challenge and not be a future date.";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}