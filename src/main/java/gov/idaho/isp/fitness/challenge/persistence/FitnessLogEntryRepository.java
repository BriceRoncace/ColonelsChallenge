package gov.idaho.isp.fitness.challenge.persistence;

import gov.idaho.isp.fitness.challenge.ChallengeYear;
import gov.idaho.isp.fitness.challenge.FitnessLogEntry;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FitnessLogEntryRepository extends JpaRepository<FitnessLogEntry, Long> {
  @Query("select sum(hours*60*60) + sum(minutes*60) + sum(seconds) from FitnessLogEntry e where e.challengeYear = ?1")
  Long findTotalAerobicSeconds(ChallengeYear year);

  @Query("select sum(distance) from FitnessLogEntry e where e.challengeYear = ?1")
  BigDecimal findTotalDistance(ChallengeYear year);

  @Query("select sum(reps) from FitnessLogEntry e where e.challengeYear = ?1")
  Integer findTotalReps(ChallengeYear year);

  @Query("select sum(totalWeight) from FitnessLogEntry e where e.challengeYear = ?1")
  BigDecimal findTotalResistance(ChallengeYear year);
}