package gov.idaho.isp.fitness.challenge.persistence;

import gov.idaho.isp.fitness.challenge.ChallengeYear;
import gov.idaho.isp.fitness.challenge.Employee;
import gov.idaho.isp.fitness.challenge.Employee_;
import gov.idaho.isp.fitness.challenge.FitnessLogEntry;
import gov.idaho.isp.fitness.challenge.FitnessLogEntry_;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

public class AdhocEmployeeSpecification implements Specification<Employee> {
  private final EmployeeCriteria criteria;
  private final ChallengeYear year;

  public AdhocEmployeeSpecification(ChallengeYear year, EmployeeCriteria criteria) {
    this.criteria = criteria;
    this.year = year;
  }

  @Override
  public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
    List<Predicate> predicates = new ArrayList<>();

    if (Boolean.TRUE.equals(criteria.isParticipant())) {
      Subquery<Long> subquery = cq.subquery(Long.class);
      Root<FitnessLogEntry> fromFitnessLogEntry = subquery.from(FitnessLogEntry.class);
      subquery.select(fromFitnessLogEntry.get(FitnessLogEntry_.id));

      List<Predicate> subQueryPredicates = new ArrayList<>();
      subQueryPredicates.add(cb.equal(fromFitnessLogEntry.get(FitnessLogEntry_.challengeYear), year));
      subQueryPredicates.add(cb.equal(fromFitnessLogEntry.get(FitnessLogEntry_.employee), root));
      subquery.where(subQueryPredicates.toArray(new Predicate[]{}));
      predicates.add(cb.exists(subquery));
    }
    if (criteria.getDistricts() != null && !criteria.getDistricts().isEmpty()) {
      predicates.add(cb.isTrue(root.get(Employee_.district).in(criteria.getDistricts())));
    }
    if (criteria.getDepartments() != null && !criteria.getDepartments().isEmpty()) {
      predicates.add(cb.isTrue(root.get(Employee_.department).in(criteria.getDepartments())));
    }
    if (criteria.getCommissioned() != null) {
      if (criteria.getCommissioned()) {
        predicates.add(cb.isTrue(root.get(Employee_.commissioned)));
      }
      else {
        predicates.add(cb.isFalse(root.get(Employee_.commissioned)));
      }
    }
    if (criteria.getGender() != null) {
      predicates.add(cb.equal(root.get(Employee_.gender), criteria.getGender()));
    }

    return andTogether(cb, predicates);
  }

  public static Predicate andTogether(CriteriaBuilder cb, List<Predicate> predicates) {
    return cb.and(predicates.toArray(new Predicate[0]));
 	}
}