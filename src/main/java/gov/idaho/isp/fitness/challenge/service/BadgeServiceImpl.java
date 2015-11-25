package gov.idaho.isp.fitness.challenge.service;

import gov.idaho.isp.fitness.challenge.Badge;
import gov.idaho.isp.fitness.challenge.ChallengeYear;
import gov.idaho.isp.fitness.challenge.Employee;
import gov.idaho.isp.fitness.challenge.persistence.EmployeeRankDirectory;
import gov.idaho.isp.fitness.challenge.persistence.EmployeeRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class BadgeServiceImpl implements BadgeService {
  private EmployeeRepository employeeRepository;
  private EmployeeRankDirectory employeeRankDirectory;

  @Override
  public List<Badge> getAllBadgesEarned(ChallengeYear year, Employee emp) {
    List<Badge> badges = new ArrayList<>();
    badges.addAll(getCompletionBadges(year, emp));
    badges.addAll(getPerformanceBadges(year, emp));
    badges.addAll(getSecretBadges(year, emp));
    return badges;
  }

  private List<Badge> getPerformanceBadges(ChallengeYear year, Employee emp) {
    EmployeeRankStats ranks = employeeRankDirectory.findRanks(year, emp);
    List<Badge> performanceBadges = new ArrayList<>();
    performanceBadges.add(Badge.valueOf(Badge.Type.GATOR, ranks.getSitupPushupRank()));
    performanceBadges.add(Badge.valueOf(Badge.Type.BEAST, ranks.getResistanceRank()));
    performanceBadges.add(Badge.valueOf(Badge.Type.LONG_HAULER, ranks.getAerobicHoursRank()));
    performanceBadges.add(Badge.valueOf(Badge.Type.CHEETAH, ranks.getRunRank()));
    performanceBadges.add(Badge.valueOf(Badge.Type.CYCLIST, ranks.getBikeRank()));
    performanceBadges.add(Badge.valueOf(Badge.Type.SHARK, ranks.getSwimRank()));
    performanceBadges.removeAll(Collections.singleton(null));
    return performanceBadges;
  }

  private List<Badge> getCompletionBadges(ChallengeYear year, Employee emp) {
    List<Badge> completioBadges = new ArrayList<>();
    int goalsMet = emp.getNumberOfGoalsReached(year);
    if (goalsMet == 3) {
      completioBadges.add(Badge.VICTOR);
    }
    else if (goalsMet == 2) {
      completioBadges.add(Badge.TWO_THIRDER);
    }
    else if (goalsMet == 1) {
      completioBadges.add(Badge.ONE_THIRDER);
    }

    return completioBadges;
  }

  private List<Badge> getSecretBadges(ChallengeYear year, Employee emp) {
    Employee e = employeeRepository.findOne(emp.getId());
    return e.getSecretBadges(year).stream().map(sb -> sb.getBadge()).collect(Collectors.toList());
  }

  @Inject
  public void setEmployeeRankDirectory(EmployeeRankDirectory employeeRankDirectory) {
    this.employeeRankDirectory = employeeRankDirectory;
  }

  @Inject
  public void setEmployeeRepository(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }
}