package gov.idaho.isp.fitness.challenge.controller;

import gov.idaho.isp.fitness.challenge.Badge;
import gov.idaho.isp.fitness.challenge.ChallengeYear;
import gov.idaho.isp.fitness.challenge.District;
import gov.idaho.isp.fitness.challenge.Employee;
import gov.idaho.isp.fitness.challenge.SecretBadge;
import gov.idaho.isp.fitness.challenge.persistence.EmployeeCriteria;
import gov.idaho.isp.fitness.challenge.persistence.EmployeeRankDirectory;
import gov.idaho.isp.fitness.challenge.persistence.EmployeeRepository;
import gov.idaho.isp.fitness.challenge.persistence.SecretBadgeRepository;
import gov.idaho.isp.fitness.challenge.service.BadgeService;
import gov.idaho.isp.fitness.challenge.service.SecretBadgeCalculator;
import gov.idaho.isp.fitness.challenge.service.StatsService;
import gov.idaho.isp.fitness.challenge.service.Totals;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StatsController {
  @Inject private StatsService statsService;
  @Inject private EmployeeRepository employeeRepository;
  @Inject private EmployeeRankDirectory employeeRankDirectory;
  @Inject private BadgeService badgeService;
  @Inject private SecretBadgeCalculator secretBadgeCalculator;
  @Inject private SecretBadgeRepository secretBadgeRepository;

  @ModelAttribute("challengeYear")
  public ChallengeYear getUser(HttpServletRequest request)  {
    return (ChallengeYear) request.getAttribute("challengeYear");
  }

  @RequestMapping(value ="/customStats.html", method = RequestMethod.GET)
  public String customStats(@ModelAttribute("criteria") EmployeeCriteria criteria, @ModelAttribute ChallengeYear year, ModelMap modelMap) {
    modelMap.put("districts", Arrays.asList(District.values()));
    modelMap.put("departments", employeeRepository.findAllDepartments());
    if (criteria != null) {
      modelMap.put("stats", statsService.calculateStats(year, criteria));
    }
    return "stats-custom";
  }

  @RequestMapping(value ="/overviewStats.html", method = RequestMethod.GET)
  public String overviewStats(@RequestParam(value="employeeId", required=false) Long employeeId, @ModelAttribute ChallengeYear year, ModelMap modelMap) {
    Employee employee = null;
    if (employeeId != null) {
      employee = employeeRepository.findOne(employeeId);
    }

    modelMap.put("stats", statsService.getQuickStats(year, employee));
    return "stats-overview";
  }

  @RequestMapping(value ="/leaderboardStats.html", method = RequestMethod.GET)
  public String leaderboardStats(@RequestParam(required = false) ChallengeYear challengeYear, ModelMap modelMap) {
    challengeYear = challengeYear != null ? challengeYear : ChallengeYear.getCurrentYear();
    modelMap.put("ranks", employeeRankDirectory.findOverallRanks(challengeYear, 10));
    return "stats-leaderboard";
  }

  @RequestMapping(value ="/loginStats.json", headers="Accept=*/*", method = RequestMethod.GET)
  public @ResponseBody Totals getLoginStats(@ModelAttribute ChallengeYear year) {
    return statsService.getTotals(year);
  }

  @RequestMapping(value ="/badges.json", headers="Accept=*/*", method = RequestMethod.GET)
  public @ResponseBody List<Badge> getEmployeeBadges(@RequestParam(required = false) ChallengeYear challengeYear, @RequestParam() Long employeeId) {
    challengeYear = challengeYear != null ? challengeYear : ChallengeYear.getCurrentYear();
    return badgeService.getAllBadgesEarned(challengeYear, employeeRepository.findOne(employeeId));
  }

  @RequestMapping(value ="/calcSecretBadges.json", headers="Accept=*/*", method = RequestMethod.GET)
  public @ResponseBody Set<Badge> calcEmployeeSecretBadges(@RequestParam(required = false) ChallengeYear challengeYear, @RequestParam Long employeeId, @RequestParam(required=false) boolean save) {
    challengeYear = challengeYear != null ? challengeYear : ChallengeYear.getCurrentYear();
    Employee emp = employeeRepository.findOne(employeeId);
    Set<SecretBadge> secretBadges = secretBadgeCalculator.calcuateSecretBadges(challengeYear, emp);

    if (save) {
      Set<SecretBadge> existingBadges = emp.getSecretBadges(ChallengeYear.getCurrentYear());
      if (!secretBadges.equals(existingBadges)) {
        if (!existingBadges.isEmpty()) {
          secretBadgeRepository.remove(existingBadges);
        }
        secretBadgeRepository.save(secretBadges);
      }
    }

    return secretBadges.stream().map(sb -> sb.getBadge()).collect(Collectors.toSet());
  }

  @RequestMapping(value ="/calcGoals.json", headers="Accept=*/*", method = RequestMethod.GET)
  public @ResponseBody Map<String,String> calcGoals(@RequestParam(required = false) ChallengeYear challengeYear, @RequestParam Long employeeId) {
    challengeYear = challengeYear != null ? challengeYear : ChallengeYear.getCurrentYear();
    Employee emp = employeeRepository.findOne(employeeId);

    Map<String,String> goals = new HashMap<>();
    goals.put("situps/pushups", String.valueOf(emp.getDidCompleteSitupsPushupsGoal(challengeYear)));
    goals.put("resistance", String.valueOf(emp.getDidCompleteWeightGoal(challengeYear)));
    goals.put("aerobic hours", String.valueOf(emp.getDidCompleteAerobicHoursGoal(challengeYear)));
    return goals;
  }
}