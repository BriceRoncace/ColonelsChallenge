package gov.idaho.isp.fitness.challenge.controller;

import gov.idaho.isp.fitness.challenge.persistence.EmployeeRepository;
import gov.idaho.isp.fitness.challenge.persistence.SpouseRepository;
import java.util.Map;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ResultsController {
  @Inject private EmployeeRepository employeeRepository;
  @Inject private SpouseRepository spouseRepository;

  @RequestMapping(value ="/results.html", method = RequestMethod.GET)
  public String results(@RequestParam Map<String,String> params, ModelMap modelMap) {
    modelMap.put("participants", employeeRepository.findParticipants());
    modelMap.put("type", "employee");
    return "results";
  }

  @RequestMapping(value ="/spouseResults.html", method = RequestMethod.GET)
  public String spouseResults(@RequestParam Map<String,String> params, ModelMap modelMap) {
    modelMap.put("participants", spouseRepository.findAll());
    return "results-spouse";
  }
}