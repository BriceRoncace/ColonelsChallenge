package gov.idaho.isp.fitness.challenge.interceptor;

import gov.idaho.isp.fitness.challenge.Employee;
import gov.idaho.isp.fitness.challenge.Gender;
import gov.idaho.isp.fitness.challenge.persistence.EmployeeRepository;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Named
public class CurrentUserInterceptor extends HandlerInterceptorAdapter {
  @Inject private EmployeeRepository employeeRepository;

  private static final String SETUP_LOCATION = "setup.html";
  private static final String SETUP_COMPLETE = "completeSetup.html";

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    Employee user = loadCurrentUser(request.getRemoteUser());
    request.setAttribute("currentUser", user);

    if (userNeedsSetup(user) && !isAlreadyRequestingSetup(request)) {
      response.sendRedirect(SETUP_LOCATION);
      return false;
    }

    return true;
	}

  private boolean userNeedsSetup(Employee user) {
    return user != null && (user.getStartingBodyWeight() == null || user.getGender() == Gender.U);
  }

  private boolean isAlreadyRequestingSetup(HttpServletRequest request) {
    String uri = request.getRequestURI();
    return uri.endsWith(SETUP_LOCATION) || uri.endsWith(SETUP_COMPLETE);
  }

  private Employee loadCurrentUser(String username) {
    return employeeRepository.findByUsernameIgnoreCase(username);
  }
}