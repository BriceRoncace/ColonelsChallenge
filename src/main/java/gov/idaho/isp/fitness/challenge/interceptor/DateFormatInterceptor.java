package gov.idaho.isp.fitness.challenge.interceptor;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Named
public class DateFormatInterceptor extends HandlerInterceptorAdapter {
  @Inject @Value("${date.format}")
  private String dateFormat;

  @Inject @Value("${time.format}")
  private String timeFormat;

  @Inject @Value("${date.time.format}")
  private String dateTimeFormat;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    request.setAttribute("dateFormat", dateFormat);
    request.setAttribute("timeFormat", timeFormat);
    request.setAttribute("dateTimeFormat", dateTimeFormat);
	  return true;
	}
}