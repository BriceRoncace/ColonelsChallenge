package gov.idaho.isp.fitness.challenge.service;

import gov.idaho.isp.fitness.challenge.ChallengeYear;
import gov.idaho.isp.fitness.challenge.Employee;
import gov.idaho.isp.fitness.challenge.persistence.EmployeeCriteria;

public interface StatsService {
  StatData calculateStats(ChallengeYear year, EmployeeCriteria criteria);

  /**
   * If Employee parameter is not null, the QuickStats will include
   * that employee's mean and median results
  */
  QuickStats getQuickStats(ChallengeYear year, Employee employee);

  Totals getTotals(ChallengeYear year);
}