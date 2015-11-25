package gov.idaho.isp.fitness.challenge.controller;

import gov.idaho.isp.fitness.challenge.CardioType;
import gov.idaho.isp.fitness.challenge.ExerciseType;
import gov.idaho.isp.fitness.challenge.FitnessLogEntry;
import gov.idaho.isp.fitness.challenge.persistence.FitnessLogEntryRepository;
import gov.idaho.isp.fitness.challenge.validation.CardioTypeRequired;
import gov.idaho.isp.fitness.challenge.validation.DateWithinChallenge;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
public class FitnessLogEntryController {

  @Inject private FitnessLogEntryRepository fitnessLogEntryRepository;

  @RequestMapping(value = "/toEntry.html", method = RequestMethod.GET)
  public String loginFailed(ModelMap modelMap) {
    populateModelMapForForm(modelMap);
    return "save-fitness-log-entry";
  }

  @RequestMapping(value = "/saveEntry.html", method = RequestMethod.POST)
  public String saveFitnessLogEntry(@Valid @ModelAttribute("entry") FitnessLogEntry entry, BindingResult result, @RequestParam(value = "forSpouse") Boolean forSpouse, ModelMap modelMap, final RedirectAttributes redirectAttributes) {
    if (result.hasErrors()) {
      populateModelMapForForm(modelMap);
      modelMap.put("forSpouse", forSpouse);
      modelMap.put("errors", getErrorMessages(result));
      return "save-fitness-log-entry";
    }

    String redirectPage = "dashboard.html";
    if (Boolean.TRUE.equals(forSpouse)) {
      entry.setEmployee(null);
      redirectPage = "spouseDashboard.html";
    }
    else {
      entry.setSpouse(null);
    }

    fitnessLogEntryRepository.save(entry);
    redirectAttributes.addFlashAttribute("messages", new String[] {"Training entry saved."});
    return "redirect:/" + redirectPage;
  }

  private List<String> getErrorMessages(BindingResult result) {
    List<String> errorMessages = new ArrayList<>();

    result.getAllErrors().forEach(oe -> {
      if (oe.getCode().equals(CardioTypeRequired.class.getSimpleName())) {
        errorMessages.add(oe.getDefaultMessage());
      }
      if (oe.getCode().equals(DateWithinChallenge.class.getSimpleName())) {
        errorMessages.add(oe.getDefaultMessage());
      }
    });

    if (errorMessages.isEmpty()) {
      errorMessages.add("Invalid submission.  Please correct and resubmit.");
    }

    return errorMessages;
  }

  @RequestMapping(value = "/viewEntry.html", method = RequestMethod.GET)
  public String viewFitnessLogEntry(@RequestParam("id") long id, ModelMap model) {
    model.put("entry", fitnessLogEntryRepository.findOne(id));
    return "view-fitness-log-entry";
  }

  @RequestMapping(value = "/editEntry.html", method = RequestMethod.GET)
  public String editFitnessLogEntry(@RequestParam("id") long id, ModelMap modelMap) {
    FitnessLogEntry entry = fitnessLogEntryRepository.findOne(id);
    modelMap.put("entry", entry);
    populateModelMapForForm(modelMap);
    modelMap.put("forSpouse", entry.getSpouse() != null);
    return "save-fitness-log-entry";
  }

  @RequestMapping(value = "/deleteEntry.html", method = RequestMethod.GET)
  public String deleteFitnessLogEntry(@RequestParam("id") long id, @RequestParam("employee") boolean employee, ModelMap model, final RedirectAttributes redirectAttributes) {
    fitnessLogEntryRepository.delete(id);
    redirectAttributes.addFlashAttribute("messages", new String[] {"Training entry deleted."});
    return employee ? "redirect:/dashboard.html" : "redirect:/spouseDashboard.html";
  }

  private void populateModelMapForForm(ModelMap modelMap) {
    modelMap.put("exerciseTypes", Arrays.asList(ExerciseType.values()));
    modelMap.put("cardioTypes", Arrays.asList(CardioType.values()));
  }
}