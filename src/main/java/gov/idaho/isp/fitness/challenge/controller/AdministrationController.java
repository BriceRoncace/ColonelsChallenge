package gov.idaho.isp.fitness.challenge.controller;

import gov.idaho.isp.fitness.challenge.persistence.EmployeeRepository;
import java.util.Map;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdministrationController {
  @Inject private EmployeeRepository employeeRepository;

  @RequestMapping(value ="/administration.html", method = RequestMethod.GET)
  public String dashboard(@RequestParam Map<String,String> params, ModelMap modelMap) {
    modelMap.put("participants", employeeRepository.findParticipants());
    return "administration";
  }
}