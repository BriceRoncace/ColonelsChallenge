package gov.idaho.isp.fitness.challenge.persistence;

import gov.idaho.isp.fitness.challenge.ChallengeYear;
import gov.idaho.isp.fitness.challenge.Employee;
import gov.idaho.isp.fitness.challenge.SecretBadge;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface SecretBadgeDirectory extends Repository<SecretBadge, Long> {
  @Query("from Employee e join e.secretBadges sb where sb.challengeYear = ?1 and sb.badge = gov.idaho.isp.fitness.challenge.Badge.MERMAID")
  List<Employee> findMermaids(ChallengeYear year);

  @Query("from Employee e join e.secretBadges sb where sb.challengeYear = ?1 and sb.badge = gov.idaho.isp.fitness.challenge.Badge.SEA_KING")
  List<Employee> findSeaKings(ChallengeYear year);

  @Query("select count(e.id) from Employee e join e.secretBadges sb where sb.challengeYear = ?1 and sb.badge = gov.idaho.isp.fitness.challenge.Badge.LUCKY_DUCK")
  Long countLuckyDucks(ChallengeYear year);

  @Query(value = "select sb.badge, count(sb.id) from SecretBadge sb where sb.challengeYear = ?1 group by sb.badge order by count(sb.id) desc")
  List<Object[]> secretBadgeCounts(ChallengeYear year);
}