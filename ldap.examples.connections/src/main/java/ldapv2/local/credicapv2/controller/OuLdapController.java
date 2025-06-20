package ldapv2.local.credicapv2.controller;

import java.util.List;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import ldapv2.local.credicapv2.dto.OuLdapDto;
import ldapv2.local.credicapv2.service.OuLdapService;

@RestController
@RequestMapping(value = "/Ous")
@CrossOrigin("*")
public class OuLdapController {

	@Autowired
	private OuLdapService service;
	
	@GetMapping
	public ResponseEntity<List<OuLdapDto>> getAllOus(HttpSession session) throws NamingException{

		return ResponseEntity.ok(service.getAllOus(session));
	}
}