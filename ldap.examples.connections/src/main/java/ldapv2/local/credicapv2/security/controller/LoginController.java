package ldapv2.local.credicapv2.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/")
    public String home() {
        return "index";
    }
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
}