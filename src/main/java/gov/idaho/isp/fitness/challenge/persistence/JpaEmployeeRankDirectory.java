package gov.idaho.isp.fitness.challenge.persistence;

import gov.idaho.isp.fitness.challenge.ChallengeYear;
import gov.idaho.isp.fitness.challenge.Employee;
import gov.idaho.isp.fitness.challenge.Gender;
import gov.idaho.isp.fitness.challenge.service.EmployeeRankStats;
import gov.idaho.isp.fitness.challenge.service.RankStats;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named
public class JpaEmployeeRankDirectory implements EmployeeRankDirectory {
  @PersistenceContext
  private EntityManager em;

  @Override
  public RankStats findOverallRanks(ChallengeYear year, int maxRank) {
    List<Employee> maleEmployees = em.createQuery("from Employee where gender = ?").setParameter(1, Gender.M).getResultList();
    List<Employee> femaleEmployees = em.createQuery("from Employee where gender = ?").setParameter(1, Gender.F).getResultList();

    RankStats ranks = new RankStats();
    ranks.setChallengeYear(year);
    ranks.setMaleAerobicHours(sortIntoNewList(maleEmployees, maxRank, (e1,e2)-> e2.getTotalAerobicHours(year).compareTo(e1.getTotalAerobicHours(year))));
    ranks.setFemaleAerobicHours(sortIntoNewList(femaleEmployees, maxRank, (e1,e2)-> e2.getTotalAerobicHours(year).compareTo(e1.getTotalAerobicHours(year))));

    ranks.setMaleBike(sortIntoNewList(maleEmployees, maxRank, (e1,e2)-> e2.getTotalBikeDistance(year).compareTo(e1.getTotalBikeDistance(year))));
    ranks.setFemaleBike(sortIntoNewList(femaleEmployees, maxRank, (e1,e2)-> e2.getTotalBikeDistance(year).compareTo(e1.getTotalBikeDistance(year))));

    ranks.setMaleResistance(sortIntoNewList(maleEmployees, maxRank, (e1,e2)-> e2.getTotalWeight(year).compareTo(e1.getTotalWeight(year))));
    ranks.setFemaleResistance(sortIntoNewList(femaleEmployees, maxRank, (e1,e2)-> e2.getTotalWeight(year).compareTo(e1.getTotalWeight(year))));

    ranks.setMaleRun(sortIntoNewList(maleEmployees, maxRank, (e1,e2)-> e2.getTotalRunDistance(year).compareTo(e1.getTotalRunDistance(year))));
    ranks.setFemaleRun(sortIntoNewList(femaleEmployees, maxRank, (e1,e2)-> e2.getTotalRunDistance(year).compareTo(e1.getTotalRunDistance(year))));

    ranks.setMaleSitupPushups(sortIntoNewList(maleEmployees, maxRank, (e1,e2)-> e2.getTotalSitupsPushups(year).compareTo(e1.getTotalSitupsPushups(year))));
    ranks.setFemaleSitupPushups(sortIntoNewList(femaleEmployees, maxRank, (e1,e2)-> e2.getTotalSitupsPushups(year).compareTo(e1.getTotalSitupsPushups(year))));

    ranks.setMaleSwim(sortIntoNewList(maleEmployees, maxRank, (e1,e2)-> e2.getTotalSwimDistance(year).compareTo(e1.getTotalSwimDistance(year))));
    ranks.setFemaleSwim(sortIntoNewList(femaleEmployees, maxRank, (e1,e2)-> e2.getTotalSwimDistance(year).compareTo(e1.getTotalSwimDistance(year))));

    return ranks;
  }

  private List<Employee> sortIntoNewList(List<Employee> employees, int maxSize, Comparator<Employee> comparator) {
    List<Employee> sortedEmployees = new ArrayList<>(employees);
    Collections.sort(sortedEmployees, comparator);
    return sortedEmployees.subList(0, maxSize);
  }

  @Override
  public EmployeeRankStats findRanks(ChallengeYear challengeYear, Employee e) {
    EmployeeRankStats ranks = new EmployeeRankStats();

    List<?> repTotals = em.createNativeQuery("select e.id, nvl(sum(ENTRY.reps),0) total_reps from employee e, FITNESS_LOG_ENTRY entry where e.id = ENTRY.EMPLOYEE_ID and e.gender = " + e.getGender().ordinal() + " and entry.challenge_year = '" + challengeYear.name() + "' group by e.id having sum(entry.reps) > 0 order by total_reps desc").getResultList();
    ranks.setSitupPushupRank(getRank(e.getId(), repTotals));

    List<?> resistanceTotals = em.createNativeQuery("select e.id, nvl(sum(ENTRY.WEIGHT),0) total_weight from employee e, FITNESS_LOG_ENTRY entry where e.id = ENTRY.EMPLOYEE_ID and e.gender = " + e.getGender().ordinal() + " and entry.challenge_year = '" + challengeYear.name() + "' group by e.id having sum(entry.weight) > 0 order by total_weight desc").getResultList();
    ranks.setResistanceRank(getRank(e.getId(), resistanceTotals));

    List<?> aerobicSecondsTotal = em.createNativeQuery("select e.id, sum(nvl(hours,0)*60*60) + sum(nvl(minutes,0)*60) + sum(nvl(seconds,0)) total_seconds from employee e, FITNESS_LOG_ENTRY entry where e.id = ENTRY.EMPLOYEE_ID and e.gender = " + e.getGender().ordinal() + " and entry.challenge_year = '" + challengeYear.name() + "' group by e.id having (sum(nvl(hours,0)*60*60) + sum(nvl(minutes,0)*60) + sum(nvl(seconds,0))) > 0 order by total_seconds desc").getResultList();
    ranks.setAerobicHoursRank(getRank(e.getId(), aerobicSecondsTotal));

    List<?> runTotal = em.createNativeQuery("select e.id, nvl(sum(ENTRY.distance),0) run_distance from employee e, FITNESS_LOG_ENTRY entry where e.id = ENTRY.EMPLOYEE_ID and ENTRY.CARDIO_TYPE = 'RUNNING' and e.gender = " + e.getGender().ordinal() + " and entry.challenge_year = '" + challengeYear.name() + "' group by e.id having sum(entry.distance) > 0 order by run_distance desc").getResultList();
    ranks.setRunRank(getRank(e.getId(), runTotal));

    List<?> bikeTotal = em.createNativeQuery("select e.id, nvl(sum(ENTRY.distance),0) bike_distance from employee e, FITNESS_LOG_ENTRY entry where e.id = ENTRY.EMPLOYEE_ID and ENTRY.CARDIO_TYPE = 'BIKING' and e.gender = " + e.getGender().ordinal() + " and entry.challenge_year = '" + challengeYear.name() + "' group by e.id having sum(entry.distance) > 0 order by bike_distance desc").getResultList();
    ranks.setBikeRank(getRank(e.getId(), bikeTotal));

    List<?> swimTotal = em.createNativeQuery("select e.id, nvl(sum(ENTRY.distance),0) swim_distance from employee e, FITNESS_LOG_ENTRY entry where e.id = ENTRY.EMPLOYEE_ID and ENTRY.CARDIO_TYPE = 'SWIMMING' and e.gender = " + e.getGender().ordinal() + " and entry.challenge_year = '" + challengeYear.name() + "' group by e.id having sum(entry.distance) > 0 order by swim_distance desc").getResultList();
    ranks.setSwimRank(getRank(e.getId(), swimTotal));

    return ranks;
  }

  private Integer getRank(Long employeeId, List<?> results) {
    for (int i = 0; i < results.size(); i++) {
      Object[] rowVals = (Object[]) results.get(i);
      Number empId = (Number) rowVals[0];
      if (empId != null) {
        if (employeeId.equals(empId.longValue())) {
          return i + 1;
        }
      }
    }
    return -1;
  }
}