package gov.idaho.isp.fitness.challenge.security;

import gov.idaho.isp.fitness.challenge.Employee;
import gov.idaho.isp.fitness.challenge.persistence.EmployeeRepository;
import gov.idaho.isp.fitness.role.Role;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Named;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;

@Named
public class CustomLdapUserMapper extends LdapUserDetailsMapper {
  @Inject private EmployeeRepository employeeRepository;

  @Override
  public UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection<? extends GrantedAuthority> ldapAuthorities) {
    return super.mapUserFromContext(ctx, username, getAuthorities(username));
  }

  private Collection<? extends GrantedAuthority> getAuthorities(String username) {
    Set<GrantedAuthority> authorities = new HashSet<>();
    getRoles(username).forEach(r -> authorities.add(() -> r.toString()));
    return authorities;
  }

  private Set<Role> getRoles(String username) {
    Employee employee = employeeRepository.findByUsernameIgnoreCase(username);
    if (employee != null) {
      return employee.getRoles();
    }
    return Collections.emptySet();
  }
}