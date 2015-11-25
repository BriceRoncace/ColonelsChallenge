package gov.idaho.isp.fitness.challenge.service;

import gov.idaho.isp.fitness.challenge.ChallengeParticipant;
import gov.idaho.isp.fitness.challenge.ChallengeYear;
import gov.idaho.isp.fitness.challenge.District;
import gov.idaho.isp.fitness.challenge.Employee;
import gov.idaho.isp.fitness.challenge.Gender;
import gov.idaho.isp.fitness.challenge.persistence.AdhocEmployeeSpecification;
import gov.idaho.isp.fitness.challenge.persistence.EmployeeCriteria;
import gov.idaho.isp.fitness.challenge.persistence.EmployeeRepository;
import gov.idaho.isp.fitness.challenge.persistence.FitnessLogEntryRepository;
import gov.idaho.isp.fitness.challenge.utils.MathUtils;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class StatsServiceImpl implements StatsService {

  @Inject private EmployeeRepository employeeRepository;
  @Inject private FitnessLogEntryRepository fitnessLogEntryRepository;

  @Override
  public StatData calculateStats(ChallengeYear year, EmployeeCriteria criteria) {
    return calculateStatData(year, getDescriptionBasedOnCriteria(criteria), getEmployeeParticipationCount(year, criteria), getTotalEmployeeCount(year, criteria), getParticipants(year, criteria));
  }

  @Override
  public QuickStats getQuickStats(ChallengeYear year, Employee employee) {
    QuickStats quickStats = new QuickStats();
    EmployeeCriteria criteria = new EmployeeCriteria();
    criteria.setParticipant(Boolean.TRUE);

    quickStats.setIspAll(calculateStatData(year, "All of ISP", getEmployeeParticipationCount(year, criteria), getTotalEmployeeCount(year, criteria), getParticipants(year, criteria)));

    criteria.setDistricts(Arrays.asList(District.DISTRICT_1));
    quickStats.setD1(calculateStatData(year, "D1", getEmployeeParticipationCount(year, criteria), getTotalEmployeeCount(year, criteria), getParticipants(year, criteria)));

    criteria.setDistricts(Arrays.asList(District.DISTRICT_2));
    quickStats.setD2(calculateStatData(year, "D2", getEmployeeParticipationCount(year, criteria), getTotalEmployeeCount(year, criteria), getParticipants(year, criteria)));

    criteria.setDistricts(Arrays.asList(District.DISTRICT_3));
    quickStats.setD3(calculateStatData(year, "D3", getEmployeeParticipationCount(year, criteria), getTotalEmployeeCount(year, criteria), getParticipants(year, criteria)));

    criteria.setDistricts(Arrays.asList(District.DISTRICT_4));
    quickStats.setD4(calculateStatData(year, "D4", getEmployeeParticipationCount(year, criteria), getTotalEmployeeCount(year, criteria), getParticipants(year, criteria)));

    criteria.setDistricts(Arrays.asList(District.DISTRICT_5));
    quickStats.setD5(calculateStatData(year, "D5", getEmployeeParticipationCount(year, criteria), getTotalEmployeeCount(year, criteria), getParticipants(year, criteria)));

    criteria.setDistricts(Arrays.asList(District.DISTRICT_6));
    quickStats.setD6(calculateStatData(year, "D6", getEmployeeParticipationCount(year, criteria), getTotalEmployeeCount(year, criteria), getParticipants(year, criteria)));

    if (employee != null) {
      quickStats.setIndividual(calculateStatData(year, "You", 1L, 1L, Arrays.asList(new Employee[] {employee})));
    }

    return quickStats;
  }

  @Override
  public Totals getTotals(ChallengeYear year) {
    Totals totals = new Totals();
    totals.setTotalAerobicHours(MathUtils.secondsToHours(fitnessLogEntryRepository.findTotalAerobicSeconds(year)));
    totals.setTotalDistance(fitnessLogEntryRepository.findTotalDistance(year));
    totals.setTotalSitupsPushups(fitnessLogEntryRepository.findTotalReps(year));
    totals.setTotalResistance(fitnessLogEntryRepository.findTotalResistance(year));
    totals.setTotalParticipants(getEmployeeParticipationCount(year, new EmployeeCriteria()));
    return totals;
  }

  private StatData calculateStatData(ChallengeYear year, String label, Long participantCount, Long totalCount, List<? extends ChallengeParticipant> participants) {
    StatData sd = new StatData();
    sd.setChallengeYear(year);
    sd.setLabel(label);
    sd.setParticipantCount(participantCount);
    sd.setTotalCount(totalCount);
    sd.setParticipationRate(calcParticipationRate(participantCount, totalCount));
    sd.setMeanAerobic(calcMeanAerobicHours(year, participants));
    sd.setMedianAerobic(calcMedianAerobicHours(year, participants));
    sd.setMeanDistance(calcMeanDistance(year, participants));
    sd.setMedianDistance(calcMedianDistance(year, participants));
    sd.setMeanSitupsPushups(calcMeanReps(year, participants));
    sd.setMedianSitupsPushups(calcMedianReps(year, participants));
    sd.setMeanResistance(calcMeanWeight(year, participants));
    sd.setMedianResistance(calcMedianWeight(year, participants));
    return sd;
  }

  private String getDescriptionBasedOnCriteria(EmployeeCriteria c) {
    StringBuilder sb = new StringBuilder();
    sb.append("Statistics for ").append(getGenderText(c)).append(" ")
      .append(getCommissionedText(c)).append(" ")
      .append("participants in ").append(getDistrictText(c)).append(" ")
      .append("(").append(getDepartmentText(c)).append(")");
    return sb.toString();
  }

  private String getDistrictText(EmployeeCriteria c) {
    if (c.getDistricts() != null && !c.getDistricts().isEmpty()) {
      if (c.getDistricts().size() == 1) {
        return "district " + c.getDistricts().get(0).getLabel();
      }
      StringJoiner sj = new StringJoiner(",");
      c.getDistricts().forEach(d -> sj.add(d.getLabel()));
      return "districts " + sj.toString();
    }
    else {
      return "all districts";
    }
  }

  private String getDepartmentText(EmployeeCriteria c) {
    if (c.getDepartments()!= null && !c.getDepartments().isEmpty()) {
      if (c.getDepartments().size() == 1) {
        return "department " + c.getDepartments().get(0);
      }
      StringJoiner sj = new StringJoiner(",");
      c.getDepartments().forEach(d -> sj.add(d));
      return "departments " + sj.toString();
    }
    else {
      return "all departments";
    }
  }

  private String getGenderText(EmployeeCriteria c) {
    if (c.getGender() == Gender.M) {
      return "male";
    }
    if (c.getGender() == Gender.F) {
      return "female";
    }
    return "male and female";
  }

  private String getCommissionedText(EmployeeCriteria c) {
    if (Boolean.TRUE.equals(c.getCommissioned())) {
      return "commissioned";
    }
    if (Boolean.FALSE.equals(c.getCommissioned())) {
      return "non-commissioned";
    }
    return "commissioned and non-commissioned";
  }

  private long getTotalEmployeeCount(ChallengeYear year, EmployeeCriteria c) {
    c.setParticipant(null);
    AdhocEmployeeSpecification specification = new AdhocEmployeeSpecification(year, c);
    return employeeRepository.count(specification);
  }

  private long getEmployeeParticipationCount(ChallengeYear year, EmployeeCriteria c) {
    c.setParticipant(true);
    AdhocEmployeeSpecification specification = new AdhocEmployeeSpecification(year, c);
    return employeeRepository.count(specification);
  }

  private BigDecimal calcParticipationRate(Long participantCount, Long totalCount) {
    long part = participantCount != null ? participantCount : 0L;
    long total = totalCount != null ? totalCount : 0L;
    if (total != 0L) {
      return BigDecimal.valueOf(((double)part/(double)total));
    }
    return BigDecimal.ZERO;
  }

  private List<? extends ChallengeParticipant> getParticipants(ChallengeYear year, EmployeeCriteria c) {
    c.setParticipant(true);
    return employeeRepository.findAll(new AdhocEmployeeSpecification(year, c));
  }

  private BigDecimal calcMeanAerobicHours(ChallengeYear year, List<? extends ChallengeParticipant> participants) {
    if (participants == null || participants.isEmpty()) {
      return BigDecimal.ZERO;
    }

    List<BigDecimal> hours = participants.stream().map(p -> p != null ? p.getTotalAerobicHours(year) : BigDecimal.ZERO).collect(Collectors.toList());
    return MathUtils.getMean(hours);
  }

  private BigDecimal calcMedianAerobicHours(ChallengeYear year, List<? extends ChallengeParticipant> participants) {
    List<BigDecimal> hours = participants.stream().map(p -> p != null ? p.getTotalAerobicHours(year) : BigDecimal.ZERO).collect(Collectors.toList());
    return MathUtils.getMedian(hours);
  }

  private BigDecimal calcMeanDistance(ChallengeYear year, List<? extends ChallengeParticipant> participants) {
    if (participants == null || participants.isEmpty()) {
      return BigDecimal.ZERO;
    }

    List<BigDecimal> distances = participants.stream().map(p -> p != null ? p.getTotalDistance(year) : BigDecimal.ZERO).collect(Collectors.toList());
    return MathUtils.getMean(distances);
  }

  private BigDecimal calcMedianDistance(ChallengeYear year, List<? extends ChallengeParticipant> participants) {
    List<BigDecimal> distances = participants.stream().map(p -> p != null ? p.getTotalDistance(year): BigDecimal.ZERO).collect(Collectors.toList());
    return MathUtils.getMedian(distances);
  }

  private BigDecimal calcMeanReps(ChallengeYear year, List<? extends ChallengeParticipant> participants) {
    if (participants == null || participants.isEmpty()) {
      return BigDecimal.ZERO;
    }

    List<BigDecimal> reps = participants.stream().map(p -> p != null ? BigDecimal.valueOf(p.getTotalSitupsPushups(year)) : BigDecimal.ZERO).collect(Collectors.toList());
    return MathUtils.getMean(reps);
  }

  private BigDecimal calcMedianReps(ChallengeYear year, List<? extends ChallengeParticipant> participants) {
    List<BigDecimal> reps = participants.stream().map(p -> p != null ? BigDecimal.valueOf(p.getTotalSitupsPushups(year)) : BigDecimal.ZERO).collect(Collectors.toList());
    return MathUtils.getMedian(reps);
  }

  private BigDecimal calcMeanWeight(ChallengeYear year, List<? extends ChallengeParticipant> participants) {
    if (participants == null || participants.isEmpty()) {
      return BigDecimal.ZERO;
    }

    List<BigDecimal> pounds = participants.stream().map(p -> p != null ? p.getTotalWeight(year) : BigDecimal.ZERO).collect(Collectors.toList());
    return MathUtils.getMean(pounds);
  }

  private BigDecimal calcMedianWeight(ChallengeYear year, List<? extends ChallengeParticipant> participants) {
    List<BigDecimal> weights = participants.stream().map(p -> p != null ? p.getTotalWeight(year) : BigDecimal.ZERO).collect(Collectors.toList());
    return MathUtils.getMedian(weights);
  }
}