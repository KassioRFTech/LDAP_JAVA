package ldapv2.local.credicapv2.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import ldapv2.local.credicapv2.dto.PersonLdapDto;
import ldapv2.local.credicapv2.service.PersonLdapService;

@RestController
@RequestMapping(value = "/users")
@CrossOrigin("*")
public class PersonLdapController {

	
	@Autowired
	private PersonLdapService service;
	
	@GetMapping(value = "/{ouname}")
	public ResponseEntity<List<PersonLdapDto>> getUsersOU(@PathVariable String ouname, HttpSession session){
	
		if(ouname.equalsIgnoreCase("sede")) {
			return ResponseEntity.ok(service.getUsersSede(ouname, session));
		}else {
			return ResponseEntity.ok(service.getUsers(ouname, session));
		}
	}
	
	@PatchMapping(value = "/{login}")
	public ResponseEntity<PersonLdapDto> updateActOrDes(@PathVariable String login, @RequestBody PersonLdapDto dto, HttpSession session) throws NamingException, UnsupportedEncodingException {
		
		if(dto.getPass() != null && dto.getAccountControl() == null) {
			service.updatePass(login, dto, session);
			return ResponseEntity.ok(service.updatePass(login, dto, session));
		}
		
		if(dto.getAccountControl().equalsIgnoreCase("desativar")) {
			dto.setAccountControl("514");
			return ResponseEntity.ok(service.updateActOrDes(login, dto, session));
		}
		
		else{
			if(dto.getAccountControl().equalsIgnoreCase("ativar")) {
				dto.setAccountControl("512");
			}
			return ResponseEntity.ok(service.updateActOrDes(login, dto, session));
		}
	}
}