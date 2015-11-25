package gov.idaho.isp.fitness.challenge.persistence;

import gov.idaho.isp.fitness.challenge.ChallengeYear;
import gov.idaho.isp.fitness.challenge.Employee;
import gov.idaho.isp.fitness.challenge.service.EmployeeRankStats;
import gov.idaho.isp.fitness.challenge.service.RankStats;

public interface EmployeeRankDirectory {
  RankStats findOverallRanks(ChallengeYear year, int maxRank);
  EmployeeRankStats findRanks(ChallengeYear year, Employee e);
}