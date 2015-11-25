package gov.idaho.isp.fitness.challenge.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

  @RequestMapping(value = "/login.html", method = RequestMethod.GET)
  public String login(ModelMap modelMap) {
    modelMap.addAttribute("failed", false);
    return "login";
  }

  @RequestMapping(value = "/login-failed.html", method = RequestMethod.GET)
  public String loginFailed(ModelMap modelMap) {
    modelMap.addAttribute("failed", true);
    return "login";
  }

  @RequestMapping(value = {"/", "/index.html"}, method = RequestMethod.GET)
  public String home() {
    return "redirect:/dashboard.html";
  }

  @RequestMapping(value = "/logout.html", method = RequestMethod.GET)
  public String logout(HttpServletRequest request, HttpServletResponse response, final RedirectAttributes redirectAttributes) {
    request.getSession().invalidate();
    //redirectAttributes.addFlashAttribute("messages", new String[] {"You are now logged out"});
    return "redirect:/dashboard.html";
  }
}