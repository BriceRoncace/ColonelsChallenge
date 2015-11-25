package gov.idaho.isp.fitness.challenge.controller;

import gov.idaho.isp.fitness.challenge.Employee;
import gov.idaho.isp.fitness.challenge.Gender;
import gov.idaho.isp.fitness.challenge.persistence.EmployeeRepository;
import gov.idaho.isp.fitness.challenge.service.EmployeeSyncService;
import java.math.BigDecimal;
import java.util.Arrays;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EmployeeController {
  @Inject private EmployeeRepository employeeRepository;
  @Inject private EmployeeSyncService employeeSyncService;

  @RequestMapping(value = {"/setup.html"}, method = RequestMethod.GET)
  public String setup(@RequestParam(value="emp", required=false) Boolean employee) {
    if (Boolean.FALSE.equals(employee)) {
      return "setup-spouse";
    }

    return "setup";
  }

  @RequestMapping(value = {"/completeSetup.html"}, method = RequestMethod.POST)
  public String finalizeAccount(@RequestParam("employeeId") Long id, @RequestParam("weight") BigDecimal weight, @RequestParam("gender") Gender gender, final RedirectAttributes redirectAttributes) {
    Employee emp = employeeRepository.findOne(id);
    emp.setStartingBodyWeight(weight);
    emp.setGender(gender);
    employeeRepository.save(emp);
    redirectAttributes.addFlashAttribute("messages", Arrays.asList(new String[] {"Thank you.  Your account has been setup."}));
    return "redirect:/dashboard.html";
  }

  @RequestMapping(value = {"/employeeSync.html"}, method = RequestMethod.GET)
  public String employeeSync(@RequestParam(value="emp", required=false) Boolean employee) {
    employeeSyncService.syncEmployees();
    return "redirect:/dashboard.html";
  }
}