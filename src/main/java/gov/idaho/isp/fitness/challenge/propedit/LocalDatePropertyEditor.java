package gov.idaho.isp.fitness.challenge.propedit;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.inject.Inject;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Value;

@Named
public class LocalDatePropertyEditor extends PropertyEditorSupport {
  @Inject @Value("${date.format}")
  private String dateFormat;

  public LocalDatePropertyEditor() {
    // TODO: why is this value not set by injection
    this.dateFormat = "MM/dd/yyyy";
  }

  @Override
  public void setAsText(String value) {
    setValue(LocalDate.parse(value, DateTimeFormatter.ofPattern(dateFormat)));
  }

  @Override
  public String getAsText() {
    if (getValue() != null) {
      LocalDate d = (LocalDate)getValue();
      return d.format(DateTimeFormatter.ofPattern(dateFormat));
    }
    return "";
  }
}