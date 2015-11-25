package gov.idaho.isp.fitness.challenge.persistence;

import gov.idaho.isp.fitness.challenge.SecretBadge;
import java.util.Collection;

public interface SecretBadgeRepository {
  void save(Collection<SecretBadge> badges);
  void remove(Collection<SecretBadge> badges);
}