package gov.idaho.isp.fitness.challenge.persistence;

import gov.idaho.isp.fitness.challenge.SecretBadge;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Collection;
import javax.inject.Inject;
import javax.inject.Named;
import org.springframework.jdbc.core.JdbcTemplate;

@Named(value="secretBadgeRepository")
public class JdbcSecretBadgeRepository implements SecretBadgeRepository {
  private JdbcTemplate jdbcTemplate;

  @Override
  public void save(Collection<SecretBadge> badges) {
    badges.forEach(b -> {
      jdbcTemplate.update((Connection con) -> {
        PreparedStatement ps = con.prepareStatement("insert into EMPLOYEE_SECRET_BADGE (ID, BADGE, CHALLENGE_YEAR, EMPLOYEE_ID) values (ID_SEQ.nextVal, ?, ?, ?)");
        ps.setString(1, b.getBadge().name());
        ps.setString(2, b.getChallengeYear().name());
        ps.setLong(3, b.getEmployee().getId());
        return ps;
      });
    });
  }

  @Override
  public void remove(Collection<SecretBadge> badges) {
    badges.forEach(b -> {
      jdbcTemplate.update((Connection con) -> {
        PreparedStatement ps = con.prepareStatement("delete from EMPLOYEE_SECRET_BADGE where id = ?");
        ps.setLong(1, b.getId());
        return ps;
      });
    });
  }

  @Inject
  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }
}