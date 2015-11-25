package gov.idaho.isp.fitness.challenge.persistence;

import gov.idaho.isp.fitness.challenge.Employee;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
  Employee findByUsernameIgnoreCase(String lastname);

  @Query("from Employee e where e.fitnessLog is not empty")
  List<Employee> findParticipants();

  @Query("select distinct department from Employee e where e.department is not null order by e.department")
  List<String> findAllDepartments();
}