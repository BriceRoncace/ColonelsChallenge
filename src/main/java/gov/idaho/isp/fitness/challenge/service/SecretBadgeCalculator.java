package gov.idaho.isp.fitness.challenge.service;

import gov.idaho.isp.fitness.challenge.ChallengeYear;
import gov.idaho.isp.fitness.challenge.Employee;
import gov.idaho.isp.fitness.challenge.SecretBadge;
import java.util.Set;

public interface SecretBadgeCalculator {
  Set<SecretBadge> calcuateSecretBadges(ChallengeYear year, Employee employee);
}