package gov.idaho.isp.fitness.challenge.interceptor;

import gov.idaho.isp.fitness.challenge.ChallengeYear;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Named
public class ChallengeYearInterceptor extends HandlerInterceptorAdapter {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    request.setAttribute("challengeYear", getChallengeYear(request));
    return true;
	}

  private ChallengeYear getChallengeYear(HttpServletRequest request) {
    String challengeYear = request.getParameter("challengeYear");
    return StringUtils.hasText(challengeYear) ? ChallengeYear.valueOf(challengeYear) : ChallengeYear.getCurrentYear();
  }
}