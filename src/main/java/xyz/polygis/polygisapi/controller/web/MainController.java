package xyz.polygis.polygisapi.controller.web;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller(value = "/")
@Secured({"USER"})
public class MainController {
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("eventName", "RELEASE PARTY");
		return "index";
	}
}
