
package gov.idaho.isp.fitness.challenge.controller;

import gov.idaho.isp.fitness.challenge.Employee;
import gov.idaho.isp.fitness.challenge.Gender;
import gov.idaho.isp.fitness.challenge.Spouse;
import gov.idaho.isp.fitness.challenge.persistence.EmployeeRepository;
import gov.idaho.isp.fitness.challenge.persistence.SpouseRepository;
import java.math.BigDecimal;
import java.util.Arrays;
import javax.inject.Inject;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SpouseController {
  @Inject private EmployeeRepository employeeRepository;
  @Inject private SpouseRepository spouseRepository;

  @RequestMapping(value = {"/toSpouseSetup.html"}, method = RequestMethod.GET)
  public String toSpouseSetup() {
    return "setup-spouse";
  }

  @RequestMapping(value = {"/saveSpouse.html"}, method = RequestMethod.POST)
  public String saveSpouse(@RequestParam("employeeId") Long id, @Valid @ModelAttribute("spouse") Spouse spouse, BindingResult result, ModelMap modelMap, final RedirectAttributes redirectAttributes) {
    Employee emp = employeeRepository.findOne(id);
    emp.setSpouse(spouse);

    if (result.hasErrors()) {
      modelMap.put("errors", new String[] {"Invalid submission.  Please correct and resubmit."});
      return "setup-spouse";
    }

    employeeRepository.save(emp);

    redirectAttributes.addFlashAttribute("messages", Arrays.asList(new String[] {"Spouse saved."}));
    return "redirect:/dashboard.html";
  }

  @RequestMapping(value = {"/updateSpouse.html"}, method = RequestMethod.POST)
  public String updateSpouse(
    @RequestParam("spouseId") Long id,
    @RequestParam("firstName") String firstName,
    @RequestParam("lastName") String lastName,
    @RequestParam("startingBodyWeight") BigDecimal startingBodyWeight,
    @RequestParam("gender") Gender gender,
    final RedirectAttributes redirectAttributes) {

    Spouse s = spouseRepository.findOne(id);
    s.setFirstName(firstName);
    s.setLastName(lastName);
    s.setStartingBodyWeight(startingBodyWeight);
    s.setGender(gender);

    spouseRepository.save(s);

    redirectAttributes.addFlashAttribute("messages", Arrays.asList(new String[] {"Spouse saved."}));
    return "redirect:/spouseDashboard.html";
  }
}