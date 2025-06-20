package ldapv2.local.credicapv2.context.contoller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import ldapv2.local.credicapv2.context.dto.UserContextDto;
import ldapv2.local.credicapv2.context.service.ContextService;

@RestController
@RequestMapping(value = "/logger")
@CrossOrigin("*")
public class ContextController {
	
	@Autowired
	private ContextService service;
	
	@PostMapping
	public ResponseEntity<Void> conn(@RequestBody UserContextDto dto, HttpSession session) {
		session.setAttribute("ldapUser", dto.getUsername());
		session.setAttribute("ldapPass", dto.getPassword());
		service.conn(session);
		return ResponseEntity.noContent().build();
	}
}