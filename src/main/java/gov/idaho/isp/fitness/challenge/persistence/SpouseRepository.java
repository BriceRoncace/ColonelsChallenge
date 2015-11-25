package gov.idaho.isp.fitness.challenge.persistence;

import gov.idaho.isp.fitness.challenge.Spouse;
import org.springframework.data.repository.CrudRepository;

public interface SpouseRepository extends CrudRepository<Spouse, Long> {
  @Override
  Iterable<Spouse> findAll();
}