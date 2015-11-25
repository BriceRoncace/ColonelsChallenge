
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
@Constraint(validatedBy = {CardioTypeRequiredValidator.class})
@Documented
public @interface CardioTypeRequired {
  String message() default "An aerobic category must be specified.";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}