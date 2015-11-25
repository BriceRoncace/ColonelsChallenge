package gov.idaho.isp.fitness.challenge.service;

import gov.idaho.isp.fitness.challenge.Badge;
import gov.idaho.isp.fitness.challenge.ChallengeYear;
import gov.idaho.isp.fitness.challenge.Employee;
import java.util.List;

public interface BadgeService {
  List<Badge> getAllBadgesEarned(ChallengeYear year, Employee e);
}