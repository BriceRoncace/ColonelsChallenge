package gov.idaho.isp.fitness.challenge.controller.advice;

import gov.idaho.isp.fitness.challenge.propedit.LocalDatePropertyEditor;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class GlobalBindingInitializer {
  @InitBinder
  public void initLocalDateBinder(WebDataBinder binder) {
    binder.registerCustomEditor(LocalDate.class, new LocalDatePropertyEditor());
  }

  @InitBinder
  public void initBigDecimalBinder(WebDataBinder binder) throws Exception {
    DecimalFormat df = new DecimalFormat();
    DecimalFormatSymbols dfs = new DecimalFormatSymbols();
    dfs.setGroupingSeparator(',');
    df.setDecimalFormatSymbols(dfs);
    binder.registerCustomEditor(BigDecimal.class, new CustomNumberEditor(BigDecimal.class, df, true));
  }
}