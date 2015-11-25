package gov.idaho.isp.fitness.challenge.service;

import gov.idaho.isp.fitness.challenge.District;
import gov.idaho.isp.fitness.challenge.Employee;
import gov.idaho.isp.fitness.role.Role;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;

@Transactional
public class EmployeeSyncServiceImpl implements EmployeeSyncService {
  private static final Logger logger = LoggerFactory.getLogger(EmployeeSyncServiceImpl.class);

  private LdapTemplate ldapTemplate;
  private JpaRepository<Employee,Long> employeeRepository;
  private List<String> userObjectClasses;

  @Override
  public void syncEmployees() {
    sync(getUuidToEmployeeMap(), getEmployeeLdapInfo());
  }

  private void sync(Map<String,Employee> employees, Set<LdapInfo> ldapInfo) {
    if (logger.isDebugEnabled()) {
      logger.debug("Got " + employees.size() + " employees and " + ldapInfo.size() + " ldapInfo objects");
    }

    ldapInfo.forEach(info -> {
        if (employees.containsKey(info.uuid)) {
          updateEmployee(employees.get(info.uuid), info);
        }
        else {
          createEmployee(info);
        }
      });

    findEmployeesNotInLdap(employees, ldapInfo).forEach(e -> inactivateEmployee(e));
  }

  private void updateEmployee(Employee emp, LdapInfo info) {
    emp.setUuid(info.uuid);
    emp.setFirstName(info.firstName);
    emp.setLastName(info.lastName);
    emp.setTitle(info.title);
    emp.setEmail(info.email);
    emp.setDepartment(info.department);
    emp.setCommissioned(info.commissioned);
    emp.setDistrict(info.district);
    emp.getRoles().add(Role.EMPLOYEE);
    if (logger.isDebugEnabled()) {
      logger.debug("Updating person " + emp.getUsername()+ "(" + emp.getNameLastFirst() + ")");
    }
    Employee e = employeeRepository.save(emp);
    if (e == null) {
      if (logger.isDebugEnabled()) {
        logger.debug("Can't update employee " + emp.getUsername());
      }
    }
  }

  private void createEmployee(LdapInfo info) {
    Employee emp = new Employee();
    emp.setUuid(info.uuid);
    emp.setUsername(info.username);
    emp.setFirstName(info.firstName);
    emp.setLastName(info.lastName);
    emp.setTitle(info.title);
    emp.setEmail(info.email);
    emp.setDepartment(info.department);
    emp.setCommissioned(info.commissioned);
    emp.setActivelyEmployed(Boolean.TRUE);
    emp.getRoles().add(Role.EMPLOYEE);
    emp.setDistrict(info.district);

    if (emp.getDistrict() == null) {
      if (logger.isDebugEnabled()) {
        logger.debug("Skipping person " + emp.getUsername()+ "(" + emp.getNameLastFirst() + ")");
      }
      return;
    }
    if (logger.isDebugEnabled()) {
      logger.debug("Saving person " + emp.getUsername()+ "(" + emp.getNameLastFirst() + ")");
    }

    Employee e = employeeRepository.save(emp);
    if (e == null) {
      if (logger.isDebugEnabled()) {
        logger.debug("Can't save employee " + emp.getUsername());
      }
    }
  }

  private Map<String,Employee> getUuidToEmployeeMap() {
    return employeeRepository.findAll().stream().collect(Collectors.toMap(Employee::getUuid, Function.<Employee>identity()));
  }

  private Set<LdapInfo> getEmployeeLdapInfo() {
    AndFilter filter = new AndFilter();
    addObjectClassToFilter(filter);
    List<String> commissionedEmployeeCns = getCommissionedEmployees();

    List<LdapInfo> infoList = ldapTemplate.search("OU=ISP Users OU", filter.encode(), (Attributes attributes) -> {
      LdapInfo info = new LdapInfo();
      info.uuid = getUUID(attributes);
      info.username = nullSafeGet(attributes, "sAMAccountName");
      info.firstName = nullSafeGet(attributes, "givenName");
      info.lastName = nullSafeGet(attributes, "sn");
      info.title = nullSafeGet(attributes, "title");
      info.email = nullSafeGet(attributes, "mail").toLowerCase();
      info.department = nullSafeGet(attributes, "department");
      info.district = asDistrict(nullSafeGet(attributes, "physicalDeliveryOfficeName"));
      info.commissioned = commissionedEmployeeCns.contains(nullSafeGet(attributes, "distinguishedName"));
      return info;
    });

    return new HashSet<>(infoList);
  }

  private String nullSafeGet(Attributes attributes, String property) throws NamingException {
    Attribute a = attributes.get(property);
    if (a != null) {
      Object value = a.get();
      if (value != null) {
        return value.toString();
      }
    }
    return "";
  }

  private String getUUID(Attributes attributes) throws NamingException {
    Object guid = attributes.get("objectGUID").get();
    if (guid instanceof byte[]) {
      UUID uuid = UUID.nameUUIDFromBytes((byte[])guid);
      return uuid != null ? uuid.toString() : null;
    }
    return guid != null ? guid.toString() : null;
  }

  private District asDistrict(String office) {
    Optional<District> dist = District.valueOfAltLabel(office);
    if (dist.isPresent()) {
      return dist.get();
    }
    return null;
  }

  private List<String> getCommissionedEmployees() {
    List<String> commissionedEmployeeCns = new ArrayList<>();

    AndFilter filter = new AndFilter();
    filter.and(new EqualsFilter("objectClass", "group"));
    ldapTemplate.search("CN=DL Commissioned,OU=Distribution Lists", filter.encode(), (Attributes attributes) -> {
      NamingEnumeration<String> members = (NamingEnumeration<String>) attributes.get("member").getAll();
      while (members.hasMoreElements()) {
        commissionedEmployeeCns.add(members.nextElement());
      }
      return null;
    });

    return commissionedEmployeeCns;
  }

  private void addObjectClassToFilter(AndFilter filter) {
    userObjectClasses.forEach((objectClass) -> {
      filter.and(new EqualsFilter("objectClass", objectClass));
    });
  }

  private List<Employee> findEmployeesNotInLdap(Map<String,Employee> employees, Set<LdapInfo> ldapInfo) {
    Set<String> employeeUuids = employees.keySet();
    Set<String> ldapUuids = ldapInfo.stream().map(info -> info.uuid).collect(Collectors.toSet());
    Set<String> difference = diff(employeeUuids, ldapUuids);
    return difference.stream().map(uuid -> employees.get(uuid)).collect(Collectors.toList());
  }

  public static <T> Set<T> diff(final Set<? extends T> s1, final Set<? extends T> s2) {
    Set<T> symmetricDiff = new HashSet<>(s1);
    symmetricDiff.addAll(s2);
    Set<T> commonElements = new HashSet<>(s1);
    commonElements.retainAll(s2);
    symmetricDiff.removeAll(commonElements);
    return symmetricDiff;
  }

  private void inactivateEmployee(Employee emp) {
    if (emp != null) {
      if (logger.isDebugEnabled()) {
        logger.debug("Inactivating employee " + emp.getNameLastFirst() + " as the UUID, " + emp.getUuid() + ", is no longer found in ldap");
      }
      emp.setActivelyEmployed(Boolean.FALSE);
      emp.getRoles().clear();
      employeeRepository.save(emp);
    }
  }

  public class LdapInfo {
    public String uuid;
    public String username;
    public String firstName;
    public String lastName;
    public String title;
    public String email;
    public String department;
    public District district;
    public Boolean commissioned;

    @Override
    public String toString() {
      return "LdapInfo{" + "uuid=" + uuid + ", username=" + username + ", firstName=" + firstName + ", lastName=" + lastName + ", title=" + title + ", email=" + email + ", department=" + department + ", district=" + district + ", commissioned=" + commissioned + '}';
    }
  }

  public void setLdapTemplate(LdapTemplate ldapTemplate) {
    this.ldapTemplate = ldapTemplate;
  }

  public void setEmployeeRepository(JpaRepository<Employee, Long> employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  public void setUserObjectClasses(List<String> userObjectClasses) {
    this.userObjectClasses = userObjectClasses;
  }
}