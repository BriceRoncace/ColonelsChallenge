package gov.idaho.isp.fitness.challenge.controller.advice;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
  private static final String DEFAULT_ERROR_VIEW = "error";

  @ExceptionHandler(RuntimeException.class)
  public String handleException(HttpServletRequest request, RuntimeException ex) {
    request.setAttribute("url", request.getRequestURI());
    request.setAttribute("exception", ex);
    return DEFAULT_ERROR_VIEW;
  }
}