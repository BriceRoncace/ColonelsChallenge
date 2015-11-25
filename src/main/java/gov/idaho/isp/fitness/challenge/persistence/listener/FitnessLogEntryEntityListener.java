package gov.idaho.isp.fitness.challenge.persistence.listener;

import gov.idaho.isp.fitness.challenge.ChallengeYear;
import gov.idaho.isp.fitness.challenge.Employee;
import gov.idaho.isp.fitness.challenge.FitnessLogEntry;
import gov.idaho.isp.fitness.challenge.SecretBadge;
import gov.idaho.isp.fitness.challenge.persistence.EmployeeRepository;
import gov.idaho.isp.fitness.challenge.persistence.SecretBadgeRepository;
import gov.idaho.isp.fitness.challenge.service.ApplicationContextProvider;
import gov.idaho.isp.fitness.challenge.service.SecretBadgeCalculator;
import java.util.Set;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

// Not trivial to perform db operations within a jpa entity listener
// http://stackoverflow.com/a/23621548/225217
// http://stackoverflow.com/q/336881/225217
// http://stackoverflow.com/a/12157593/225217
public class FitnessLogEntryEntityListener {

  @PostPersist
  @PostUpdate
  @PostRemove
  public void calculateEmployeeSecretBadges(FitnessLogEntry logEntry) {
    TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
      @Override
      public void afterCommit() {
        cacluateSecretBadges(logEntry);
      }
    });
  }

  private Employee loadEmployeeOrNull(FitnessLogEntry logEntry) {
    EmployeeRepository empRepo = ApplicationContextProvider.getApplicationContext().getBean("employeeRepository", EmployeeRepository.class);
    if (logEntry.getEmployee() != null) {
      return empRepo.findOne(logEntry.getEmployee().getId());
    }
    return null;
  }

  private void cacluateSecretBadges(FitnessLogEntry logEntry) {
    Employee emp = loadEmployeeOrNull(logEntry);
    if (emp != null) {
      Set<SecretBadge> badges = calculateSecretBadges(emp);
      Set<SecretBadge> existingBadges = emp.getSecretBadges(ChallengeYear.getCurrentYear());

      if (!badges.equals(existingBadges)) {
        SecretBadgeRepository secretBadgeRepo = ApplicationContextProvider.getApplicationContext().getBean("secretBadgeRepository", SecretBadgeRepository.class);
        if (!existingBadges.isEmpty()) {
          secretBadgeRepo.remove(existingBadges);
        }
        secretBadgeRepo.save(badges);
      }
    }
  }

  private Set<SecretBadge> calculateSecretBadges(Employee emp) {
    SecretBadgeCalculator calc = ApplicationContextProvider.getApplicationContext().getBean("secretBadgeCalculator", SecretBadgeCalculator.class);
    return calc.calcuateSecretBadges(ChallengeYear.getCurrentYear(), emp);
  }
}