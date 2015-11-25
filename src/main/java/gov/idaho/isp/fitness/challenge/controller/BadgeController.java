package gov.idaho.isp.fitness.challenge.controller;

import gov.idaho.isp.fitness.challenge.Badge;
import gov.idaho.isp.fitness.challenge.ChallengeYear;
import gov.idaho.isp.fitness.challenge.persistence.SecretBadgeDirectory;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BadgeController {
  private SecretBadgeDirectory secretBadgeDirectory;

  @RequestMapping(value ="/badges.html", method = RequestMethod.GET)
  public String badges() {
    return "badges";
  }

  @RequestMapping(value ="/secret-badges-revealed.html", method = RequestMethod.GET)
  public String secretBadges(Model model) {
    model.addAttribute("badgeCountMap", getBadgeToCountMap());
    return "secret-badges";
  }

  private Map<String,Integer> getBadgeToCountMap() {
    List<Object[]> list = secretBadgeDirectory.secretBadgeCounts(ChallengeYear.getCurrentYear());
    return list.stream().collect(Collectors.toMap(objArr -> ((Badge)objArr[0]).name(), objArr -> ((Number)objArr[1]).intValue()));
  }

  @Inject
  public void setSecretBadgeDirectory(SecretBadgeDirectory secretBadgeDirectory) {
    this.secretBadgeDirectory = secretBadgeDirectory;
  }
}