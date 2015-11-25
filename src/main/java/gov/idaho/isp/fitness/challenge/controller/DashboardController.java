package gov.idaho.isp.fitness.challenge.controller;

import java.security.Principal;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DashboardController {
  @RequestMapping(value ="/dashboard.html", method = RequestMethod.GET)
  public String dashboard(@RequestParam Map<String,String> params, ModelMap modelMap, Principal principal) {
    return "dashboard";
  }

  @RequestMapping(value ="/spouseDashboard.html", method = RequestMethod.GET)
  public String spouseDashboard(@RequestParam Map<String,String> params, ModelMap modelMap, Principal principal) {
    return "dashboard-spouse";
  }
}